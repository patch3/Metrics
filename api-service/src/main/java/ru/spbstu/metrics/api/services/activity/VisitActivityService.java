package ru.spbstu.metrics.api.services.activity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.api.dtos.activity.VisitActivityDTO;
import ru.spbstu.metrics.api.models.activity.VisitActivity;
import ru.spbstu.metrics.api.repositories.activity.VisitActivityRepository;
import ru.spbstu.metrics.api.services.TokenService;

import java.time.Instant;
import java.util.Optional;

@Service
public class VisitActivityService {
    private final VisitActivityRepository visitActivityRepository;

    private final TokenService tokenService;

    @Autowired
    public VisitActivityService(VisitActivityRepository visitActivityRepository,
                                TokenService tokenService) {
        this.visitActivityRepository = visitActivityRepository;
        this.tokenService = tokenService;
    }

    public void handleVisit(VisitActivityDTO visitDto, HttpServletRequest request) {
        val token = tokenService.getTokenByToken(visitDto.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        val ipAddress = request.getHeader("X-FORWARDED-FOR");

        val activity = new VisitActivity();
        activity.setToken(token);
        activity.setIpAddress(ipAddress);
        activity.setPageUrl(visitDto.getPageUrl());
        activity.setTimestamp(visitDto.getTimestamp());
        visitActivityRepository.save(activity);
    }


    public VisitActivity save(VisitActivity visitActivity) {
        return visitActivityRepository.save(visitActivity);
    }

    public Optional<VisitActivity> findById(Long id) {
        return visitActivityRepository.findById(id);
    }

    public void deleteById(Long id) {
        visitActivityRepository.deleteById(id);
    }
}
