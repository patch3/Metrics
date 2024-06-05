package ru.spbstu.metrics.ui.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spbstu.metrics.ui.dtos.PageDTO;
import ru.spbstu.metrics.ui.dtos.activity.ClickActivityDTO;
import ru.spbstu.metrics.ui.dtos.activity.VisitActivityDTO;

import java.util.List;

@FeignClient(name = "api-service", url = "http://localhost:8081/api/service")
public interface ApiFeignClient {
    @PostMapping("/create-token")
    String createToken(
            @RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken
    );

    @PostMapping("/not-sync-tokens")
    List<String> syncTokens(
            @RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken,
            @RequestParam("tokens") List<String> tokens
    );

    @PostMapping("/delete-token")
    void deleteToken(@RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken, String token);

    @PostMapping("/get-view-activity")
    PageDTO<VisitActivityDTO> getViewActivity(
            @RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken,
            @RequestParam("token") String token,
            @RequestParam("numPage") Integer numPage
    );

    @PostMapping("/get-click-activity")
    PageDTO<ClickActivityDTO> getClickActivity(
            @RequestHeader("SERVICE-AUTH-TOKEN") String serviceAuthToken,
            @RequestParam("token") String token,
            @RequestParam("numPage") Integer numPage
    );
}
