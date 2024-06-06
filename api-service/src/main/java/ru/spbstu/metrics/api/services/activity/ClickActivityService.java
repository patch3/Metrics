package ru.spbstu.metrics.api.services.activity;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbstu.metrics.api.dtos.activity.ClickActivityDTO;
import ru.spbstu.metrics.api.models.activity.ClickActivity;
import ru.spbstu.metrics.api.models.activity.Tag;
import ru.spbstu.metrics.api.repositories.activity.ClickActivityRepository;
import ru.spbstu.metrics.api.repositories.activity.TagRepository;
import ru.spbstu.metrics.api.services.TokenService;

import java.util.Optional;

@Service
public class
ClickActivityService {
    private final ClickActivityRepository clickActivityRepository;
    private final TokenService tokenService;
    private final TagRepository tagRepository;

    @Value("${service.numRecordsOnPage}")
    private Integer numRecordsOnPage;


    @Autowired
    public ClickActivityService(ClickActivityRepository clickActivityRepository,
                                TokenService tokenService,
                                TagRepository tagRepository) {
        this.clickActivityRepository = clickActivityRepository;
        this.tokenService = tokenService;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public void handleClient(ClickActivityDTO clickDTO) {
        val tagRequest = tagRepository.findByElementNameAndElementIdAndClasses(
                clickDTO.getElementName(),
                clickDTO.getElementId(),
                clickDTO.getClasses()
        ).orElseGet(() -> tagRepository.save(new Tag(clickDTO)));


        val token = tokenService.getTokenByToken(clickDTO.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        val activity = new ClickActivity();
        activity.setToken(token);
        activity.setTag(tagRequest);
        activity.setTimestamp(clickDTO.getTimestamp());

        clickActivityRepository.save(activity);
    }

    public Page<ClickActivity> findByToken(String token, Integer numPage) {
        val tokenEntity = tokenService.getTokenByToken(token);
        if (tokenEntity.isEmpty())
            return null;

        Pageable pageable = PageRequest.of((numPage - 1) * numRecordsOnPage,numPage * numRecordsOnPage);
        return clickActivityRepository.findByToken(tokenEntity.get(), pageable);
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
