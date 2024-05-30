package ru.spbstu.metrics.ui.controllers.staff;


import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spbstu.metrics.ui.service.ApiClientService;

@Controller
@RequestMapping("/staff/menu")
public class MenuTokenController {

    private final ApiClientService apiClientService;

    @Autowired
    public MenuTokenController(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    @GetMapping
    public String initializeBasePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("error", "You are not authorized");
            return "/error";
        }
        val email = authentication.getName();
        val tokens = apiClientService.getTokensByClientEmail(email);

        model.addAttribute("email", email);
        model.addAttribute("tokens", tokens);
        model.addAttribute("title", "dl-stat");
        return "/staff/menu";
    }
}
