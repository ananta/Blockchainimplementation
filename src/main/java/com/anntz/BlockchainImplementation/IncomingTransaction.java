package com.anntz.BlockchainImplementation;

// Suppose Adam sent me => 0.2 CLEM_COIN
// This is incoming transaction
// this will have transaction output id
public class IncomingTransaction {
    private String outgoingTransactionId;
    private String Id;
    private OutgoingTransaction unspentTransaction;

    public IncomingTransaction(String outgoingTransactionOutputId){
        this.outgoingTransactionId = outgoingTransactionOutputId;
    }

    public String getOutgoingTransactionId(){
        return this.outgoingTransactionId;
    }

    public String getId(){
        return this.Id;
    }

    public void setTransactionOutputId(String transactionOutputId){
        this.outgoingTransactionId = transactionOutputId;
    }

    public OutgoingTransaction getUnspentTransaction(){
        return this.unspentTransaction;
    }

    public void setUnspentTransaction(OutgoingTransaction unspentTransaction){
        this.unspentTransaction = unspentTransaction;
    }
}
