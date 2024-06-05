package ru.spbstu.metrics.ui.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import ru.spbstu.metrics.ui.constants.Role;
import ru.spbstu.metrics.ui.constants.SecretKeys;

@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void authManagerBuilderChange(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
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
                ).rememberMe((remember) -> remember
                        .rememberMeCookieDomain("remember-me")
                        .key(SecretKeys.REMEMBER_ME)
                        .tokenValiditySeconds(SecretKeys.TIME_REMEMBER)
                        .userDetailsService(userDetailsService)
                ).csrf(
                        (csrf) -> csrf
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                ).exceptionHandling(
                        (exception) -> exception
                                .accessDeniedPage("/error?code=403")
                );
        return http.build();
    }

}
