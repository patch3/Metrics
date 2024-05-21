package ru.spbstu.metrics.configs.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain standardFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(
                        (request) -> request
                                .requestMatchers("/api/**").authenticated()
                                .anyRequest().permitAll()
                ).httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
