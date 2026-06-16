package ru.proba.authentication.tokens;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.proba.authentication.records.AccessTokenInfo;
import ru.proba.authentication.service.CustomUserDetailsService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JWTGenerator {
    JWTConfig config;
    JWTRefreshConfig refreshConfig;
    RedisTemplate<String, String> redisTemplate;
    CustomUserDetailsService userDetailsService;

    public String generateAccess(String username){
        AccessTokenInfo ati = userDetailsService.getInfo(username);
        SecretKey key = Keys.hmacShaKeyFor(config.getSecret().getBytes(StandardCharsets.UTF_8));
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 1000 * 60 * config.getAccessExpiration();
        Date exp = new Date(expMillis);
        return Jwts.builder()
                .id(ati.id().toString())
                .claim("roles", ati.roles())
                .expiration(exp)
                .signWith(key,Jwts.SIG.HS384)
                .compact();
    }

//    public String generateRefresh(String username, String device_id){
//        RefreshTokenInfo rti = userDetailsService.getInfoForRefresh(username);
//
//        return
//    }
}
