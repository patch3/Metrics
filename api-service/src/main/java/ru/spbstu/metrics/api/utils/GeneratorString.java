package ru.spbstu.metrics.api.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class GeneratorString {
    public static String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
