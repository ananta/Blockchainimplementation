package com.anntz.BlockchainImplementation;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SHA256Helper {
    // Returns 64 character string
    public static String hash(String data){
        try {
            // Ref: https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/security/MessageDigest.html
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            // convert the bytes to hex values
            StringBuilder hexaDecimalString = new StringBuilder();
            for(int i=0; i<hash.length; i++){
                // convert bytes to hexadecimal representation.
                // Get positive integer value from the bytes
                    // if negative, AND with 0xff to convert to positive int: -5 & 0xff = 251 = 256 - 5
                // convert the integer to hexadecimal string
                String hexString =  Integer.toHexString(0xff & hash[i]);
                // padding if the generated hex string is a single character, let's make it double. This will result in 64 character string
                // if hexString = 5, hexString should append 05 instead of 5
                if (hexString.length() == 1) hexaDecimalString.append('0');
                hexaDecimalString.append(hexString);
            }
            return hexaDecimalString.toString();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    };

    public static boolean compare(String string, String _hash){
        return string.equals(hash(_hash));
    };
}
