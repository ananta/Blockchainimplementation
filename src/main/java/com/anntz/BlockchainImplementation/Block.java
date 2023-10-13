package com.anntz.BlockchainImplementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {
    private String hash;
    private int number;
    private String previousHash;
    private List<Transaction> transactions;
    private long timeStamp;

    private int nonce;

    public Block(String previousHash){
        this.transactions = new ArrayList<Transaction>();
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        generateHash();
    }

    // Generate hash from available data
    public void generateHash() {
        String dataToHash = Integer.toString(number) + previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + transactions.toString();
        this.hash = SHA256Helper.hash(dataToHash);
    }

    // For miners to generate our block according to the Difficulty level
    public void increamentNonce(){
        this.nonce = this.nonce + 1;
    }

    public String getPreviousHash(){
        return this.previousHash;
    }

    public void setPreviousHash(String hash){
        this.previousHash = hash;
    }

    public String getHash(){
        return this.hash;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

    public boolean addTransaction(Transaction transaction){
        if(transaction == null) return false;

        if((!previousHash.equals(Constants.GENESIS_PREVIOUS_HASH))){
            if((!transaction.verifyTransacttion())){
                System.out.println("Invalid Transaction.... Ignoring");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction "+ transaction.getTransactionId() + " is valid & it's added to the block");
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block{");
        sb.append("hash='").append(hash).append('\'');
        sb.append(", number=").append(number);
        sb.append(", previousHash='").append(previousHash).append('\'');
        sb.append(", transactions=[\n");
        for (Transaction transaction : transactions) {
            sb.append("TransactionID: ").append(transaction.getTransactionId());
            sb.append(" Transaction Amount: ").append(transaction.getAmount());
            sb.append(", \n");
        }
        sb.append("]\n");
        sb.append(", timeStamp=").append(timeStamp);
        sb.append(", nonce=").append(nonce);
        sb.append('}');
        return sb.toString();
    }
}
