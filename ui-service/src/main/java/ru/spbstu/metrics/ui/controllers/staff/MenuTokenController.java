package ru.spbstu.metrics.ui.controllers.staff;


import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spbstu.metrics.ui.models.RelationshipBetweenClientAndToken;
import ru.spbstu.metrics.ui.service.ApiClientService;
import ru.spbstu.metrics.ui.service.RelationshipBetweenClientAndTokenService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/staff/menu")
public class MenuTokenController {
    private final ApiClientService apiClientService;
    private final RelationshipBetweenClientAndTokenService relationshipService;

    @Autowired
    public MenuTokenController(ApiClientService apiClientService,
                               RelationshipBetweenClientAndTokenService relationshipService) {
        this.apiClientService = apiClientService;
        this.relationshipService = relationshipService;
    }

    @GetMapping
    public String initializeBasePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("error", "You are not authorized");
            return "/error";
        }
        val username = authentication.getName();

        val tokensStr = relationshipService.getAllByClientUsername(username).stream()
                .map(RelationshipBetweenClientAndToken::getToken)
                .collect(Collectors.toList());

        for (String token : apiClientService.getNotSyncTokens(tokensStr)) {
            relationshipService.deleteByToken(token);
            tokensStr.remove(token);
        }

        val tokens = relationshipService.getTokensByList(tokensStr);

        model.addAttribute("username", username);
        model.addAttribute("tokens", tokens);
        model.addAttribute("title", "dl-stat");
        return "/staff/menu";
    }
}