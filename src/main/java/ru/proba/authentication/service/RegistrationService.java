package ru.proba.authentication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.proba.authentication.email.EmailService;
import ru.proba.authentication.exception.UserExistsException;
import ru.proba.authentication.generated.model.ApprovalCodeDto;
import ru.proba.authentication.generated.model.UserRegistrationDto;
import ru.proba.authentication.tokens.SecurityTokenGen;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RegistrationService {

    CustomUserDetailsService userDetailsService;
    RedisTemplate<String, String> redisTemplate;
    SecurityTokenGen generator;
    EmailService service;

    public void register(UserRegistrationDto body) {
        /*
        Если нет - передаем данные, в БД бросаем ключ с UUID и пишем данные аккаунта, но с пометкой,
        что аккаунт не активирован; неактивные аккаунты впоследствии вычищаем раз в неделю, как и токены,
        у которого истекло время
        */
        boolean hasAccount = userDetailsService.hasAccount(body);
        if (hasAccount) {
            throw new UserExistsException("User already exists");
        } else {
            UUID user_ID = userDetailsService.registerUser(body);
            String token;
            do {
                token = generator.generateSessionId(); //it also generates tokens for registration
            } while (!redisTemplate.hasKey(token));
            redisTemplate.opsForValue().set(token, String.valueOf(user_ID),2, TimeUnit.HOURS);
            service.sendActivationCode(body.email(),token);
        }
    }

    public void confirm(ApprovalCodeDto body) {
        /*
           проверить код, если присутствует в Redis - активируем аккаунт, который ассоциируется с кодом
        */
    }
}
