package com.mmo.infrastructure.security;

public interface Decryptor {

    String decrypt(String string) throws DecryptionException;
}
