/*
 * EncryptService.java
 *
 * @formatter:off
 *
 * Copyright Vistex UK Ltd
 *
 * @author Andrew
 *
 * Created: 16 Jan 2019
 *
 * Change Log:
 *
 * Date        Developer     Job No.  Job Description
 *
 *
 * @formatter:on
 */

package com.github.awferg.restex.service;

import org.slf4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EncryptService implements EncryptDecrypt<String, String> {

    // just need Symmetric we will hide the key somewhere
    private static final String ALGORITHM = "AES";

    private static final String TRANSFORMATION = "AES/CBC/PKCS5PADDING";
    private static final int BLOCK_SIZE = 128;// 128 will do for this
    private static final byte[] KEY;
    private static final String IV_DATA_SEP = "#";

    static {
        // TODO complete key management, not in static block
        final String key = EncryptService.getKey();
        if (key == null) {

            // create if no key
            KeyGenerator keygen;
            try {
                keygen = KeyGenerator.getInstance(EncryptService.ALGORITHM);
            } catch (final NoSuchAlgorithmException exception) {
                // TODO re-throw for now, key wouldn't be got here normally
                throw new IllegalStateException(
                        "You can't do " + EncryptService.ALGORITHM + ", sorry", exception);
            }
            keygen.init(EncryptService.BLOCK_SIZE);
            // make up key
            KEY = keygen.generateKey().getEncoded();
            // TODO save key for restart
        } else {
            KEY = key.getBytes();
        }
    }

    private static String getKey() {

        // TODO get key from storage
        return "‘:”ŽŸö»ìhà‡ÝytÞs";
    }

    private final Logger logger;
    private final SecretKeySpec secretKey;

    private SecureRandom secureRandom;

    public EncryptService() {

        this.logger = null;
        this.secretKey = null;
    }

    @Inject
    public EncryptService(final Logger logger) {

        super();
        this.logger = logger;
        this.secretKey = new SecretKeySpec(EncryptService.KEY, EncryptService.ALGORITHM);
    }

    /**
     * Decrypts the given encrypted text
     *
     * @param encryptedText
     *            The data to decrypt
     */
    @Override
    public String decrypt(final String encryptedText) {

        if (encryptedText == null) {
            return null;
        }

        final byte[] byteDecrypted = this.decryptToBytes(encryptedText);

        final String decrypted = new String(byteDecrypted, StandardCharsets.UTF_8);
        this.logger.trace("decrypt decrypted {}", decrypted);

        return decrypted;
    }

    public byte[] decryptToBytes(final String encryptedText) {

        if (encryptedText == null) {
            return new byte[0];
        }

        final String[] parts = encryptedText.split(EncryptService.IV_DATA_SEP);
        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    String.format("Decrypt parameter %s is invalid", encryptedText));
        }
        final byte[] initializationVector = Base64.getDecoder().decode(parts[0]);
        this.logger.trace("decrypt initializationVector {}", initializationVector);
        final byte[] byteEncrypted = Base64.getDecoder().decode(parts[1]);
        this.logger.trace("decrypt byteEncrypted {}", byteEncrypted);

        final byte[] byteDecrypted;
        try {
            final Cipher cipher = Cipher.getInstance(EncryptService.TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, this.secretKey,
                    new IvParameterSpec(initializationVector));

            byteDecrypted = cipher.doFinal(byteEncrypted);
            this.logger.trace("decrypt byteDecrypted {}", byteDecrypted);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException exception) {

            this.logger.error("Failed to get cipher", exception);
            throw new IllegalStateException(
                    String.format("You can't do %s, sorry", EncryptService.TRANSFORMATION),
                    exception);

        } catch (final InvalidKeyException | InvalidAlgorithmParameterException exception) {

            this.logger.error("Failed to initialise cipher", exception);
            throw new IllegalStateException("Failed to initialise cipher", exception);

        } catch (IllegalBlockSizeException | BadPaddingException exception) {

            this.logger.error("Failed to do decryption of {}", encryptedText, exception); // might not want to log sensitive
                                                                                          // info
            throw new IllegalStateException("Failed to do decryption", exception);
        }

        return byteDecrypted;
    }

    public String encrypt(final byte[] byteOriginal) {

        if (byteOriginal.length == 0) {
            return null;
        }

        final byte[] initializationVector = this.getInitializationVector();

        final String encrypted;
        try {
            final Cipher cipher = Cipher.getInstance(EncryptService.TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey,
                    new IvParameterSpec(initializationVector));

            this.logger.trace("encrypt byteDecrypted {}", byteOriginal);

            final byte[] byteEncrypted = cipher.doFinal(byteOriginal);
            this.logger.trace("encrypt byteEncrypted {}", byteEncrypted);

            encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
            this.logger.trace("encrypt encrypted {}", encrypted);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException exception) {

            this.logger.error("Failed to get cipher", exception);
            throw new IllegalStateException(
                    String.format("You can't do %s, sorry", EncryptService.TRANSFORMATION),
                    exception);

        } catch (final InvalidKeyException | InvalidAlgorithmParameterException exception) {

            this.logger.error("Failed to intialise cipher", exception);
            throw new IllegalStateException("Failed to intialise cipher", exception);

        } catch (IllegalBlockSizeException | BadPaddingException exception) {

            this.logger.error("Failed to do encryption", exception); // might not want to log sensitive info
            throw new IllegalStateException("Failed to do encryption", exception);
        }

        return Base64.getEncoder().encodeToString(initializationVector) + EncryptService.IV_DATA_SEP
                + encrypted;
    }

    /**
     * Encrypts the given plain text
     *
     * @param plainText
     *            The plain text to encrypt
     * @return encrypted string
     */
    @Override
    public String encrypt(final String plainText) {

        if (plainText == null) {
            return null;
        }

        return this.encrypt(plainText.getBytes(StandardCharsets.UTF_8));
    }

    private byte[] getInitializationVector() {

        final byte[] initializationVector = new byte[EncryptService.BLOCK_SIZE / 8];
        this.getSecureRandom().nextBytes(initializationVector);
        this.logger.trace("encrypt initializationVector {}", initializationVector);

        return initializationVector;
    }

    private SecureRandom getSecureRandom() {

        if (this.secureRandom == null) {
            try {
                this.secureRandom = SecureRandom.getInstanceStrong();
            } catch (final NoSuchAlgorithmException exception) {
                this.logger.error("Failed to get secure random instance", exception);
                throw new IllegalStateException("Failed to get secure random instance", exception);
            }
        }

        return this.secureRandom;
    }
}