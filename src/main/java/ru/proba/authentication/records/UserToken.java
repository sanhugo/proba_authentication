package ru.proba.authentication.records;

public record UserToken(String accessToken, long accessTokenExpiration) {
}
