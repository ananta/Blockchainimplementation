package com.anntz.BlockchainImplementation;

// Extra: This class is responsible for generating the hash according to our protocol: See Constants.java for the DIFFICULTY level
public class Miner {
  private int reward;

  // This will mine the block and reward the miner
  public void mine(Block block, BlockChain blockChain){
    while (!isValidHash(block)){
      block.increamentNonce();
      block.generateHash();
    }
    //System.out.println(block + ".....mined with the Hash: " + block.getHash());
    blockChain.addBlock(block);
    this.reward += Constants.REWARD;
  }

  private boolean isValidHash(Block block){
    String leadingZeros = new String(new char[Constants.DIFFICULTY]).replace('\0', '0');
    return block.getHash().substring(0, Constants.DIFFICULTY).equals(leadingZeros);
  }

  public int getReward() {
    return this.reward;
  }

}
