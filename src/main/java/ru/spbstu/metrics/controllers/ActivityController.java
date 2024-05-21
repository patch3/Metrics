package ru.spbstu.metrics.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.metrics.dtos.ActivityDto;
import ru.spbstu.metrics.services.ActivityService;

import java.security.Principal;

@RestController
@RequestMapping("/ipa/activity")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<?> saveActivity(@RequestBody ActivityDto activityDto, Principal principal) {
        activityService.saveActivity(activityDto, principal.getName());
        return ResponseEntity.ok().build();
    }
}
