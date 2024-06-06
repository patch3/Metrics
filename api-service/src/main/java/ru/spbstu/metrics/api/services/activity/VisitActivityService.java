package ru.spbstu.metrics.api.services.activity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbstu.metrics.api.dtos.activity.VisitActivityFromScriptDTO;
import ru.spbstu.metrics.api.models.activity.Request;
import ru.spbstu.metrics.api.models.activity.VisitActivity;
import ru.spbstu.metrics.api.repositories.activity.RequestRepository;
import ru.spbstu.metrics.api.repositories.activity.VisitActivityRepository;
import ru.spbstu.metrics.api.services.TokenService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Service
public class VisitActivityService {
    private static final Logger log = LoggerFactory.getLogger(VisitActivityService.class);

    private final VisitActivityRepository visitActivityRepository;
    private final RequestRepository requestRepository;

    private final TokenService tokenService;

    @Value("${service.numRecordsOnPage}")
    private Integer numRecordsOnPage;

    @Autowired
    public VisitActivityService(VisitActivityRepository visitActivityRepository, RequestRepository requestRepository,
                                TokenService tokenService) {
        this.visitActivityRepository = visitActivityRepository;
        this.requestRepository = requestRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    public void handleVisit(VisitActivityFromScriptDTO visitDto, HttpServletRequest servletRequest) {
        try {
            val token = tokenService.getTokenByToken(visitDto.getToken())
                    .orElseThrow(() -> new IllegalArgumentException("Token not found"));

            val ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");

            val visitRequest = requestRepository.findByPageUrlAndIpAddress(visitDto.getPageUrl(), InetAddress.getByName(ipAddress))
                    .orElseGet(() -> {
                        try {
                            return requestRepository.save(new Request(visitDto.getPageUrl(), InetAddress.getByName(ipAddress)));
                        } catch (UnknownHostException e) {
                            throw new RuntimeException(e);
                        }
                    });

            val activity = new VisitActivity();
            activity.setToken(token);
            activity.setRequest(visitRequest);
            activity.setTimestamp(visitDto.getTimestamp());

            visitActivityRepository.save(activity);
        } catch (UnknownHostException ex) {
            log.info("not getting ip address", ex);
        }
    }


    public VisitActivity save(VisitActivity visitActivity) {
        return visitActivityRepository.save(visitActivity);
    }

    public Page<VisitActivity> findByToken(String token, Integer numPage) {
        val tokenEntity = tokenService.getTokenByToken(token);
        if (tokenEntity.isEmpty()) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of((numPage - 1) * numRecordsOnPage,numPage * numRecordsOnPage);
        return visitActivityRepository.findByToken(tokenEntity.get(), pageable);
    }

    public Optional<VisitActivity> findById(Long id) {
        return visitActivityRepository.findById(id);
    }

    public void deleteById(Long id) {
        visitActivityRepository.deleteById(id);
    }
}
