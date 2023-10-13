package com.anntz.BlockchainImplementation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BlockChain {
    public static List<Block> blockChain;
    public static Map<String, OutgoingTransaction> UnspentTransaction;

    public BlockChain(){
        BlockChain.blockChain = new LinkedList<>();
        BlockChain.UnspentTransaction = new HashMap<String, OutgoingTransaction>();
    }

    public void addBlock(Block newBlock) {
        BlockChain.blockChain.add(newBlock);
    }

    public int getSize() {
        return BlockChain.blockChain.size();
    }

    @Override
    public String toString() {
        StringBuilder blockString = new StringBuilder();
        for (Block _block: BlockChain.blockChain){
            blockString.append(_block.toString()).append("\n");
        }
        return blockString.toString();
    }
}
