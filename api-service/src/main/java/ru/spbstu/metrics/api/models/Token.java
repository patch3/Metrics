package ru.spbstu.metrics.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spbstu.metrics.api.models.activity.ClickActivity;
import ru.spbstu.metrics.api.models.activity.VisitActivity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "token")
public class Token {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String name;
    private String token;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "token")
    private List<ClickActivity> clickActivities;

    @OneToMany(mappedBy = "token")
    private List<VisitActivity> visitActivities;
}
