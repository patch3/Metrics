package ru.spbstu.metrics.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spbstu.metrics.ui.service.ApiClientService;

@Controller
@RequestMapping({"/", "/home", "/index"})
public class HomeControllers {
    private final ApiClientService apiClientService;

    @Autowired
    public HomeControllers(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }


    @GetMapping({"/", "/home", "/index"})
    public String initializeBasePage(
            @RequestParam(value = "error", required = false) String error,
            Model model) {
        if (error != null) {
            model.addAttribute("errorMassage", "Ошибка входа");
        }
        model.addAttribute("title", "Metrics");
        return "/index";
    }

}
