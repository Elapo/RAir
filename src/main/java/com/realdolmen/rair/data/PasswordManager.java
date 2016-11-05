package com.realdolmen.rair.data;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordManager {

    private MessageDigest digest;
    private boolean initialized;

    public PasswordManager() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
            initialized = true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    public String getRandomSalt() throws NoSuchAlgorithmException {
        if (!isInitialized()) {
            return null;
        }
        return new BigInteger(32 * 8, SecureRandom.getInstanceStrong()).toString(32);
    }

    public byte[] hashText(String salt, String input) {
        StringBuilder sb = new StringBuilder();
        if (salt != null) {
            sb.append(salt);
        }

        sb.append(input);
        return digest.digest(sb.toString().getBytes());
    }

    public byte[] hashText(String input) {
        return hashText(null, input);
    }
}
