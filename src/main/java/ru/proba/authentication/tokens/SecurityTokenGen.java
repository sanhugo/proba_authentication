package ru.proba.authentication.tokens;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class SecurityTokenGen {
    private static final SecureRandom secureRandom = new SecureRandom();

    private static final Base64.Encoder urlEncoder = Base64.getUrlEncoder().withoutPadding();

    public String generateSessionId() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return urlEncoder.encodeToString(randomBytes);
    }
}
