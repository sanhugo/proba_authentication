package ru.proba.authentication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.proba.authentication.entity.User;
import ru.proba.authentication.enums.EnteredField;
import ru.proba.authentication.generated.model.UserAndRoleDto;
import ru.proba.authentication.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleFixService {
    UserRepository userRepository;
    RedisTemplate<String, String> redisTemplate;

    public boolean fixRoleWithLock(UserAndRoleDto body, EnteredField ef, boolean isAdd){
        return false;
    }

    @Transactional
    public boolean addRoleInTransaction(UserAndRoleDto body, EnteredField ef) {
        Optional<User> u = switch (ef){
            case UUID -> userRepository.findUserById(UUID.fromString(
                    body.userData()
            ));
            case LOGIN -> userRepository.findByLogin(body.userData());
            case EMAIL -> userRepository.findByEmail(body.userData());
        };
        return u.map(user -> user.getRoles().add(body.role())).orElse(false);
    }

    @Transactional
    public boolean removeRole(UserAndRoleDto body, EnteredField ef) {
        Optional<User> u = switch (ef){
            case UUID -> userRepository.findUserById(UUID.fromString(
                    body.userData()
            ));
            case LOGIN -> userRepository.findByLogin(body.userData());
            case EMAIL -> userRepository.findByEmail(body.userData());
        };
        return u.map(user -> user.getRoles().remove(body.role())).orElse(false);
    }
}
