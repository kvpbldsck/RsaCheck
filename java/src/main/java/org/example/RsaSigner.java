package org.example;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RsaSigner {
    
    private static Signature signature;
    
    static {
        try {
            signature = Signature.getInstance("SHA256WithRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    private final String secret;
    
    public RsaSigner(String secret) {
        this.secret = secret;
    }
    
    public String makeBase64Signature(String message) throws Exception {
        var signature = makeSignature(message);
        return Base64.getEncoder().encodeToString(signature);
    }
    
    public byte[] makeSignature(String message) throws Exception {
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        signature.initSign(readPrivateKey(secret));
        signature.update(data);
        return signature.sign();
    }

    private PrivateKey readPrivateKey(String secret) throws Exception {
        String privateKeyPEM = secret
                .replaceAll("-----[A-Z ]+-----", "")
                .replaceAll("\\s+", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }
}
