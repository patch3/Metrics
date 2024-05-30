package ru.spbstu.metrics.api.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.metrics.api.models.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @EntityGraph(attributePaths = {"tokens"})
    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);
}
