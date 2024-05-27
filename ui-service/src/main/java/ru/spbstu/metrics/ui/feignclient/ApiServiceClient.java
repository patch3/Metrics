package ru.spbstu.metrics.ui.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "api-service", url = "http://localhost:8081")
public interface ApiServiceClient {
    @PostMapping("/authenticate")
    boolean authenticate(@RequestParam("username") String username, @RequestParam("password") String password);
}
