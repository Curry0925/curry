package com.curry.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void greetingReturnsApplicationInfo() throws Exception {
        mockMvc.perform(get("/api/curry/greeting"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.application").value("curry-service"))
                .andExpect(jsonPath("$.data.message").value("Hello from Curry Service!"));
    }

    @Test
    void healthReturnsUp() throws Exception {
        mockMvc.perform(get("/api/curry/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("UP"));
    }
}
