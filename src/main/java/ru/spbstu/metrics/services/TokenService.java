package ru.spbstu.metrics.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.models.Client;
import ru.spbstu.metrics.models.Token;
import ru.spbstu.metrics.repositories.TokenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Client getClientByToken(String token) throws BadCredentialsException {
        return tokenRepository.findById(token)
                .orElseThrow(() -> new BadCredentialsException("Invalid token"))
                .getClient();
    }

    public boolean isTokenExists(String token) {
        return tokenRepository.existsById(token);
    }

    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    public Optional<Token> getTokenByToken(String token) {
        return tokenRepository.findById(token);
    }

    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    public void deleteToken(String token) {
        tokenRepository.deleteById(token);
    }
}