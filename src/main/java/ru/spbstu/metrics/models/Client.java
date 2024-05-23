package ru.spbstu.metrics.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    // Связь с токенами
    @OneToMany(mappedBy = "client")
    private List<Token> tokens;
}
