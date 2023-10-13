package com.anntz.BlockchainImplementation;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main (String[] args){
        // BouncyCastle Provider Setup
        Security.addProvider(new BouncyCastleProvider());

        Wallet userA = new Wallet();
        Wallet userB = new Wallet();

        Wallet lender = new Wallet();
        BlockChain chain = new BlockChain();
        Miner miner = new Miner();


        // send 500 coins to userA. This will be genesis transaction

        Transaction genesisTransaction = new Transaction(lender.getPublicKey(), userA.getPublicKey(), 500, null);
        genesisTransaction.signSignature(lender.getPrivateKey());
        genesisTransaction.setTransactionId("0");
        genesisTransaction.outputs.add(new OutgoingTransaction(genesisTransaction.getReceiverAddress(), genesisTransaction.getAmount(), genesisTransaction.getTransactionId()));
        BlockChain.UnspentTransaction.put(genesisTransaction.outputs.get(0).getOutgoingTransactionId(), genesisTransaction.outputs.get(0));

        Block  genesisBlock = new Block(Constants.GENESIS_PREVIOUS_HASH);
        genesisBlock.addTransaction(genesisTransaction);
        miner.mine(genesisBlock, chain);


        Block block1 = new Block(genesisBlock.getHash());
        System.out.println("\n userA's balance is: " + userA
                .calculateBalance());
        System.out.println("\n userA sends 120 coins to userB " );
        block1.addTransaction(userA.transferBalance(userB.getPublicKey(), 120));
        miner.mine(block1, chain);
        System.out.println("\n userA's balance is: " + userA
                .calculateBalance());
        System.out.println("\n userB's balance is: " + userB
                .calculateBalance());

        Block block2 = new Block(block1.getHash());
        System.out.println("\n userA tries sending 600 coins to userB");
        block2.addTransaction(userA.transferBalance(userB.getPublicKey(), 600));
        miner.mine(block2, chain);
        System.out.println("\n userA's balance is: " + userA
                .calculateBalance());
        System.out.println("\n userB's balance is: " + userB
                .calculateBalance());

        Block block3 = new Block(block2.getHash());
        System.out.println("\n userB tries sending 110 coins to userB");
        block3.addTransaction(userB.transferBalance(userA.getPublicKey(), 110));
        System.out.println("\n userA's balance is: " + userA
                .calculateBalance());
        System.out.println("\n userB's balance is: " + userB
                .calculateBalance());
        miner.mine(block2, chain);

        System.out.println("\n Miner's reward: " + miner.getReward());


    }
}
