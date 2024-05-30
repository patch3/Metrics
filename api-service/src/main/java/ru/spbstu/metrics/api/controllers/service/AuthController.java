package ru.spbstu.metrics.api.controllers.service;

import org.springframework.web.bind.annotation.*;
import ru.spbstu.metrics.api.dtos.ClientDTO;
import ru.spbstu.metrics.api.services.ClientService;

@RestController
@RequestMapping("/api/service")
public class AuthController {
    private final ClientService clientService;

    public AuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/authenticate")
    public boolean authenticate(
            @RequestParam String username,
            @RequestParam String password
    ) {
        return clientService.authenticate(username, password);
    }

    @PostMapping("/register")
    public boolean register(@RequestBody ClientDTO clientDTO) {
        return clientService.registration(clientDTO);
    }
}
