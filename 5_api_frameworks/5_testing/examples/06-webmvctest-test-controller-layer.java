package com.example.demo.controller;

import com.example.demo.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@DisplayName("TaskController Tests")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    @DisplayName("GET /api/tasks trả về 200 OK")
    void shouldReturnAllTasks() throws Exception {
        // Arrange
        when(taskService.getAllTasks()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /api/tasks trả về 201 Created")
    void shouldCreateTask() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "title": "New Task",
                        "description": "Test",
                        "priority": 1
                    }
                    """))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST với title rỗng trả về 400 Bad Request")
    void shouldRejectInvalidTask() throws Exception {
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "title": "",
                        "priority": 1
                    }
                    """))
            .andExpect(status().isBadRequest());
    }
}
