package ru.spbstu.metrics.api.services.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.api.repositories.ClientRepository;
import ru.spbstu.metrics.api.services.ClientService;

import java.io.IOException;


@Service
@Lazy
public class ClientDetailsServiceImpl implements ClientDetailService {
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    public ClientDetailsServiceImpl(ClientService clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }


    @Autowired


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var client = clientRepository.findBy(username)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found with email: " + username));
        return new ClientDetails(client);
    }


    @Override
    public void save(ClientDTO clientDTO) throws IOException {
        clientRepository.save(
                new Client(
                        clientDTO.getFullName(),
                        clientDTO.getEmail(),
                        false,
                        clientDTO.getPassportPhoto().getBytes(),
                        passwordEncoder.encode(clientDTO.getPassword())
                )
        );
    }
}