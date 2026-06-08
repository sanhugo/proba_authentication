package ru.proba.authentication.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.RegistrationApi;
import ru.proba.authentication.generated.model.ApprovalCodeDto;
import ru.proba.authentication.generated.model.UserRegistrationDto;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RegistrationController implements RegistrationApi {

    @Override
    public String registerUser(UserRegistrationDto body) {
        return null;
    }

    @Override
    public String confirmRegistration(ApprovalCodeDto body) {
        return null;
    }
}
