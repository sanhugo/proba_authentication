package ru.proba.authentication.email;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Validated
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfig {
    private String from;
    private String baseURL;
    private String port;
}
