package ru.spbstu.metrics.api.configs.security;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.spbstu.metrics.api.dtos.ClientDTO;


@Configuration
@ConfigurationProperties(prefix = "basic.user")
public class BaseUserConfig extends ClientDTO {
}
