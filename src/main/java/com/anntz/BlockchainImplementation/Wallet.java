package com.anntz.BlockchainImplementation;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wallet {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    public Wallet(){
        KeyPair keyPair = CryptographyHelper.ellipticCurveCrypto();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    public PrivateKey getPrivateKey(){
        return this.privateKey;
    }
    public PublicKey getPublicKey(){
        return this.publicKey;
    }

    public double calculateBalance() {
        double balance = 0;
        for(Map.Entry<String, OutgoingTransaction> item: BlockChain.UnspentTransaction.entrySet()){
            OutgoingTransaction outgoingTransaction = item.getValue();
            if(outgoingTransaction.getReceiver() == publicKey){
                balance = balance + outgoingTransaction.getAmount();
            }
        }
        return balance;
    }

    public Transaction transferBalance(PublicKey reciverAddress, double amount){
        if (calculateBalance() < amount){
            System.out.println("Insufficient Balance");
            return null;
        }

        List<IncomingTransaction> inputs = new ArrayList<IncomingTransaction>();
        for (Map.Entry<String, OutgoingTransaction> item: BlockChain.UnspentTransaction.entrySet()){
            OutgoingTransaction unspentTransaction = item.getValue();

            if(unspentTransaction.getReceiver() == this.publicKey){
                inputs.add(new IncomingTransaction((unspentTransaction.getOutgoingTransactionId())));
            }

        }

        Transaction newTransaction = new Transaction(publicKey, reciverAddress, amount, inputs);

        newTransaction.signSignature(privateKey);

        return newTransaction;
    }
}
