package ru.spbstu.metrics.ui.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import ru.spbstu.metrics.ui.constants.Role;
import ru.spbstu.metrics.ui.managers.StaffAuthenticationManager;

@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfig {
    private final StaffAuthenticationManager staffAuthenticationManager;


    @Autowired
    public SecurityConfig(StaffAuthenticationManager staffAuthenticationManager) {
        this.staffAuthenticationManager = staffAuthenticationManager;
    }


    @Bean
    @Primary
    public AuthenticationManager authManager(HttpSecurity http) {
        return staffAuthenticationManager;
    }

    @Bean
    public SecurityFilterChain standardFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (request) -> request
                                .requestMatchers(
                                        "/staff/**"
                                ).hasRole(Role.STAFF)
                                .requestMatchers(
                                        "/", "/home", "/index",
                                        "/login/process"
                                ).anonymous()
                                .requestMatchers(
                                        "/js/**",
                                        "/fonts/**",
                                        "/images/**",
                                        "/css/**",
                                        "/favicon.ico"
                                ).permitAll()
                                .anyRequest().authenticated()
                ).formLogin(
                        (form) -> form
                                .loginPage("/home")
                                .loginProcessingUrl("/login/process")
                                .failureUrl("/home?error")
                                .defaultSuccessUrl("/staff/menu")
                ).logout(
                        (logout) -> logout
                                .logoutUrl("/staff/logout")
                                .logoutSuccessUrl("/home?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                ).csrf(
                        (csrf) -> csrf
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );
        return http.build();
    }
}
