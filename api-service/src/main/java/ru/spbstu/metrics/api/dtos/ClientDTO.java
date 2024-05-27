package ru.spbstu.metrics.api.dtos;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ClientDTO {

    protected String fullName;

    protected String email;

    protected String password;
}
