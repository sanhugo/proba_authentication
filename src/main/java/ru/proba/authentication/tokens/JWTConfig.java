package ru.proba.authentication.tokens;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.access")
public class JWTConfig {
    private String secret;
    private long accessExpiration;
}
