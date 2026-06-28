package ru.proba.authentication.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.proba.authentication.entity.User;
import ru.proba.authentication.enums.Role;
import ru.proba.authentication.records.AccessTokenInfo;
import ru.proba.authentication.records.RefreshTokenInfo;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository  extends JpaRepository<User,Integer> {
    Optional<User> findByLogin(String login);

    @Query("select u from User u join fetch u.roles where u.login=?1")
    Optional<User> findIDAndRolesByUsername(String username);

    Optional<RefreshTokenInfo> findIdByLogin(String login);

    Optional<User> findUserById(UUID id);

    Optional<User> findByEmail(String email);

    long countByLoginOrEmail(@NotNull String login, @NotNull String email);

    @Query("select new ru.proba.authentication.records.AccessTokenInfo(u.id as id, u.roles) from User u where u.email=?1")
    Optional<AccessTokenInfo> findIDAndRolesByEmail(@NotNull @Size(min = 1) String login);

    @Query()
    Optional<AccessTokenInfo> findIDAndRolesByLogin(@NotNull @Size(min = 1) String login);

    @Query("select u.roles from User u where u.id=?1")
    Set<Role> findRolesById(UUID uuid);

    boolean existsByEmail(String email);
}

