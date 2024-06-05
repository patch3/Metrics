package ru.spbstu.metrics.ui.controllers.staff;

import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spbstu.metrics.ui.dtos.ClientDTO;
import ru.spbstu.metrics.ui.service.ClientService;

import java.security.Principal;

@Controller
@RequestMapping("/staff/registration")
public class RegistrationController {
    private final ClientService clientService;

    public RegistrationController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String registerPage(Model model, Principal principal) {
        model.addAttribute("title", "dl-stat | Registration");
        val username = principal.getName();
        model.addAttribute("username", username);
        return "/staff/registration";
    }

    @PostMapping("/process")
    public String registerProcess(ClientDTO clientDTO) {
        if (clientDTO.getUsername().isEmpty() || clientDTO.getPassword().isEmpty()
                || clientService.isNotClientExists(clientDTO.getUsername())) {
            return "redirect:/staff/registration?error";
        }
        clientService.registration(clientDTO.getUsername(), clientDTO.getPassword());
        return "/staff/menu";
    }
}
