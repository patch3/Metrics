package ru.spbstu.metrics.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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
import ru.spbstu.metrics.api.configs.security.ServiceAuthConfig;
import ru.spbstu.metrics.api.constants.Role;

import java.io.IOException;
import java.util.Collections;

@Slf4j
public class ServiceAuthFilter extends OncePerRequestFilter {

    private final ServiceAuthConfig serviceAuthConfig;

    public ServiceAuthFilter(ServiceAuthConfig serviceAuthConfig) {
        this.serviceAuthConfig = serviceAuthConfig;
    }

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String serviceAuthToken = httpServletRequest.getHeader("SERVICE-AUTH-TOKEN");
        if (serviceAuthToken == null || serviceAuthToken.isEmpty()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        boolean isTokenValid = validateJwtToken(serviceAuthToken);
        if (isTokenValid) {
            Authentication authentication = getAuthentication();
            // Устанавливаем аутентификацию в контекст безопасности
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.setStatus(401);
            httpServletResponse.getWriter().write("INVALID_TOKEN");
        }
    }

    /**
     * Проверьте токен, сравнив его секретный ключ со списком секретных ключей
     * зарегистрированных в сервисе UI.
     */
    private boolean validateJwtToken(String jwtToken) {
        DecodedJWT decodedJWT =
                JWT.require(Algorithm.HMAC512(serviceAuthConfig.getJwtAlgorithmKey()))
                        .build()
                        .verify(jwtToken);
        log.info("Request Initiated from {}", decodedJWT.getIssuer());
        return serviceAuthConfig.getRegisteredSecretKeys().contains(decodedJWT.getSubject());
    }

    private Authentication getAuthentication() {
        val authority = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + Role.SERVICE));
        return new UsernamePasswordAuthenticationToken(serviceAuthConfig.getJwtAlgorithmKey(), serviceAuthConfig.getRegisteredSecretKeys(), authority);
    }
}