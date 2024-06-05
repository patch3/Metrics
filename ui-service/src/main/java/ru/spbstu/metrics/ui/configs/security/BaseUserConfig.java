package ru.spbstu.metrics.ui.configs.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.spbstu.metrics.ui.constants.Role;

import java.util.List;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "basic.user")
public class BaseUserConfig {
    public final PasswordEncoder passwordEncoder;
    private String username;
    private String password;

    @Autowired
    public BaseUserConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails adminUser() {
        return new User(username, passwordEncoder.encode(password), List.of(
                new SimpleGrantedAuthority("ROLE_" + Role.STAFF),
                new SimpleGrantedAuthority("ROLE_" + Role.ADMIN)
        ));
    }
}
