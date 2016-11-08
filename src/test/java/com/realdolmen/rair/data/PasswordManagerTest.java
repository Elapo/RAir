package com.realdolmen.rair.data;

import org.junit.Test;

import java.security.MessageDigest;
import java.util.Base64;

import static org.junit.Assert.*;

public class PasswordManagerTest {
    @Test
    public void randomSaltReturnsRandomSaltForEveryCall() throws Exception {
        String salt = new PasswordManager().getRandomSalt();
        assertNotNull(salt);
        assertEquals(32, salt.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertNullBytesThrowsIllegalArgumentException() throws Exception {
        PasswordManager.HashMode.BASE64.convert(null);
        fail("Converting null array should throw IAE");
    }

    @Test
    public void hashTextWithNullSaltProducesHashWithoutSalt() throws Exception {
        String text = new String(MessageDigest.getInstance("SHA-256").digest("Test".getBytes()));
        String testText = new String(new PasswordManager().hashText(null, "Test", 1, PasswordManager.HashMode.RAW));
        assertEquals(text, testText);
    }

    @Test
    public void hashTextWithSaltProducesHashWithSalt() throws Exception {
        String salt = "Hello";
        String text = new String(MessageDigest.getInstance("SHA-256").digest("Test".getBytes())); //Hash without salt
        String testText = new String(new PasswordManager().hashText(salt, "Test", 1, PasswordManager.HashMode.RAW));
        assertNotEquals(text, testText);
    }

    @Test(expected = IllegalArgumentException.class)
    public void hashTextWithNullInputThrowsIllegalArgumentEx() throws Exception {
        new PasswordManager().hashText(null);
        fail("Hashing null should not be a thing!");
    }

    @Test
    public void hashTextIteratesXTimes() throws Exception {
        byte[] temp = "Test".getBytes();
        PasswordManager pm = new PasswordManager();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        for (int i = 0; i < PasswordManager.HASH_ITERATIONS; i++) {
            temp = md.digest(temp);
        }
        assertEquals(new String(temp), new String(pm.hashText(null, "Test", PasswordManager.HashMode.RAW)));
    }

    @Test
    public void hashTextCreatesBase64Hash() throws Exception {
        String text = new String(MessageDigest.getInstance("SHA-256").digest("Test".getBytes()));
        String testText = new String(PasswordManager.HashMode.BASE64.convert(text.getBytes()));
        assertEquals(new String(Base64.getEncoder().encode(text.getBytes())), testText);
    }
}