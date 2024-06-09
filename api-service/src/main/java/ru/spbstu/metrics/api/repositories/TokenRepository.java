package ru.spbstu.metrics.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.metrics.api.models.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    boolean existsByToken(String token);

    void deleteByToken(String token);
}
