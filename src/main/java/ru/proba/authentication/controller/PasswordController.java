package ru.proba.authentication.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.PasswordApi;
import ru.proba.authentication.generated.model.CodeAndPasswordDto;
import ru.proba.authentication.generated.model.ResetEmailDto;

@RestController
public class PasswordController implements PasswordApi {
    @Override
    public void clearPassword(ResetEmailDto body) {
    }

    @Override
    public void changePassword(CodeAndPasswordDto body) {
    }
}
