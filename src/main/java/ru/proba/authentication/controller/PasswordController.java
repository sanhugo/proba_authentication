package ru.proba.authentication.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.PasswordApi;
import ru.proba.authentication.generated.model.CodeAndEmailDto;
import ru.proba.authentication.generated.model.EmailDto;
import ru.proba.authentication.generated.model.NewPasswordDto;
import ru.proba.authentication.service.PasswordService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordController implements PasswordApi {

    PasswordService service;

    @Override
    public ResponseEntity<Void> clearPassword(EmailDto body) {
        service.generateCode(body);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> verifyEmail(CodeAndEmailDto body) {
        service.verify(body);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> changePassword(NewPasswordDto body) {
        service.changePassword(body);
        return ResponseEntity.ok().build();
    }

}
