package com.example.demo.integration;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")  // Dùng application-test.yml
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("Tạo và truy xuất task từ database")
    void shouldCreateAndRetrieveTask() {
        // Arrange
        Task task = new Task("Integration Test", "Test với H2");

        // Act
        Task saved = taskRepository.save(task);
        Optional<Task> found = taskRepository.findById(saved.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Integration Test");
    }

    @Test
    @Order(2)
    @DisplayName("Tìm tasks theo completed status")
    void shouldFindTasksByCompleted() {
        // Arrange
        Task task1 = new Task("Task 1", "Desc");
        Task task2 = new Task("Task 2", "Desc");
        task2.setCompleted(true);
        taskRepository.saveAll(List.of(task1, task2));

        // Act
        List<Task> incomplete = taskRepository.findByCompletedFalse();

        // Assert
        assertThat(incomplete).hasSize(1);
        assertThat(incomplete.get(0).getTitle()).isEqualTo("Task 1");
    }
}
