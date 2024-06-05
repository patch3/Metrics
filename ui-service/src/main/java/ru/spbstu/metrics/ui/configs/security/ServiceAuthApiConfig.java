package ru.spbstu.metrics.ui.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service")
public class ServiceAuthApiConfig {
    @Setter
    @Getter
    private String jwtAlgorithmKey;

    @Setter
    @Getter
    private String serviceBSecretKey;

    @Value("${spring.application.name}")
    private String applicationName;

    @Getter
    private String serviceBAuthToken;

    /**
     * Сгенерируйте токен аутентификации, который будет передан в заголовке при вызове api сервиса B
     */
    @PostConstruct
    public void load() {
        serviceBAuthToken = createJwtToken(serviceBSecretKey);
    }

    /**
     * Создает токен JWT с ключом алгоритма (общим как для вызываемой, так и для вызывающей службы)
     * Секретный ключ службы должен быть сгенерирован вызывающей службой, а затем совместно использован
     * и сохранен в yml вызываемой службы
     */
    private String createJwtToken(String serviceSecretKey) {
        return JWT.create()
                .withIssuer(applicationName)
                .withSubject(serviceSecretKey)
                .sign(Algorithm.HMAC512(jwtAlgorithmKey));
    }
}
