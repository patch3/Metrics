package ru.spbstu.metrics.api.services;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.api.dtos.ActivityDto;
import ru.spbstu.metrics.api.repositories.ActivityRepository;
import ru.spbstu.metrics.api.models.Activity;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    private final TokenService tokenService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, TokenService tokenService) {
        this.activityRepository = activityRepository;
        this.tokenService = tokenService;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    public void saveActivity(ActivityDto activityDto) {
        val token = tokenService.getTokenByToken(activityDto.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        val activity = Activity.builder()
                .token(token)
                .target(activityDto.getTarget())
                .action(activityDto.getAction())
                .build();
        activityRepository.save(activity);

    }
}
