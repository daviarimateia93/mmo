package com.mmo.core.security;

public interface Encryptor {

    String encrypt(String string) throws EncryptionException;
}
