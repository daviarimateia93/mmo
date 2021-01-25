package com.mmo.core.security;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import lombok.Builder;
import lombok.NonNull;

public class Decryptor {

    private static final String CIPHER_TRANSFORMATION = "AES";

    private final Cipher cipher;

    @Builder
    private Decryptor(@NonNull String key) {
        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), CIPHER_TRANSFORMATION);
            cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
        } catch (Exception exception) {
            throw new CipherInitializationException(exception, "Failed to init cipher in decrypt mode");
        }
    }

    public String decrypt(String string) {
        try {
            byte[] b64Decoded = Base64.getDecoder().decode(string);
            byte[] aesDecoded = cipher.doFinal(b64Decoded);

            return new String(aesDecoded);
        } catch (Exception exception) {
            throw new DecryptionException(exception, "Failed to decrypt");
        }
    }
}
