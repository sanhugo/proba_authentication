package ru.proba.authentication.controller;

import com.github.f4b6a3.uuid.UuidCreator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.AuthenticationApi;
import ru.proba.authentication.generated.model.Token;
import ru.proba.authentication.generated.model.UserLoginDto;
import ru.proba.authentication.records.UserTokens;
import ru.proba.authentication.service.AuthService;
import ru.proba.authentication.tokens.JWTRefreshConfig;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LoginLogoutController implements AuthenticationApi {

    AuthService service;
    JWTRefreshConfig config;

    @Override
    public ResponseEntity<Token> login(UserLoginDto body) {
        String session_id = String.valueOf(UuidCreator.getTimeOrderedEpoch());
        UserTokens ut = service.createTokens(body, session_id);
        ResponseCookie rc = ResponseCookie.from("refreshToken", ut.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("strict")
                .path("/")
                .maxAge(60L * 60 * 24 * config.getRefreshExpiration())
                .build();
        return ResponseEntity
                .ok()
//                .header("Access-Control-Allow-Credentials", "true")
                //include when frontend is ready
                .header("Set-Cookie", rc.toString())
                .body(new Token(ut.accessToken()));
    }

    @Override
    public ResponseEntity<Void> logoutUser(String refreshToken, Token accessToken) {
        service.logout(refreshToken, accessToken);
        return ResponseEntity.accepted().build();
    }
}
