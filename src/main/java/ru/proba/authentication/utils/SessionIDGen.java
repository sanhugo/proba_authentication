package ru.proba.authentication.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.Base64;

@UtilityClass
public class SessionIDGen {
    private static final SecureRandom secureRandom = new SecureRandom();

    private static final Base64.Encoder urlEncoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateSessionId() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return urlEncoder.encodeToString(randomBytes);
    }
}
