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
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String action;

    @Column
    private String target;

    // Связь с токеном
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id", insertable = false, updatable = false)
    private Token token;
}
