package ru.spbstu.metrics.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.spbstu.metrics.api.constants.RoleConst;
import ru.spbstu.metrics.api.services.TokenService;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;


    public JWTAuthorizationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Извлекаем токен из заголовка
        String token = header.replace("Bearer ", "");

        // Здесь должна быть логика проверки и получения аутентификации из токена
        Authentication authentication = getAuthentication(token);

        // Устанавливаем аутентификацию в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Продолжаем цепочку фильтров
        filterChain.doFilter(request, response);
    }

    // Метод для получения аутентификации из токена
    private Authentication getAuthentication(String token) throws BadCredentialsException {
        val client = tokenService.getClientByToken(token);
        val authorities = Collections.singletonList(
                new SimpleGrantedAuthority(RoleConst.ACTIVITY));
        val userDetails = new User(
                client.getFullName(),
                "",
                authorities
        );
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }
}