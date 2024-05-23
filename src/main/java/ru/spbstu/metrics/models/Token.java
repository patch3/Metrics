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
public class Token {
    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "username")
    private String username;

    // Связь с клиентом
    @ManyToOne
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private Client client;

    // Связь с активностями
    @OneToMany(mappedBy = "token")
    private List<Activity> activities;
}
