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

import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.proba.authentication.enums.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name ="users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id=UuidCreator.getTimeOrderedEpoch();

    @Version //optimistic locking in case roles and passwords don't often change
    @Column(nullable = false)
    private Integer version = 0;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isBlocked=false;

    @Column(nullable = false)
    private boolean isEnabled=false;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"))
    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles=new HashSet<>();

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
    public boolean isEnabled() {
        return isEnabled;
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
