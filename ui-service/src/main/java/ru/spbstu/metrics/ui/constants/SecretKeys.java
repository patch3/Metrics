package ru.spbstu.metrics.ui.constants;

import java.security.SecureRandom;
import java.util.Base64;

public final class SecretKeys {

    public static final String REMEMBER_ME = generateRandomKey();

    public static final int TIME_REMEMBER = 86400;

    private static String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }
}
