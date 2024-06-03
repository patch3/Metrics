package ru.spbstu.metrics.api.repositories.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.metrics.api.models.activity.Request;

import java.net.InetAddress;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByPageUrlAndIpAddress(String pageUrl, InetAddress ipAddress);
}
