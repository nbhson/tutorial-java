package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService với Mock Repository")
class TaskServiceMockTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Lấy task theo ID thành công")
    void shouldGetTaskById() {
        // Arrange
        Task task = new Task("Học Java", "Mô tả");
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        Task result = taskService.getTaskById(1L);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Học Java");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Ném exception khi task không tồn tại")
    void shouldThrowExceptionWhenTaskNotFound() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.getTaskById(99L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Task not found");
    }

    @Test
    @DisplayName("Lấy tất cả tasks")
    void shouldGetAllTasks() {
        // Arrange
        List<Task> tasks = Arrays.asList(
            new Task("Task 1", "Desc 1"),
            new Task("Task 2", "Desc 2")
        );
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Task::getTitle)
            .containsExactly("Task 1", "Task 2");
    }

    @Test
    @DisplayName("Xóa task thành công")
    void shouldDeleteTask() {
        // Arrange
        when(taskRepository.existsById(1L)).thenReturn(true);

        // Act
        taskService.deleteTask(1L);

        // Verify
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
