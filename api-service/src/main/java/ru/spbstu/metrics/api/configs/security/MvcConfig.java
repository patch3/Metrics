package ru.spbstu.metrics.api.configs.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*") // Разрешаем любой источник с помощью регулярных выражений
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешаем эти методы HTTP
                .allowedHeaders("*") // Разрешаем любые заголовки
                .allowCredentials(true); // Разрешаем передачу куки и авторизационных заголовков
    }
}
