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
@Table(name = "token")
public class Token {
    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "username")
    private String username;

    @ManyToOne
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private Client client;

    @OneToMany(mappedBy = "token")
    private List<Activity> activities;
}
