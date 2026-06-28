package ru.proba.authentication.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.RegistrationApi;
import ru.proba.authentication.generated.model.ApprovalCodeDto;
import ru.proba.authentication.generated.model.UserRegistrationDto;
import ru.proba.authentication.service.RegistrationService;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RegistrationController implements RegistrationApi {

    RegistrationService service;

    @Override
    public ResponseEntity<Void> registerUser(UserRegistrationDto body) {
        service.register(body);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> confirmRegistration(ApprovalCodeDto body) {
        service.confirm(body);
        return ResponseEntity.ok().build();
    }
}
