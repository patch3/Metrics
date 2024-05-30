package ru.spbstu.metrics.ui.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spbstu.metrics.ui.dtos.ClientDTO;
import ru.spbstu.metrics.ui.dtos.activity.ClickActivityDTO;
import ru.spbstu.metrics.ui.dtos.activity.VisitActivityDTO;

import java.util.List;

@FeignClient(name = "api-service", url = "http://localhost:8081/api/service")
public interface ApiFeignClient {
    @PostMapping("/authenticate")
    boolean authenticate(
            @RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );

    @PostMapping("/register")
    boolean registration(
            @RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken,
            @RequestBody ClientDTO clientDTO
    );

    @PostMapping("/get-tokens-by-email")
    List<String> getTokensByClientEmail(
            @RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken,
            @RequestParam("email") String email
    );

    @PostMapping("/get-view-activity")
    List<VisitActivityDTO> getViewActivity(
            @RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken,
            @RequestParam("token") String token,
            @RequestParam("numPage") Integer numPage
    );

    @PostMapping("/get-click-activity")
    List<ClickActivityDTO> getClickActivity(
            @RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken,
            @RequestParam("token") String token,
            @RequestParam("numPage") Integer numPage
    );
}
