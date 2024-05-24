package ru.spbstu.metrics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.metrics.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
