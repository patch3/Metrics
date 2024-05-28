package ru.spbstu.metrics.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.ui.configs.ServiceAuthApiConfig;
import ru.spbstu.metrics.ui.feignclient.ApiFeignClient;

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

    public boolean authentication(String email, String password) {
        return apiFeignClient.authenticate(serviceAuthApiConfig.getServiceBAuthToken(), email, password);
    }
}
