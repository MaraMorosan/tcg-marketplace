package com.tcgmarketplace.util;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGeneratorUtil {
    public static void main(String[] args) {
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Secret key (Base64): " + base64Key);
    }
}

