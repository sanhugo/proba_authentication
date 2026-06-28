package ru.proba.authentication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.proba.authentication.generated.model.ResetEmailDto;
import ru.proba.authentication.repository.UserRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordService {

    UserRepository userRepository;
    RedisTemplate<String, String> redisTemplate;

    public void generateCode(ResetEmailDto body) {
        boolean userExists = userRepository.existsByEmail(body.userEmail());
        if (userExists) {

        }
    }
}
