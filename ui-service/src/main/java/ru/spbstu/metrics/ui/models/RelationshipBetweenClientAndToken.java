package ru.spbstu.metrics.ui.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "relationship_between_client_and_token")
public class RelationshipBetweenClientAndToken {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "token", nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;


    public RelationshipBetweenClientAndToken(String name, String token) {
        this.name = name;
        this.token = token;
    }
}
