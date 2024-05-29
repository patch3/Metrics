package ru.spbstu.metrics.ui.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spbstu.metrics.ui.dtos.ClientDTO;

@FeignClient(name = "api-service", url = "http://localhost:8081/api")
public interface ApiFeignClient {
    @PostMapping("/service/authenticate")
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

}
