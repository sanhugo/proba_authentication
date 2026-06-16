package ru.proba.authentication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TokenChecker {
    RedisTemplate<String,String> redisTemplate;

    private boolean checkAccess(String accessToken){
        return false;
    }
}
