package ru.spbstu.metrics.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.spbstu.metrics.api.configs.security.ServiceAuthConfig;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class ServiceAuthFilter implements Filter {

    private final ServiceAuthConfig serviceAuthConfig;

    @Autowired
    public ServiceAuthFilter(ServiceAuthConfig serviceAuthConfig) {
        this.serviceAuthConfig = serviceAuthConfig;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = ((HttpServletRequest) servletRequest);
        String serviceAuthToken = httpServletRequest.getHeader("SERVICE-AUTH-TOKEN");
        boolean isTokenValid = validateJwtToken(serviceAuthToken);
        if (!isTokenValid) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setStatus(401);
            httpServletResponse.getWriter().write("INVALID_TOKEN");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * Проверьте токен, сравнив его секретный ключ со списком секретных ключей
     * зарегистрированных в сервисе UI.
    */
    private boolean validateJwtToken(String jwtToken) {
        if (jwtToken.isEmpty()) {
            return false;
        }
        DecodedJWT decodedJWT =
            JWT.require(Algorithm.HMAC512(serviceAuthConfig.getJwtAlgorithmKey()))
                    .build()
                    .verify(jwtToken);
        log.info("Request Initiated from {}", decodedJWT.getIssuer());
        return serviceAuthConfig.getRegisteredSecretKeys().contains(decodedJWT.getSubject());
    }
}