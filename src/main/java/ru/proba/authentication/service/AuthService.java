package ru.proba.authentication.service;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import ru.proba.authentication.enums.Role;
import ru.proba.authentication.exception.NoUserException;
import ru.proba.authentication.generated.model.Token;
import ru.proba.authentication.generated.model.UserLoginDto;
import ru.proba.authentication.records.AccessTokenInfo;
import ru.proba.authentication.records.RefreshTokenInfo;
import ru.proba.authentication.records.UserTokens;
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

    public void logout(String refreshToken, Token accessToken) {

    }

    public UserTokens update(String refreshToken) {
        Claims claims = jwtDecoder.getClaimsFromRefresh(refreshToken);
        UUID uuid = claims.get("uuid", UUID.class);
        Set<Role> roles = userRepository.findRolesById(uuid);
        AccessTokenInfo ati = new AccessTokenInfo(uuid,roles);
        RefreshTokenInfo rti = new RefreshTokenInfo(uuid);
        String accessToken = jwtGenerator.generateAccess(ati);
        String newRefreshToken = jwtGenerator.generateRefresh(
                rti,
                String.valueOf(claims.get("device_id")));

        return new UserTokens(
                accessToken,
                newRefreshToken);
    }

    public UserTokens createTokens(UserLoginDto body, String device_id) {
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
            String accessToken = jwtGenerator.generateAccess(ati);
            RefreshTokenInfo rti = new RefreshTokenInfo(
                    t.get().id()
            );
            String refreshToken = jwtGenerator.generateRefresh(rti, device_id);
            return new UserTokens(
                    accessToken,
                    refreshToken);
        }
        else throw new NoUserException("User not found");
    }
}
