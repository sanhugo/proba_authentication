package ru.proba.authentication.tokens;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.refresh")
public class JWTRefreshConfig {
    private String privateKey;
    private String publicKey;
    private int refreshExpiration;
}
