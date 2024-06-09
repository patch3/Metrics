package ru.spbstu.metrics.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/", "/home", "/index"})
public class HomeControllers {


    @GetMapping({"/", "/home", "/index"})
    public String initializeBasePage(
            @RequestParam(value = "error", required = false) String error,
            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Не удачная аунтификация");
        }
        model.addAttribute("title", "Metrics");
        return "/index";
    }

}
