package com.example.demo.service;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TaskService Tests")
class TaskServiceTest {

    private TaskService taskService;

    @BeforeAll
    static void setupClass() {
        // Chạy 1 lần trước tất cả tests
        System.out.println("Setup class");
    }

    @BeforeEach
    void setUp() {
        // Chạy trước mỗi test
        taskService = new TaskService();
    }

    @AfterEach
    void tearDown() {
        // Chạy sau mỗi test
        System.out.println("Cleanup");
    }

    @AfterAll
    static void tearDownClass() {
        // Chạy 1 lần sau tất cả tests
    }

    @Test
    @DisplayName("Nên tạo task thành công với title hợp lệ")
    void shouldCreateTaskWithValidTitle() {
        // Arrange (Chuẩn bị)
        String title = "Học Spring Boot";

        // Act (Thực hiện)
        Task task = taskService.createTask(title, "Mô tả");

        // Assert (Kiểm tra)
        assertNotNull(task);
        assertEquals(title, task.getTitle());
        assertFalse(task.isCompleted());
    }

    @Test
    @DisplayName("Ném exception khi title rỗng")
    void shouldThrowExceptionWhenTitleIsBlank() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> taskService.createTask("", "Mô tả")
        );
        assertEquals("Title không được để trống", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Java", "Spring Boot", "JPA"})
    @DisplayName("Test với nhiều input khác nhau")
    void shouldAcceptVariousTitles(String title) {
        Task task = taskService.createTask(title, "");
        assertEquals(title, task.getTitle());
    }
}
