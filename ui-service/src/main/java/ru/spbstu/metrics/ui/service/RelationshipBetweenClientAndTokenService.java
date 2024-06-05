package ru.spbstu.metrics.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.ui.models.RelationshipBetweenClientAndToken;
import ru.spbstu.metrics.ui.repository.RelationshipBetweenClientAndTokenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RelationshipBetweenClientAndTokenService {
    private final RelationshipBetweenClientAndTokenRepository relationshipRepository;

    @Autowired
    public RelationshipBetweenClientAndTokenService(RelationshipBetweenClientAndTokenRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    public List<RelationshipBetweenClientAndToken> getAllByClientUsername(String username) {
        return relationshipRepository.findAllByClientUsername(username);
    }

    public List<RelationshipBetweenClientAndToken> findAll() {
        return relationshipRepository.findAll();
    }

    public Optional<RelationshipBetweenClientAndToken> findById(Long id) {
        return relationshipRepository.findById(id);
    }

    public List<RelationshipBetweenClientAndToken> getTokensByList(List<String> tokenList) {
        return relationshipRepository.findByTokenIn(tokenList);
    }

    public RelationshipBetweenClientAndToken save(RelationshipBetweenClientAndToken relationship) {
        return relationshipRepository.save(relationship);
    }

    public void deleteByToken(String token) {
        relationshipRepository.deleteByToken(token);
    }

    public void deleteById(Long id) {
        relationshipRepository.deleteById(id);
    }

}
