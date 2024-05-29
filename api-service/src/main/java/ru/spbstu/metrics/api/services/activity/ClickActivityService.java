package ru.spbstu.metrics.api.services.activity;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.api.dtos.activity.ClickActivityDTO;
import ru.spbstu.metrics.api.models.activity.ClickActivity;
import ru.spbstu.metrics.api.repositories.activity.ClickActivityRepository;
import ru.spbstu.metrics.api.services.TokenService;

import java.util.Optional;

@Service
public class ClickActivityService {
    private final ClickActivityRepository clickActivityRepository;
    private final TokenService tokenService;

    @Autowired
    public ClickActivityService(TokenService tokenService,
                                ClickActivityRepository clickActivityRepository) {
        this.tokenService = tokenService;
        this.clickActivityRepository = clickActivityRepository;
    }

    public void handleClient(ClickActivityDTO clickDTO) {
        val token = tokenService.getTokenByToken(clickDTO.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));
        val activity = new ClickActivity();
        activity.setToken(token);
        activity.setTagName(clickDTO.getTagName());
        activity.setTagId(clickDTO.getTagId());
        activity.setClassList(clickDTO.getClassList());
        activity.setTimestamp(clickDTO.getTimestamp());

        clickActivityRepository.save(activity);
    }


    public ClickActivity save(ClickActivity clickActivity) {
        return clickActivityRepository.save(clickActivity);
    }

    public Optional<ClickActivity> findById(Long id) {
        return clickActivityRepository.findById(id);
    }

    public void deleteById(Long id) {
        clickActivityRepository.deleteById(id);
    }
}
