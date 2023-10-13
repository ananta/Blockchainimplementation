package com.anntz.BlockchainImplementation;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    public String getTransactionId() {
        return transactionId;
    }

    private String transactionId;

    public void setSenderAddress(PublicKey senderAddress) {
        this.senderAddress = senderAddress;
    }

    public void setReceiverAddress(PublicKey receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // We can also use the public key, but let's try with the wallet address
    private PublicKey senderAddress;
    private PublicKey receiverAddress;
    private double amount;
    private byte[] signature;

    public PublicKey getSenderAddress() {
        return senderAddress;
    }

    public PublicKey getReceiverAddress() {
        return receiverAddress;
    }

    public double getAmount() {
        return amount;
    }

    public List<IncomingTransaction> inputs;
    public List<OutgoingTransaction> outputs;

    public Transaction(PublicKey senderAddress, PublicKey receiverAddress, double amount, List<IncomingTransaction> inputs){
        this.inputs = new ArrayList<IncomingTransaction>();
        this.outputs = new ArrayList<OutgoingTransaction>();
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.amount = amount;
        this.inputs = inputs;
        calculateHash();
    }
    // verify hash
    // calculate hash
    private void calculateHash() {
        String hash = senderAddress.toString()+receiverAddress.toString()+Double.toString(amount);
        this.transactionId = SHA256Helper.hash(hash);
    }

    public void setTransactionId(String transactionId){
        this.transactionId = transactionId;
    }

    public void signSignature(PrivateKey privateKey){
        String data = senderAddress.toString() + receiverAddress.toString() + Double.toString(amount);
        this.signature = CryptographyHelper.applyECDSASignature(privateKey, data);
    }

    public boolean verifySignature(){
        String data = senderAddress.toString() + receiverAddress.toString() + Double.toString(amount);
        return CryptographyHelper.verifyECDSASignature(senderAddress, data, signature);
    }

    public boolean verifyTransacttion() {
        if (!verifySignature()){
            System.out.println("Invalid transaction, Invalid Signature...");
        }

        // Unspent transactions from the chain
        for(IncomingTransaction incomingTransaction: inputs){
            incomingTransaction.setUnspentTransaction(BlockChain.UnspentTransaction.get(incomingTransaction.getOutgoingTransactionId()));
        }

        // send the amount to the receiver
        outputs.add(new OutgoingTransaction(this.receiverAddress, amount, transactionId));
        // send the balance-amount to the sender
        outputs.add(new OutgoingTransaction(this.senderAddress, getInputsSum() - amount, transactionId ));

        for (IncomingTransaction incomingTransaction: inputs){
            if(incomingTransaction.getUnspentTransaction() != null){
                BlockChain.UnspentTransaction.remove(incomingTransaction.getUnspentTransaction().getOutgoingTransactionId());
            }
        }

        for (OutgoingTransaction outgoingTransaction: outputs){
            BlockChain.UnspentTransaction.put(outgoingTransaction.getOutgoingTransactionId(), outgoingTransaction);
        }
        return true;
    }

    public double getInputsSum(){
        double sum = 0;

        for(IncomingTransaction incomingTransaction: inputs){
            if(incomingTransaction.getUnspentTransaction() != null){
                sum = sum + incomingTransaction.getUnspentTransaction().getAmount();
            }
        }
        return sum;
    }

}
