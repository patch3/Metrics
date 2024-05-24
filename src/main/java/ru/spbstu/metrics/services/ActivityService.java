package ru.spbstu.metrics.services;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.dtos.ActivityDto;
import ru.spbstu.metrics.models.Activity;
import ru.spbstu.metrics.repositories.ActivityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
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

    public void saveActivity(ActivityDto activityDto, String username) {
        val activity = Activity.builder()
                .token(activityDto.getToken())
                .target(activityDto.getTarget())
                .username(username)
                .build();
        activityRepository.save(activity);

    }
}
