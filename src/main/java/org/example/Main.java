package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        var message = readResource("message.json");
        var privateKey = readResource("privateKey");
        
        var signer = new RsaSigner(privateKey);
        
        System.out.println(signer.makeBase64Signature(message));
    }
    
    private static String readResource(String resourceName) throws Exception {
        var resource = Main.class.getClassLoader().getResourceAsStream(resourceName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8))) {
            String content = reader.lines().collect(Collectors.joining(System.lineSeparator()));

            return content;
        } 
    }
}
