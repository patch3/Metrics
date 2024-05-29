package ru.spbstu.metrics.api.configs.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "service")
public class ServiceAuthConfig {
  private String jwtAlgorithmKey;
  private List<String> registeredSecretKeys;
}