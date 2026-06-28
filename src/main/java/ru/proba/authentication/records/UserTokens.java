package ru.proba.authentication.records;

import ru.proba.authentication.generated.model.UserLoginDto;

public record UserTokens(String accessToken, String refreshToken) {
}
