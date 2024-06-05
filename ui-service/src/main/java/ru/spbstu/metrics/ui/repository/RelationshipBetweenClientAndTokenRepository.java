package ru.spbstu.metrics.ui.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.metrics.ui.models.RelationshipBetweenClientAndToken;

import java.util.List;

@Repository
public interface RelationshipBetweenClientAndTokenRepository extends JpaRepository<RelationshipBetweenClientAndToken, Long> {
    List<RelationshipBetweenClientAndToken> findAllByClientUsername(String username);

    void deleteByToken(String token);

    List<RelationshipBetweenClientAndToken> findByTokenIn(List<String> tokenList);
}
