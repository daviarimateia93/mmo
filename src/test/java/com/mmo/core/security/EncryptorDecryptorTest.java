package com.mmo.core.security;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EncryptorDecryptorTest {

    private static String key;
    private static Encryptor encryptor;
    private static Decryptor decryptor;
    private static String decryptedText;
    private static String encryptedText;

    @BeforeAll
    public static void setup() {
        key = "Bar12345Bar12345";
        decryptedText = "my cool string";
        encryptedText = "2tWOHxmfUw0LfGHCJ67UpQ==";

        encryptor = Encryptor.builder()
                .key(key)
                .build();

        decryptor = Decryptor.builder()
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
