package ru.spbstu.metrics.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.api.models.Token;
import ru.spbstu.metrics.api.repositories.TokenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }


    public boolean isTokenExists(String token) {
        return tokenRepository.existsByToken(token);
    }


    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    public Optional<Token> getTokenByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    public void deleteToken(Long id) {
        tokenRepository.deleteById(id);
    }

    public void deleteTokenByToken(String token) {
        tokenRepository.deleteByToken(token);
    }


}