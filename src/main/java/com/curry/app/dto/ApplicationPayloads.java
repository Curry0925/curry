package com.curry.app.dto;

public final class ApplicationPayloads {

    private ApplicationPayloads() {
    }

    public record ApiResponse<T>(boolean success, T data) {

        public static <T> ApiResponse<T> ok(T data) {
            return new ApiResponse<>(true, data);
        }
    }

    public record GreetingResponse(String application, String message) {
    }

    public record HealthResponse(String status) {
    }
}
