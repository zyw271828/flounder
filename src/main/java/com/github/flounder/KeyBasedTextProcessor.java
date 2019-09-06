package com.github.flounder;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;

public class KeyBasedTextProcessor {
    public static String decryptText(String text, String keyPath, String passphrase)
            throws IOException, NoSuchProviderException, PGPException {
        try (PrintWriter out = new PrintWriter("temp.txt.asc")) {
            out.print(text);
        }

        Security.addProvider(new BouncyCastleProvider());
        KeyBasedFileProcessor.decryptFile("temp.txt.asc", keyPath, passphrase.toCharArray(), "temp.txt");
        String result = Files.readString(Paths.get("temp.txt"), StandardCharsets.UTF_8);

        Files.deleteIfExists(Paths.get("temp.txt"));
        Files.deleteIfExists(Paths.get("temp.txt.asc"));

        return result;
    }

    public static String encryptText(String text, String keyPath)
            throws IOException, NoSuchProviderException, PGPException {
        try (PrintWriter out = new PrintWriter("temp.txt")) {
            out.print(text);
        }

        Security.addProvider(new BouncyCastleProvider());
        KeyBasedFileProcessor.encryptFile("temp.txt.asc", "temp.txt", keyPath, true, true);
        String result = Files.readString(Paths.get("temp.txt.asc"), StandardCharsets.UTF_8);

        Files.deleteIfExists(Paths.get("temp.txt"));
        Files.deleteIfExists(Paths.get("temp.txt.asc"));

        return result;
    }
}
