package ru.proba.authentication.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.AuthenticationApi;
import ru.proba.authentication.generated.model.UserLoginDto;
import ru.proba.authentication.records.UserToken;
import ru.proba.authentication.service.AuthService;
import ru.proba.authentication.service.RedisService;
import ru.proba.authentication.session.SessionIDConfig;
import ru.proba.authentication.tokens.SecurityTokenGen;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LoginLogoutController implements AuthenticationApi {

    AuthService service;
    SessionIDConfig config;
    RedisService rs;
    SecurityTokenGen gen;

    @Override
    public ResponseEntity<Void> login(UserLoginDto body) {
        String session_id = gen.generateSessionId();
        UserToken ut = service.createToken(body);
        rs.saveToken(ut,session_id);
        ResponseCookie rc = ResponseCookie.from("session_id", session_id)
                .httpOnly(true)
                .secure(true)
                .sameSite("strict")
                .path("/api/")
                .maxAge(config.getExpiration()*60L*60*24)
                .build();
        return ResponseEntity
                .ok()
//                .header("Access-Control-Allow-Credentials", "true")
                //include when frontend is ready
                .header(HttpHeaders.SET_COOKIE, rc.toString())
                .build();
    }

    @Override
    public ResponseEntity<Void> logoutUser(String session_id) {
        service.logout(session_id);
        return ResponseEntity.ok().build();
    }
}
