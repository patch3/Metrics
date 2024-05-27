package ru.spbstu.metrics.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MetricsUiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetricsUiApplication.class, args);
    }
}
