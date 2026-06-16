package ru.proba.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.proba.authentication.entity.User;
import ru.proba.authentication.records.RefreshTokenInfo;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Integer> {
    Optional<User> findByLogin(String login);

    @Query("select u from User u join fetch u.roles where u.login=?1")
    Optional<User> findIDAndRolesByUsername(String username);

    Optional<RefreshTokenInfo> findIdByLogin(String login);
}

