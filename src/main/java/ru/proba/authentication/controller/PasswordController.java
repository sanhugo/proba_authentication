package ru.proba.authentication.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.PasswordApi;
import ru.proba.authentication.generated.model.CodeAndPasswordDto;
import ru.proba.authentication.generated.model.ResetEmailDto;

@RestController
public class PasswordController implements PasswordApi {
    @Override
    public String clearPassword(ResetEmailDto body) {
        return null;
    }

    @Override
    public String changePassword(CodeAndPasswordDto body) {
        return null;
    }
}
