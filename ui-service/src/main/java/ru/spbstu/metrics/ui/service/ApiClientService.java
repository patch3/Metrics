package ru.spbstu.metrics.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.ui.configs.security.ServiceAuthApiConfig;
import ru.spbstu.metrics.ui.dtos.PageDTO;
import ru.spbstu.metrics.ui.dtos.activity.ClickActivityDTO;
import ru.spbstu.metrics.ui.dtos.activity.VisitActivityDTO;
import ru.spbstu.metrics.ui.feignclient.ApiFeignClient;

import java.util.List;

@Slf4j
@Service
public class ApiClientService {
    private final ApiFeignClient apiFeignClient;
    private final ServiceAuthApiConfig serviceAuthApiConfig;

    @Autowired
    public ApiClientService(ApiFeignClient apiFeignClient, ServiceAuthApiConfig serviceAuthApiConfig) {
        this.apiFeignClient = apiFeignClient;
        this.serviceAuthApiConfig = serviceAuthApiConfig;
    }

    public String createToken() {
        return apiFeignClient.createToken(serviceAuthApiConfig.getServiceBAuthToken());
    }

    public void deleteToken(String token) {
        apiFeignClient.deleteToken(serviceAuthApiConfig.getServiceBAuthToken(), token);
    }

    public List<String> getNotSyncTokens(List<String> tokens) {
        return apiFeignClient.syncTokens(serviceAuthApiConfig.getServiceBAuthToken(), tokens);
    }

    public PageDTO<VisitActivityDTO> getViewActivity(String token, Integer numPage) {
        return apiFeignClient.getViewActivity(serviceAuthApiConfig.getServiceBAuthToken(), token, numPage);
    }

    public PageDTO<ClickActivityDTO> getClickActivity(String token, Integer numPage) {
        return apiFeignClient.getClickActivity(serviceAuthApiConfig.getServiceBAuthToken(), token, numPage);
    }
}
