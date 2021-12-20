package com.mmo.core.security;

public interface Decryptor {

    String decrypt(String string) throws DecryptionException;
}
