package ru.proba.authentication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.proba.authentication.records.UserToken;
import ru.proba.authentication.session.SessionIDConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisService {
    RedisTemplate<String, String> redisTemplate;
    SessionIDConfig config;
    public void saveToken(UserToken tokens, String session_id) {
        Map<String, String> session_data = new HashMap<>(2);
        session_data.put("access_token", tokens.accessToken());
        session_data.put("access_expire", String.valueOf(tokens.accessTokenExpiration()));
        redisTemplate.opsForHash().putAll(session_id,
                session_data);
        redisTemplate.expire(session_id,config.getExpiration(), TimeUnit.DAYS);
    }

    public void deleteSession (String session_id){
        redisTemplate.delete(session_id);
    }
}
