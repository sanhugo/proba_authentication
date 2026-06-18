package ru.proba.authentication.tokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import ru.proba.authentication.utils.KeyTransmitter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JWTDecoder {
    JWTConfig config;
    JWTRefreshConfig refreshConfig;

    public Claims getClaimsFromRefresh(String refreshToken) {
        try {
            PrivateKey key = KeyTransmitter.getPrivateKeyFromString(refreshConfig.getPrivateKey());
            return Jwts.parser()
                    .decryptWith(key)
                    .build()
                    .parseEncryptedClaims(refreshToken)
                    .getPayload();
        } catch (Exception e)
        {
            return ExceptionUtils.rethrow(e);
        }
    }

    public Claims getClaimsFromAccess(String accessToken){
        SecretKey key = Keys.hmacShaKeyFor(config.getSecret().getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .decryptWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }
}
