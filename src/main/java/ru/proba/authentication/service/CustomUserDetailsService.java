package ru.proba.authentication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.proba.authentication.entity.User;
import ru.proba.authentication.enums.Role;
import ru.proba.authentication.generated.model.UserRegistrationDto;
import ru.proba.authentication.mapper.AccessTokenInfoMapper;
import ru.proba.authentication.repository.UserRepository;


@Service("userDetailsService")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;
    AccessTokenInfoMapper mapper;
    BCryptPasswordEncoder encoder;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }

    public boolean hasAccount(UserRegistrationDto body) {
        return userRepository.countByLoginOrEmail(body.login(), body.email()) > 0;
    }

    @Transactional
    public void registerUser(UserRegistrationDto body) {
        User u = User.builder()
                .email(body.email())
                .login(body.login())
                .password(encoder.encode(body.password()))
                .build();
        u.getRoles().add(Role.USER);
        userRepository.save(u);
    }
}
