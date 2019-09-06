package com.github.flounder;

import java.io.IOException;
import java.security.NoSuchProviderException;

import org.bouncycastle.openpgp.PGPException;

public class KeyBasedTextProcessor {
    public static String decryptText(String text, String keyPath, String passphrase)
            throws IOException, NoSuchProviderException, PGPException {
        return "foobar";
    }

    public static String encryptText(String text, String keyPath)
            throws IOException, NoSuchProviderException, PGPException {
        return "foobar";
    }
}