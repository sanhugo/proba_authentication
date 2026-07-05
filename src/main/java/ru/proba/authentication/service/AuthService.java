package ru.proba.authentication.service;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.proba.authentication.enums.Role;
import ru.proba.authentication.exception.NoUserException;
import ru.proba.authentication.generated.model.UserLoginDto;
import ru.proba.authentication.records.AccessToken;
import ru.proba.authentication.records.AccessTokenInfo;
import ru.proba.authentication.records.UserToken;
import ru.proba.authentication.repository.UserRepository;
import ru.proba.authentication.tokens.JWTDecoder;
import ru.proba.authentication.tokens.JWTGenerator;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepository userRepository;
    JWTGenerator jwtGenerator;
    JWTDecoder jwtDecoder;
    RedisTemplate<String, String> redisTemplate;
    RedisService service;

    public void logout(String session_id) {
        service.deleteSession(session_id);
    }

    public UserToken update(String session_id) {
        String accessToken = redisTemplate.opsForHash().get(session_id, "access_token").toString();
        Claims claims = jwtDecoder.getClaimsFromAccess(accessToken);
        UUID uuid = claims.get("uuid", UUID.class);
        Set<Role> roles = userRepository.findRolesById(uuid);
        AccessTokenInfo ati = new AccessTokenInfo(uuid,roles);
        AccessToken newAccessToken = jwtGenerator.generateAccess(ati);
        return new UserToken(
                newAccessToken.token(),
                newAccessToken.expiration());
    }

    public UserToken createToken(UserLoginDto body) {
        EmailValidator validator = EmailValidator.getInstance();
        boolean isEmail = validator.isValid(body.login());
        Optional<AccessTokenInfo> t;
        if (isEmail) {
            t = userRepository.findIDAndRolesByEmail(body.login());
        } else {
            t = userRepository.findIDAndRolesByLogin(body.login());
        }
        if (t.isPresent()) {
            AccessTokenInfo ati = new AccessTokenInfo(
                    t.get().id(),
                    t.get().roles());
            AccessToken accessToken = jwtGenerator.generateAccess(ati);
            return new UserToken(
                    accessToken.token(),
                    accessToken.expiration());
        }
        else throw new NoUserException("User not found");
    }
}
