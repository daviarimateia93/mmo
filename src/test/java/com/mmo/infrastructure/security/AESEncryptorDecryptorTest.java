package com.mmo.infrastructure.security;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AESEncryptorDecryptorTest {

    private static String key;
    private static AESEncryptor encryptor;
    private static AESDecryptor decryptor;
    private static String decryptedText;
    private static String encryptedText;

    @BeforeAll
    public static void setup() {
        key = "Bar12345Bar12345";
        decryptedText = "my cool string";
        encryptedText = "2tWOHxmfUw0LfGHCJ67UpQ==";

        encryptor = AESEncryptor.builder()
                .key(key)
                .build();

        decryptor = AESDecryptor.builder()
                .key(key)
                .build();
    }

    @Test
    public void encrypt() {
        String result = encryptor.encrypt(decryptedText);

        assertThat(result, equalTo(encryptedText));
    }

    @Test
    public void decrypt() {
        String result = decryptor.decrypt(encryptedText);

        assertThat(result, equalTo(decryptedText));
    }
}
