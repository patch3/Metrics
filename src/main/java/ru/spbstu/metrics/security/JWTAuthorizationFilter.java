package ru.spbstu.metrics.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.spbstu.metrics.services.ActivityService;
import ru.spbstu.metrics.services.ClientService;
import ru.spbstu.metrics.services.TokenService;

import java.io.IOException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    private final ActivityService activityService;

    public JWTAuthorizationFilter(TokenService tokenService, ActivityService activityService) {
        this.tokenService = tokenService;
        this.activityService = activityService;
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

        UserDetails userDetails = new User()


        return null; // Заглушка, вам нужно будет реализовать этот метод
    }
}