package com.realdolmen.rair.data;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@ApplicationScoped
public class PasswordManager {

    private MessageDigest digest;
    private boolean initialized;

    static final int HASH_ITERATIONS = 5;

    public enum HashMode {
        RAW, BASE64;

        byte[] convert(byte[] hash) {
            if (hash == null) {
                throw new IllegalArgumentException("Hash cannot be null!");
            }
            switch (this) {
                case RAW:
                    return hash;
                case BASE64:
                    return Base64.getEncoder().encode(hash);
                default:
                    return hash;
            }
        }
    }

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
        return new BigInteger(32 * 5, SecureRandom.getInstanceStrong()).toString(32);
    }

    public byte[] hashText(String salt, String input, int iterations, HashMode mode) {

        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }
        if (!isInitialized()) {
            throw new IllegalStateException("Password Manager must be initialized first!");
        }
        StringBuilder sb = new StringBuilder();
        if (salt != null) {
            sb.append(salt);
        }

        sb.append(input);
        byte[] temp = sb.toString().getBytes();
        for (int i = 0; i < iterations; i++) {
            temp = digest.digest(temp);
        }
        return mode.convert(temp);
    }

    public byte[] hashText(String salt, String input, HashMode mode) {
        return hashText(salt, input, HASH_ITERATIONS, mode);
    }

    public byte[] hashText(String salt, String input) {
        return hashText(salt, input, HASH_ITERATIONS, HashMode.BASE64);
    }

    public byte[] hashText(String input) {
        return hashText(null, input);
    }
}
