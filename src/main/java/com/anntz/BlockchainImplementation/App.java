package com.anntz.BlockchainImplementation;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import java.util.ArrayList;

public class App {
    public static void main (String[] args){
    // BouncyCastle Provider Setup
    Security.addProvider(new BouncyCastleProvider());

    ArrayList<Wallet> wallets = new ArrayList<Wallet>();
    for(int i=0; i<6;i++){
      wallets.add(new Wallet());
    }

    // Initialize our main chain
    BlockChain chain = new BlockChain();
    Wallet lender = new Wallet();
    Miner miner = new Miner();

    // User1 will have 360 coins at start, sent from the genesis wallet(lender)
    Transaction genesisTransaction = new Transaction(lender.getPublicKey(), wallets.get(0).getPublicKey(), 360, null);
    genesisTransaction.signSignature(lender.getPrivateKey());
    genesisTransaction.setTransactionId("0");
    genesisTransaction.outputs.add(new OutgoingTransaction(genesisTransaction.getReceiverAddress(), genesisTransaction.getAmount(), genesisTransaction.getTransactionId()));
    BlockChain.UnspentTransaction.put(genesisTransaction.outputs.get(0).getOutgoingTransactionId(), genesisTransaction.outputs.get(0));
    // Add it to the genesis block
    Block  genesisBlock = new Block(Constants.GENESIS_PREVIOUS_HASH);
    genesisBlock.addTransaction(genesisTransaction);
    miner.mine(genesisBlock, chain);

    Block block1 = new Block(genesisBlock.getHash());
    System.out.println("\nuser1 sends 72 coins to user2, user3, user4, user5" );
    block1.addTransaction(wallets.get(0).transferBalance(wallets.get(1).getPublicKey(), 72));
    block1.addTransaction(wallets.get(0).transferBalance(wallets.get(2).getPublicKey(), 72));
    block1.addTransaction(wallets.get(0).transferBalance(wallets.get(3).getPublicKey(), 72));
    block1.addTransaction(wallets.get(0).transferBalance(wallets.get(4).getPublicKey(), 72));
    miner.mine(block1, chain);
    System.out.println("\nBlock1 Info: \n" + block1);
    showBalances(wallets);

    Block block2 = new Block(block1.getHash());
    System.out.println("\nuser1, user2, user3, user4 sends 12 coins to user6" );
    block2.addTransaction(wallets.get(0).transferBalance(wallets.get(5).getPublicKey(), 12));
    block2.addTransaction(wallets.get(1).transferBalance(wallets.get(5).getPublicKey(), 12));
    block2.addTransaction(wallets.get(2).transferBalance(wallets.get(5).getPublicKey(), 12));
    block2.addTransaction(wallets.get(3).transferBalance(wallets.get(5).getPublicKey(), 12));
    miner.mine(block2, chain);
    System.out.println("\nBlock2 Info: \n" + block2);
    showBalances(wallets);

    // 60 60 60 60 72 48

    Block block3 = new Block(block2.getHash());
        System.out.println("\nuser5 sends 12 coins to user6 | user1 sends 1 coin to user2, user3, user4\n" );
    block3.addTransaction(wallets.get(4).transferBalance(wallets.get(5).getPublicKey(), 12));
    // 60 60 60 60 60 60
    block3.addTransaction(wallets.get(0).transferBalance(wallets.get(1).getPublicKey(), 1));
    block3.addTransaction(wallets.get(0).transferBalance(wallets.get(2).getPublicKey(), 1));
    block3.addTransaction(wallets.get(0).transferBalance(wallets.get(3).getPublicKey(), 1));
    //  57 61 61 61 60 60
    miner.mine(block2, chain);
    System.out.println("\n Block3 Info: " + block3);
    showBalances(wallets);

    System.out.println("\n Miner's reward: " + miner.getReward());
    }


    public static void showBalances(ArrayList<Wallet> wallets){
        int index = 1;
        for(Wallet wallet: wallets){
            System.out.println("User " + index++ + " has the balance: " + wallet.calculateBalance() + " coins.");
        }
    }

}
