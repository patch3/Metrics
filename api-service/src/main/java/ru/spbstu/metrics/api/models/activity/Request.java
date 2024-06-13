package ru.spbstu.metrics.api.models.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "request")
public class Request {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String pageUrl;

    @Column(nullable = false, columnDefinition = "inet")
    private InetAddress ipAddress;


    @OneToMany(mappedBy = "request")
    private List<VisitActivity> visitActivities;

    @OneToMany(mappedBy = "request")
    private List<ClickActivity> clickActivities;


    public Request(String pageUrl, InetAddress ipAddress) {
        this.pageUrl = pageUrl;
        this.ipAddress = ipAddress;
    }
}
