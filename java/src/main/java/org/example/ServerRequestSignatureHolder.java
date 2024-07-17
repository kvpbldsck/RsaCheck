package org.example;

import java.util.Base64;

public class ServerRequestSignatureHolder {
    private String base64Signature;


    public ServerRequestSignatureHolder(String base64Signature) {
        this.base64Signature = base64Signature;
    }
    
    public String getBase64Signature() {
        return base64Signature;
    }
    
    public byte[] getSignature() {
        return Base64.getDecoder().decode(base64Signature);
    }
}
