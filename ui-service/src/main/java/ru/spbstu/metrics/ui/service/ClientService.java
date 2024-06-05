package ru.spbstu.metrics.ui.service;


import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.ui.models.Client;
import ru.spbstu.metrics.ui.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Client> getClientByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    public boolean isNotClientExists(String username) {
        return !clientRepository.existsByUsername(username);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public boolean authenticate(String email, String password) throws UsernameNotFoundException {
        val clientOp = getClientByUsername(email);
        return clientOp
                .filter(client -> passwordEncoder.matches(password, client.getPasswordHash()))
                .isPresent();
    }

    public void registration(String username, String password) {
        clientRepository.save(new Client(username, passwordEncoder.encode(password)));
    }
}
