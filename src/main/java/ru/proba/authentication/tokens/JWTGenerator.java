package ru.proba.authentication.tokens;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.proba.authentication.records.AccessTokenInfo;
import ru.proba.authentication.records.RefreshTokenInfo;
import ru.proba.authentication.service.CustomUserDetailsService;
import ru.proba.authentication.utils.KeyTransmitter;
import ru.proba.authentication.utils.RedisKeyMaker;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public String generateRefresh(String username, String device_id) {
        try {
            RefreshTokenInfo rti = userDetailsService.getInfoForRefresh(username);
            PublicKey key = KeyTransmitter.getPublicKeyFromString(refreshConfig.getPrivateKey());
            long nowMillis = System.currentTimeMillis();
            long expMillis = nowMillis + 1000L * 60 * 60 * 24 * refreshConfig.getRefreshExpiration();
            Date exp = new Date(expMillis);
            String token = Jwts.builder()
                    .id(rti.id().toString())
                    .claim("device_id",device_id)
                    .expiration(exp)
                    .encryptWith(
                            key,
                            Jwts.KEY.RSA_OAEP_256,
                            Jwts.ENC.A256GCM)
                    .compact();
            String user_key = RedisKeyMaker.concatenateKey(
                    rti.id().toString(),
                    device_id);
            redisTemplate.opsForHash().put(
                    user_key,
                    "token",
                    token
            );
            redisTemplate.expire(user_key,refreshConfig.getRefreshExpiration(), TimeUnit.DAYS);
            return token;
        }
        catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }
}
