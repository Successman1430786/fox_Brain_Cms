package com.foxbrain.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHashUtil {

    // PBKDF2 configuration
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    private PasswordHashUtil() {
        // Utility class - prevent object creation
    }

    /**
     * Generates a secure password hash.
     *
     * Stored format:
     * iterations:salt:hash
     */
    public static String hashPassword(String password) {

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        try {
            // Generate random salt
            byte[] salt = new byte[SALT_LENGTH];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);

            // Generate hash
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(ALGORITHM);

            byte[] hash = factory.generateSecret(spec).getEncoded();

            spec.clearPassword();

            // Convert to Base64
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            return ITERATIONS + ":" + saltBase64 + ":" + hashBase64;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

            throw new IllegalStateException(
                    "Error while hashing password", e
            );
        }
    }

    /**
     * Verifies a plain-text password against a stored hash.
     */
    public static boolean verifyPassword(
            String password,
            String storedHash) {

        if (password == null || storedHash == null) {
            return false;
        }

        try {
            // Split stored value
            String[] parts = storedHash.split(":");

            if (parts.length != 3) {
                return false;
            }

            int iterations = Integer.parseInt(parts[0]);

            byte[] salt =
                    Base64.getDecoder().decode(parts[1]);

            byte[] expectedHash =
                    Base64.getDecoder().decode(parts[2]);

            // Generate hash using same salt
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    iterations,
                    expectedHash.length * 8
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(ALGORITHM);

            byte[] actualHash =
                    factory.generateSecret(spec).getEncoded();

            spec.clearPassword();

            // Constant-time comparison
            return constantTimeEquals(
                    expectedHash,
                    actualHash
            );

        } catch (
                NoSuchAlgorithmException |
                InvalidKeySpecException |
                IllegalArgumentException e) {

            return false;
        }
    }

    /**
     * Constant-time byte comparison.
     */
    private static boolean constantTimeEquals(
            byte[] a,
            byte[] b) {

        if (a == null || b == null) {
            return false;
        }

        if (a.length != b.length) {
            return false;
        }

        int result = 0;

        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }

        return result == 0;
    }
}