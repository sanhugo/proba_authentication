package ru.proba.authentication.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.InternalAuthApi;
import ru.proba.authentication.generated.model.Token;
import ru.proba.authentication.records.UserTokens;
import ru.proba.authentication.service.AuthService;
import ru.proba.authentication.tokens.JWTRefreshConfig;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InternalController implements InternalAuthApi {

    AuthService service;
    JWTRefreshConfig config;

    @Override
    public ResponseEntity<Token> updateTokens(String refreshToken) {
        UserTokens tokens = service.update(refreshToken);
        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokens.refreshToken())
                .path("/")
                .httpOnly(true)
                .maxAge(config.getRefreshExpiration()*60L*60*24)
                .sameSite("Strict")
                .secure(true)
                .build();
        return ResponseEntity
                .ok()
                .header("Set-Cookie", cookie.toString())
                .body(new Token(tokens.accessToken()));
    }
}
