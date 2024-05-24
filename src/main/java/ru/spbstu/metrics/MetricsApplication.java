package ru.spbstu.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class MetricsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricsApplication.class, args);
    }

}
