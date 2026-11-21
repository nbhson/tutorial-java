package com.example.demo.service;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskResponse;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    // Constructor Injection (best practice)
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Convert Entity -> Response
    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isCompleted(),
            task.getPriority(),
            task.getCreatedAt().toString(),
            task.getUpdatedAt().toString()
        );
    }

    // Tạo task mới
    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task(request.title(), request.description());
        task.setPriority(request.priority());
        
        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    // Lấy tất cả tasks
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    // Lấy task theo ID
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        return toResponse(task);
    }

    // Cập nhật task
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority());
        
        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    // Xóa task
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
