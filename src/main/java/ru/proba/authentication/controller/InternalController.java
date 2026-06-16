package ru.proba.authentication.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.InternalAuthApi;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InternalController implements InternalAuthApi {

    @Override
    public void updateTokens(String refreshToken) {

    }
}
