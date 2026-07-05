package ru.proba.authentication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.proba.authentication.exception.UserExistsException;
import ru.proba.authentication.generated.model.ApprovalCodeDto;
import ru.proba.authentication.generated.model.UserRegistrationDto;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RegistrationService {

    CustomUserDetailsService userDetailsService;

    public void register(UserRegistrationDto body) {
        /*
        Если нет - передаем данные, в Redis бросаем ключ с логином, в БД пишем данные аккаунта, но с пометкой,
        что аккаунт не активирован; неактивные аккаунты впоследствии вычищаем раз в неделю
        */
        boolean hasAccount = userDetailsService.hasAccount(body);
        if (hasAccount) {
            throw new UserExistsException("User already exists");
        } else {
            userDetailsService.registerUser(body);

        }
    }

    public void confirm(ApprovalCodeDto body) {
        /*
            проверить код, если присутствует в Redis - активируем аккаунт, который ассоциируется с кодом
         */
    }
}
