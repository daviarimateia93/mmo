package com.mmo.server.infrastructure.security;

public interface Encryptor {

    String encrypt(String string) throws EncryptionException;
}
