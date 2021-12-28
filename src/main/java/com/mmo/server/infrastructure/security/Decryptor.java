package com.mmo.server.infrastructure.security;

public interface Decryptor {

    String decrypt(String string) throws DecryptionException;
}
