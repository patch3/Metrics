package ru.spbstu.metrics.ui.controllers.staff;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spbstu.metrics.ui.models.RelationshipBetweenClientAndToken;
import ru.spbstu.metrics.ui.service.ApiClientService;
import ru.spbstu.metrics.ui.service.ClientService;
import ru.spbstu.metrics.ui.service.RelationshipBetweenClientAndTokenService;

import java.security.Principal;

@Controller
@RequestMapping("/staff/token")
public class TokenController {
    private final ApiClientService apiClientService;
    private final RelationshipBetweenClientAndTokenService relationshipService;
    private final ClientService clientService;

    @Autowired
    public TokenController(ApiClientService apiClientService,
                           RelationshipBetweenClientAndTokenService relationshipService,
                           ClientService clientService) {
        this.apiClientService = apiClientService;
        this.relationshipService = relationshipService;
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public String createToken(@RequestParam String tokenName, Principal principal) {
        val tokenStr = apiClientService.createToken();
        val client = clientService.getClientByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        relationshipService.save(
                RelationshipBetweenClientAndToken.builder()
                        .name(tokenName)
                        .token(tokenStr)
                        .client(client)
                        .build()
        );
        return "redirect:/staff/menu";
    }

    @GetMapping("/remove")
    public String createToken(@RequestParam String tokenSrt) {
        apiClientService.deleteToken(tokenSrt);
        relationshipService.deleteByToken(tokenSrt);
        return "redirect:/staff/menu";
    }
}
