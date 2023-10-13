package com.anntz.BlockchainImplementation;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class CryptographyHelper {
    public static KeyPair ellipticCurveCrypto(){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec params = new ECGenParameterSpec("secp192k1");
            keyPairGenerator.initialize(params, secureRandom);
            return keyPairGenerator.generateKeyPair();
        }catch(Exception ex){
            System.out.println("Error generating EC keypair");
            System.out.println(ex.getMessage());
        }
        return null;
    };


    // Signing function
    public static byte[] applyECDSASignature(PrivateKey privateKey, String data){
        Signature signature;
        byte[] output = new byte[0];
        try{
            signature = Signature.getInstance("ECDSA", "BC");
            signature.initSign(privateKey);

            byte[] strByte = data.getBytes(StandardCharsets.UTF_8);
            signature.update(strByte);
            output = signature.sign();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
        return output;
    }

    public static boolean verifyECDSASignature(PublicKey publicKey, String data, byte[] signature){
        try{
            Signature ecdsaSignature = Signature.getInstance("ECDSA", "BC");
            ecdsaSignature.initVerify(publicKey);
            ecdsaSignature.update(data.getBytes(StandardCharsets.UTF_8));
            return ecdsaSignature.verify(signature);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
