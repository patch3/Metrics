package ru.spbstu.metrics.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.metrics.api.dtos.ActivityDto;
import ru.spbstu.metrics.api.services.ActivityService;


@RestController
@RequestMapping("/api/activity")
//CrossOrigin(origins = "*")
public class ActivityController {
    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<?> saveActivity(@RequestBody ActivityDto activityDto) {
        activityService.saveActivity(activityDto);
        return ResponseEntity.ok().build();
    }
}
