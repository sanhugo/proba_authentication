package ru.proba.authentication.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.PasswordApi;
import ru.proba.authentication.generated.model.CodeAndPasswordDto;
import ru.proba.authentication.generated.model.ResetEmailDto;
import ru.proba.authentication.service.PasswordService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordController implements PasswordApi {

    PasswordService service;

    @Override
    public ResponseEntity<Void> clearPassword(ResetEmailDto body) {
        service.generateCode(body);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> changePassword(CodeAndPasswordDto body) {

        return ResponseEntity.ok().build();
    }
}
