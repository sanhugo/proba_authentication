package ru.proba.authentication.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
@Setter
@Getter
@Validated
@Configuration
@ConfigurationProperties(prefix = "session")
public class SessionIDConfig {
        private long expiration;
}
