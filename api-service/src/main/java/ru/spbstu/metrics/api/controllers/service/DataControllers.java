package ru.spbstu.metrics.api.controllers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.metrics.api.dtos.PageDTO;
import ru.spbstu.metrics.api.dtos.activity.ClickActivityDTO;
import ru.spbstu.metrics.api.dtos.activity.VisitActivityDTO;
import ru.spbstu.metrics.api.models.Token;
import ru.spbstu.metrics.api.services.TokenService;
import ru.spbstu.metrics.api.services.activity.ClickActivityService;
import ru.spbstu.metrics.api.services.activity.VisitActivityService;
import ru.spbstu.metrics.api.utils.GeneratorString;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class DataControllers {
    private final VisitActivityService visitActivityService;
    private final ClickActivityService clickActivityService;
    private final TokenService tokenService;

    @Autowired
    public DataControllers(VisitActivityService visitActivityService,
                           ClickActivityService clickActivityService, TokenService tokenService) {
        this.visitActivityService = visitActivityService;
        this.clickActivityService = clickActivityService;
        this.tokenService = tokenService;
    }

    @PostMapping("/create-token")
    public String createToken() {
        String tokenStr;
        do {
            tokenStr = GeneratorString.generateRandomKey();
        } while (tokenService.isTokenExists(tokenStr));
        tokenService.saveToken(new Token(tokenStr));
        return tokenStr;
    }

    @PostMapping("/delete-token")
    public void deleteToken(@RequestParam String token) {
        tokenService.deleteTokenByToken(token);
    }

    @PostMapping("/not-sync-tokens")
    public List<String> getTokensByEmail(@RequestParam List<String> tokens) {
        return tokens.stream().filter(token -> !tokenService.isTokenExists(token)).toList();
    }

    @PostMapping("/get-view-activity")
    public PageDTO<VisitActivityDTO> getViewActivity(
            @RequestParam String token,
            @RequestParam Integer numPage) {
        return PageDTO.of(visitActivityService.findByToken(token, numPage), VisitActivityDTO::new);
    }

    @PostMapping("/get-click-activity")
    public PageDTO<ClickActivityDTO> getClickActivity(
            @RequestParam String token,
            @RequestParam Integer numPage) {
        return PageDTO.of(clickActivityService.findByToken(token, numPage), ClickActivityDTO::new);
    }
}