package ru.spbstu.metrics.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.metrics.ui.configs.ServiceAuthApiConfig;
import ru.spbstu.metrics.ui.dtos.ClientDTO;
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

    public boolean authentication(String email, String password) {
        return apiFeignClient.authenticate(serviceAuthApiConfig.getServiceBAuthToken(), email, password);
    }

    public boolean registration(ClientDTO clientDTO) {
        return apiFeignClient.registration(serviceAuthApiConfig.getServiceBAuthToken(), clientDTO);
    }

    public List<String> getTokensByClientEmail(String email) {
        return apiFeignClient.getTokensByClientEmail(serviceAuthApiConfig.getServiceBAuthToken(), email);
    }

    public List<VisitActivityDTO> getViewActivity(String token, Integer numPage) {
        return apiFeignClient.getViewActivity(serviceAuthApiConfig.getServiceBAuthToken(), token, numPage);
    }

    public List<ClickActivityDTO> getClickActivity(String token, Integer numPage) {
        return apiFeignClient.getClickActivity(serviceAuthApiConfig.getServiceBAuthToken(), token, numPage);
    }
}
