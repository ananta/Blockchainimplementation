package com.anntz.BlockchainImplementation;

public class Constants {
    private  Constants() {
    }

    // Just for our protocol, the amount of zeros to be a valid hash for the miners
    // For difficulty 10, the hash will be something like "0000000000e9182374987243abc978987dddd"
    public static final int DIFFICULTY = 4;

    // Reward, unlike bitcoin, which halves at certain interval, let's just add it as static
    public static final double REWARD = 12.5;

    // For the first block with the hash 64 0s
    public static final String GENESIS_PREVIOUS_HASH = "0000000000000000000000000000000000000000000000000000000000000000";
}
