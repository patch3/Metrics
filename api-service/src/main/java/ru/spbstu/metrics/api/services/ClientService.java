package ru.spbstu.metrics.api.services;


import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.api.dtos.ClientDTO;
import ru.spbstu.metrics.api.models.Client;
import ru.spbstu.metrics.api.repositories.ClientRepository;

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

    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public boolean isClientExists(String email) {
        return clientRepository.existsByEmail(email);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public boolean authenticate(String email, String password) throws UsernameNotFoundException {
        val clientOp = getClientByEmail(email);
        return clientOp
                .filter(client -> passwordEncoder.matches(password, client.getPasswordHash()))
                .isPresent();
    }

    public boolean registration(ClientDTO clientDTO) {
        try {
            clientRepository.save(
                    Client.builder()
                            .fullName(clientDTO.getFullName())
                            .email(clientDTO.getEmail())
                            .passwordHash(passwordEncoder.encode(clientDTO.getPassword()))
                            .build()
            );
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
