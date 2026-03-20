package com.curry.app.controller;

import com.curry.app.dto.ApiResponse;
import com.curry.app.dto.GreetingResponse;
import com.curry.app.dto.HealthResponse;
import com.curry.app.service.ApplicationInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/curry")
public class ApplicationInfoController {

    private final ApplicationInfoService applicationInfoService;

    public ApplicationInfoController(ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }

    @GetMapping("/greeting")
    public ApiResponse<GreetingResponse> greeting() {
        return ApiResponse.ok(applicationInfoService.getGreeting());
    }

    @GetMapping("/health")
    public ApiResponse<HealthResponse> health() {
        return ApiResponse.ok(applicationInfoService.getHealth());
    }
}
