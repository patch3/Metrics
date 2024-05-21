package ru.spbstu.metrics.services;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.dtos.ActivityDto;
import ru.spbstu.metrics.models.Activity;
import ru.spbstu.metrics.repositories.ActivityRepository;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    public void saveActivity(ActivityDto activityDto, String username) {
        val activity = Activity.builder()
                .type(activityDto.getType())
                .target(activityDto.getTarget())
                .username(username)
                .build();
        activityRepository.save(activity);

    }

}
