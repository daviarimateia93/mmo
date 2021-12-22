package com.mmo.infrastructure.security;

public interface Encryptor {

    String encrypt(String string) throws EncryptionException;
}
