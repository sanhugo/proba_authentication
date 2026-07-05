package ru.proba.authentication.tokens;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.proba.authentication.records.AccessToken;
import ru.proba.authentication.records.AccessTokenInfo;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JWTGenerator {
    JWTConfig config;

    public AccessToken generateAccess(AccessTokenInfo ati){
        SecretKey key = Keys.hmacShaKeyFor(config.getSecret().getBytes(StandardCharsets.UTF_8));
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 1000 * 60 * config.getAccessExpiration();
        Date exp = new Date(expMillis);
        return new AccessToken(Jwts.builder()
                .id(ati.id().toString())
                .claim("roles", ati.roles())
                .expiration(exp)
                .signWith(key,Jwts.SIG.HS384)
                .compact(),
                expMillis);
    }
}
