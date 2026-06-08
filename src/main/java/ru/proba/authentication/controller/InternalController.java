package ru.proba.authentication.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.InternalAuthApi;
import ru.proba.authentication.generated.model.Token;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InternalController implements InternalAuthApi {

    @Override
    public Token updateTokens(String refreshToken) {
        return null;
    }
}
