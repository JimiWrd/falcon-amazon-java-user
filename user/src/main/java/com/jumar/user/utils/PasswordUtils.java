package com.jumar.user.utils;

import jakarta.xml.bind.DatatypeConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtils {
    private PasswordUtils(){}

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] salt = saltGenerator();
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        md.update(salt);

        byte [] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        return DatatypeConverter.printHexBinary(hashBytes).toUpperCase();
    }

    private static byte[] saltGenerator() {

        return "supersecretsalt".getBytes();
    }
}
