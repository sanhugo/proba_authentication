package ru.proba.authentication.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.AuthenticationApi;
import ru.proba.authentication.generated.model.Token;
import ru.proba.authentication.generated.model.UserLoginDto;


@RestController
public class LoginLogoutController implements AuthenticationApi {

    @Override
    public Token getToken(UserLoginDto body) {
        return null;
    }

    @Override
    public void logoutUser(String refreshToken) {

    }
}
