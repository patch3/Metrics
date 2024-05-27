package ru.spbstu.metrics.api.services.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.api.constants.Role;
import ru.spbstu.metrics.api.dtos.ClientDTO;
import ru.spbstu.metrics.api.models.Client;
import ru.spbstu.metrics.api.services.ClientService;

@Service
@Lazy
public class ClientDetailsServiceImpl implements ClientDetailService {
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientDetailsServiceImpl(ClientService clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var client = clientService.getClientByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found with email: " + email));
        return User.builder()
                .username(client.getEmail())
                .password(client.getPasswordHash())
                .authorities("ROLE_" + Role.USER)
                .build();
    }


    @Override
    public void save(ClientDTO clientDTO) {
        clientService.save(
                Client.builder()
                        .fullName(clientDTO.getFullName())
                        .email(clientDTO.getEmail())
                        .passwordHash(passwordEncoder.encode(clientDTO.getPassword()))
                        .build()
        );
    }
}