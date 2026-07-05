package ru.proba.authentication.records;

import jakarta.validation.constraints.NotBlank;

public record AccessToken(@NotBlank String token, long expiration) {
}
