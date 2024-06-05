package ru.spbstu.metrics.ui.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.spbstu.metrics.ui.configs.security.BaseUserConfig;
import ru.spbstu.metrics.ui.service.ClientService;


@Component
public class RegistrationBaseUserRunner implements CommandLineRunner {
    private final BaseUserConfig baseUserConfig;
    private final ClientService clientService;

    @Autowired
    public RegistrationBaseUserRunner(BaseUserConfig baseUserConfig,
                                      ClientService clientService) {
        this.baseUserConfig = baseUserConfig;
        this.clientService = clientService;
    }

    @Override
    public void run(String... args) {
        if (clientService.isNotClientExists(baseUserConfig.getUsername())) {
            clientService.registration(
                    baseUserConfig.getUsername(),
                    baseUserConfig.getPassword()
            );
        }
    }
}
