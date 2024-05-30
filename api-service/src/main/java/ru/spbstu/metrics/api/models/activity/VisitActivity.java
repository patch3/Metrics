package ru.spbstu.metrics.api.models.activity;

import jakarta.persistence.*;
import lombok.*;
import ru.spbstu.metrics.api.models.Token;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity @Table(name = "visit_activity")
public class VisitActivity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private Long id;
    private String pageUrl;
    private String ipAddress;
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id")
    private Token token;

    public void setTimestamp(Long timestamp){
        val instant = Instant.ofEpochMilli(timestamp);
        this.timestamp = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
