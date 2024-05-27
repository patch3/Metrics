package ru.spbstu.metrics.api.services.client;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.spbstu.metrics.api.dtos.ClientDTO;

import java.io.IOException;

public interface ClientDetailService extends UserDetailsService {
    void save(ClientDTO clientDTO) throws IOException;
}
