package ru.spbstu.metrics.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.metrics.api.services.ClientService;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final ClientService clientService;

    public AuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/authenticate")
    public Boolean authenticate(
            @RequestParam String username,
            @RequestParam String password
    ) {
        return clientService.authenticate(username, password);
    }
}
