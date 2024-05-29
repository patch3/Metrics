package ru.spbstu.metrics.api.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.spbstu.metrics.api.configs.security.BaseUserConfig;
import ru.spbstu.metrics.api.services.ClientService;

@Component
public class RegistrationBaseUserRunner implements CommandLineRunner {
    private final BaseUserConfig baseUserConfig;
    private final ClientService clientService;

    @Autowired
    public RegistrationBaseUserRunner(BaseUserConfig baseUserConfig, ClientService clientService) {
        this.baseUserConfig = baseUserConfig;
        this.clientService = clientService;
    }


    @Override
    public void run(String... args) {
        if (!clientService.isClientExists(baseUserConfig.getEmail())) {
            clientService.registration(baseUserConfig);
        }
    }
}
