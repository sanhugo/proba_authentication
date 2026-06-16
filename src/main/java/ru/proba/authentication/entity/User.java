package ru.proba.authentication.entity;

import com.github.f4b6a3.uuid.UuidCreator;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.proba.authentication.enums.Role;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name ="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {
    @Id
    @Column(columnDefinition = "uuid")
    private final UUID id=UuidCreator.getTimeOrderedEpoch();

    @Column(unique = true, nullable = false)
    private String login;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isBlocked=false;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"))
    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return !isBlocked;
    }

    @Override
    @NullMarked
    public String getPassword() {
        return password;
    }

    @Override
    @NullMarked
    public String getUsername() {
        return login;
    }
}
