package com.anntz.BlockchainImplementation;

import java.util.ArrayList;
import java.util.List;

public class MerkleTree {

  private List<String> transactions;

  public MerkleTree(List<String> transactions){
    this.transactions = transactions;
  }

  //getMerkelRoot: List
  public List<String> getRoot(){
    return construct(this.transactions);
  }

  // Recursive
  private List<String> construct(List<String> transactions){
    // if there's single item, we're at the root of the tree, return the item
    if(transactions.size() == 1) return transactions;

    List<String> updatedList = new ArrayList<>();
    // merge neighbouring items
    for (int i =0; i<transactions.size()-1; i= i+2){
      updatedList.add(mergeHash(transactions.get(i), transactions.get(i+1)));
    }

    // incase of odd trnasactions, the last item is hashed itself
    if (transactions.size()%2 == 1){
      updatedList.add(mergeHash(transactions.get(transactions.size()-1), transactions.get(transactions.size() - 1)));
    }

    // recursive call until the root is found
    return construct(updatedList);
  }

  // combine the hash values and get string hash
  private String mergeHash(String firstHash, String secondHash){
    StringBuilder sb = new StringBuilder();
    sb.append(firstHash);
    sb.append(secondHash);
    return SHA256Helper.hash(sb.toString());
  }
}
