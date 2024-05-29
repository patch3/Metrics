package ru.spbstu.metrics.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "client")
public class Client {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private Long id;
    private String fullName;
    private String email;
    private String passwordHash;

    // Связь с токенами
    @OneToMany(mappedBy = "client")
    private List<Token> tokens;
}
