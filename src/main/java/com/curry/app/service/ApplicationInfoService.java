package com.curry.app.service;

import com.curry.app.dto.GreetingResponse;
import com.curry.app.dto.HealthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationInfoService {

    private final String applicationName;

    public ApplicationInfoService(@Value("${spring.application.name}") String applicationName) {
        this.applicationName = applicationName;
    }

    public GreetingResponse getGreeting() {
        return new GreetingResponse(applicationName, "Hello from Curry Service!");
    }

    public HealthResponse getHealth() {
        return new HealthResponse("UP");
    }
}
