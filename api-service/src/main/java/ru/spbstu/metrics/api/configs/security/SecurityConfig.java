package ru.spbstu.metrics.api.configs.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.spbstu.metrics.api.constants.Role;
import ru.spbstu.metrics.api.security.ReceiverFromScriptJWTAuthFilter;
import ru.spbstu.metrics.api.security.ServiceAuthFilter;
import ru.spbstu.metrics.api.services.TokenService;

@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfig {
    private final TokenService tokenService;
    private final ServiceAuthConfig serviceAuthConfig;

    public SecurityConfig(TokenService tokenService, ServiceAuthConfig serviceAuthConfig) {
        this.tokenService = tokenService;
        this.serviceAuthConfig = serviceAuthConfig;
    }

    @Bean
    public SecurityFilterChain standardFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (request) -> request
                                .requestMatchers("/api/activity").hasRole(Role.ACTIVITY)
                                .requestMatchers("/api/service/**").hasRole(Role.SERVICE)
                                .anyRequest().permitAll()
                ).sessionManagement(
                        (sessionManagement) -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterAt(new ServiceAuthFilter(serviceAuthConfig), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new ReceiverFromScriptJWTAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
