package ru.spbstu.metrics.api.controllers;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.metrics.api.dtos.activity.ClickActivityDTO;
import ru.spbstu.metrics.api.dtos.activity.VisitActivityFromScriptDTO;
import ru.spbstu.metrics.api.services.activity.ClickActivityService;
import ru.spbstu.metrics.api.services.activity.VisitActivityService;


@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    private final VisitActivityService visitActivityService;
    private final ClickActivityService clickActivityService;

    @Autowired
    public ActivityController(VisitActivityService visitActivityService, ClickActivityService clickActivityService) {
        this.visitActivityService = visitActivityService;
        this.clickActivityService = clickActivityService;
    }

    @PostMapping("/visit")
    public ResponseEntity<?> handleVisit(@RequestBody VisitActivityFromScriptDTO visitActivityDTO, HttpServletRequest request) {
        visitActivityService.handleVisit(visitActivityDTO, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/click")
    public ResponseEntity<?> handleClick(@RequestBody ClickActivityDTO clickActivityDTO) {
        clickActivityService.handleClient(clickActivityDTO);
        return ResponseEntity.ok().build();
    }
}
