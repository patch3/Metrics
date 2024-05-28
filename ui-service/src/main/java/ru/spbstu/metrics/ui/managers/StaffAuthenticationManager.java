package ru.spbstu.metrics.ui.managers;

import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.spbstu.metrics.ui.constants.Role;
import ru.spbstu.metrics.ui.service.ApiClientService;

import java.util.Collections;

@Component
public class StaffAuthenticationManager implements AuthenticationManager {
    private final ApiClientService apiClientService;

    public StaffAuthenticationManager(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        val username = authentication.getName();
        val password = authentication.getCredentials().toString();

        if (username.isEmpty() || password.isEmpty()) {
            throw new BadCredentialsException("Пустые поля");
        }
        val isAuthenticated = apiClientService.authentication(username, password);

        if (!isAuthenticated) {
            throw new BadCredentialsException("Неверное имя пользователя или пароль");
        }
        return new UsernamePasswordAuthenticationToken(
                username,
                password,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + Role.STAFF))
        );
    }
}
