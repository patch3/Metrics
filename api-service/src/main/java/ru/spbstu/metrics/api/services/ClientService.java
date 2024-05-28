package ru.spbstu.metrics.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.api.models.Client;
import ru.spbstu.metrics.api.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;
import lombok.val;

@Service

public class ClientService {
    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public boolean authenticate(String email, String password) {
        val client = getClientByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found with email: " + email));
        return passwordEncoder.matches(password, client.getPasswordHash());
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
