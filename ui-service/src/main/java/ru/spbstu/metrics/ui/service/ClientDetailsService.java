package ru.spbstu.metrics.ui.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.ui.configs.security.BaseUserConfig;
import ru.spbstu.metrics.ui.repository.ClientRepository;
import ru.spbstu.metrics.ui.security.ClientDetails;


@Service
@Lazy
public class ClientDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;

    private final BaseUserConfig baseUserConfig;

    @Autowired
    public ClientDetailsService(ClientRepository clientRepository, BaseUserConfig baseUserConfig) {
        this.clientRepository = clientRepository;
        this.baseUserConfig = baseUserConfig;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(baseUserConfig.getUsername())) {
            return baseUserConfig.adminUser();
        }

        var client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found with username: " + username));
        return new ClientDetails(client);
    }
}