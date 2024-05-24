package ru.spbstu.metrics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.metrics.models.Token;

public interface TokenRepository extends JpaRepository<Token, String> {
}
