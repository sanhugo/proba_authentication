package ru.proba.authentication.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.InternalAuthApi;
import ru.proba.authentication.records.UserToken;
import ru.proba.authentication.service.AuthService;
import ru.proba.authentication.service.RedisService;
import ru.proba.authentication.session.SessionIDConfig;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InternalController implements InternalAuthApi {

    AuthService service;
    SessionIDConfig config;
    RedisService redisService;

    @Override
    public ResponseEntity<Void> updateTokens(String session_id) {
        UserToken tokens = service.update(session_id);
        redisService.saveTokens(tokens, session_id);
        ResponseCookie cookie = ResponseCookie.from("session_id", session_id)
                .path("/api/")
                .httpOnly(true)
                .maxAge(config.getExpiration()*60L*60*24)
                .sameSite("Strict")
                .secure(true)
                .build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
