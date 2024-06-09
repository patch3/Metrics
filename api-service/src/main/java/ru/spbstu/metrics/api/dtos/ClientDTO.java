package ru.spbstu.metrics.api.dtos;

import lombok.Data;

@Data
public class ClientDTO {
    private String fullName;
    private String email;
    private String password;
}
