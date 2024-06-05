package ru.spbstu.metrics.api.constants;

import ru.spbstu.metrics.api.utils.GeneratorString;

public final class SecretKeys {

    public static final String REMEMBER_ME_KEY = GeneratorString.generateRandomKey();

    public static final int TIME_REMEMBER = 86400;

}
