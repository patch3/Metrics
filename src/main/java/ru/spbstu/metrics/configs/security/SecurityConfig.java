package ru.spbstu.metrics.configs.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.spbstu.metrics.security.JWTAuthorizationFilter;
import ru.spbstu.metrics.services.TokenService;

@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfig {
    private final TokenService tokenService;

    @Autowired
    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain standardFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (request) -> request
                                .requestMatchers("/api/**").authenticated()
                                .anyRequest().permitAll()
                ).sessionManagement(
                        (sessionManagement) -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterAfter(new JWTAuthorizationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
