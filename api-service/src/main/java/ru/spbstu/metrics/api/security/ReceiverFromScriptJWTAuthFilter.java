package ru.spbstu.metrics.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.spbstu.metrics.api.constants.Role;
import ru.spbstu.metrics.api.services.TokenService;

import java.io.IOException;
import java.util.Collections;

@Slf4j
public class ReceiverFromScriptJWTAuthFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public ReceiverFromScriptJWTAuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        val header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Извлекаем токен из заголовка
        val token = header.replace("Bearer ", "");
        // логика проверки и получения аутентификации из токена
        val authentication = getAuthentication(token);

        if (authentication == null) {
            log.info("bad token: {}", token);
            filterChain.doFilter(request, response);
            return;
        }

        // Устанавливаем аутентификацию в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Продолжаем цепочку фильтров
        filterChain.doFilter(request, response);
    }

    // Метод для получения аутентификации из токена
    private Authentication getAuthentication(String tokenSrt) {
        val tokenOp = tokenService.getTokenByToken(tokenSrt);

        if (tokenOp.isPresent()) {
            val authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + Role.ACTIVITY));
            return new UsernamePasswordAuthenticationToken(tokenOp.get().getToken(), "", authorities);
        } else {
            return null;
        }
    }
}