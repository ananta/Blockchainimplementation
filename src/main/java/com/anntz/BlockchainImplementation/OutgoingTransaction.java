package com.anntz.BlockchainImplementation;

import java.security.PublicKey;

public class OutgoingTransaction {
    // transactiont output id
    private String outgoingTransactionId;
    // parent transaction id
    private String parentTransactionId;
    // new owner of the coin
    private PublicKey receiver;
    private double amount;

    public OutgoingTransaction(PublicKey receiver, double amount, String parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
        this.receiver = receiver;
        this.amount = amount;
        generateId();
    }

    private void generateId(){
        this.outgoingTransactionId = SHA256Helper.hash(receiver.toString()+Double.toString(amount) + parentTransactionId);
    }

    public double getAmount(){
        return this.amount;
    }

    public PublicKey getReceiver(){
        return this.receiver;
    }

    public String getOutgoingTransactionId(){
        return this.outgoingTransactionId;
    }

}
