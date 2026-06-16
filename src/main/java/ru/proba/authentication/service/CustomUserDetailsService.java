package ru.proba.authentication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.proba.authentication.entity.User;
import ru.proba.authentication.mapper.AccessTokenInfoMapper;
import ru.proba.authentication.records.AccessTokenInfo;
import ru.proba.authentication.records.RefreshTokenInfo;
import ru.proba.authentication.repository.UserRepository;

@Service("userDetailsService")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;
    AccessTokenInfoMapper mapper;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(String.valueOf(user.getRoles()))
                .build();
    }

    public AccessTokenInfo getInfo(String username){
        User u = userRepository.findIDAndRolesByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("No user found with username: "+username)
        );
        return mapper.fromUser(u);
    }
    public RefreshTokenInfo getInfoForRefresh(String username){
        return userRepository.findIdByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("No user found with username: "+username)
        );
    }
}
