package ru.spbstu.metrics.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    


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
    private Authentication getAuthentication(String token) {
        // Здесь должна быть логика проверки и получения аутентификации из токена
        // Например, извлечение пользователя и ролей из токена
        // и создание объекта Authentication на их основе
        return null; // Заглушка, вам нужно будет реализовать этот метод
    }
}