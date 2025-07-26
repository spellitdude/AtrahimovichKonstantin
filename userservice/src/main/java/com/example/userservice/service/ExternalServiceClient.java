package com.example.userservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class ExternalServiceClient {
    @CircuitBreaker(name = "externalService", fallbackMethod = "fallback")
    public String callExternalApi() {
        // вызов внешнего API
        RestOperations restTemplate = null;
        return restTemplate.getForObject("http://external/api", String.class);
    }

    public String fallback(Exception e) {
        return "Fallback response due to failure: " + e.getMessage();
    }
}
