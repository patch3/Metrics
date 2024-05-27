package ru.spbstu.metrics.api.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "action")
    private String action;

    @Column(name = "target")
    private String target;

    // Связь с токеном
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_token", insertable = false, updatable = false)
    private Token token;
}
