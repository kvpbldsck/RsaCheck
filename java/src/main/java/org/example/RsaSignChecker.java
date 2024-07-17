package org.example;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaSignChecker {

    private static Signature signature;

    static {
        try {
            signature = Signature.getInstance("SHA256WithRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private final ServerRequestSignatureHolder signatureHolder;
    private final String secret;

    public RsaSignChecker(ServerRequestSignatureHolder signatureHolder, String secret) {
        this.signatureHolder = signatureHolder;
        this.secret = secret;
    }

    public boolean validate(String payload) throws Exception {
        if (signatureHolder.getSignature() == null) {
            throw new Exception("Invalid signature");
        }

        final byte[] sign = signatureHolder.getSignature();

        byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);

        signature.initVerify(readPublicKey(secret));
        
        signature.update(payloadBytes);
        
        return signature.verify(sign);
    }

    private PublicKey readPublicKey(String secret) throws Exception {
        String publicKeyPEM = secret
                .replaceAll("-----[A-Z ]+-----", "")
                .replaceAll("\\s+", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return keyFactory.generatePublic(keySpec);
    }
}

