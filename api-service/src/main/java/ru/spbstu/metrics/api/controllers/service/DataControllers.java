package ru.spbstu.metrics.api.controllers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.metrics.api.dtos.activity.ClickActivityDTO;
import ru.spbstu.metrics.api.dtos.activity.VisitActivityDTO;
import ru.spbstu.metrics.api.models.Token;
import ru.spbstu.metrics.api.services.ClientService;
import ru.spbstu.metrics.api.services.activity.ClickActivityService;
import ru.spbstu.metrics.api.services.activity.VisitActivityService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/service")
public class DataControllers {
    private final VisitActivityService visitActivityService;
    private final ClickActivityService clickActivityService;
    private final ClientService clientService;

    @Autowired
    public DataControllers(VisitActivityService visitActivityService,
                           ClickActivityService clickActivityService,
                           ClientService clientService) {
        this.visitActivityService = visitActivityService;
        this.clickActivityService = clickActivityService;
        this.clientService = clientService;
    }

    @PostMapping("get-tokens-by-email")
    public List<String> getTokensByEmail(@RequestParam String email) {
        return clientService.getClientByEmail(email)
                .map(client -> client.getTokens().stream()
                        .map(Token::getToken)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @PostMapping("/get-view-activity")
    public List<VisitActivityDTO> getViewActivity(
            @RequestParam String token,
            @RequestParam Integer numPage) {
        return visitActivityService.findByToken(token, numPage).stream()
                .map(VisitActivityDTO::new)
                .toList();
    }

    @PostMapping("/get-click-activity")
    public List<ClickActivityDTO> getClickActivity(
            @RequestParam String token,
            @RequestParam Integer numPage) {
        return clickActivityService.findByToken(token, numPage).stream()
                .map(ClickActivityDTO::new)
                .toList();
    }
}
