package ru.proba.authentication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.proba.authentication.generated.model.CodeAndEmailDto;
import ru.proba.authentication.generated.model.EmailDto;
import ru.proba.authentication.generated.model.NewPasswordDto;
import ru.proba.authentication.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordService {

    UserRepository userRepository;
    RedisTemplate<String, String> redisTemplate;
    BCryptPasswordEncoder encoder;

    public void generateCode(EmailDto body) {
        boolean userExists = userRepository.existsByEmail(body.userEmail());
        if (userExists) {
            try {
                SecureRandom secureRandom = SecureRandom.getInstance("NativePRNGNonBlocking");
                String number = String.valueOf(secureRandom.nextInt(100000, 10000000));
                redisTemplate.opsForHash().put(body.userEmail(),"reset_code",encoder.encode(number));
            } catch (NoSuchAlgorithmException e) {
                ExceptionUtils.rethrow(e);
            }
        }
    }

    public void verify(CodeAndEmailDto body) {

    }

    public void changePassword(NewPasswordDto body) {
    }
}
