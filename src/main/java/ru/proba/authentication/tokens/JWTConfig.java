package ru.proba.authentication.tokens;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.access")
public class JWTConfig {
    private String secret;
    private long accessExpiration;
}
