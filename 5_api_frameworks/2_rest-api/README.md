# REST API với Spring Boot

> ### 🆕 Cập nhật Spring Boot 4.x (kiểm tra ngày 28/10/2026)
>
> Những điểm liên quan trực tiếp đến việc xây dựng REST API trong chương này:
>
> - **API Versioning "first-class"** (mới trong Framework 7 / Boot 4): Spring MVC hỗ trợ version API chính thức qua annotation `@RequestMapping` thuộc tính `version` / `ApiVersion` và version resolver. Với REST API thực tế, bạn có thể đánh version theo header hoặc media type thay vì tự viết interceptor như cách làm thủ công trước đây.
> - **HTTP Service Clients** (mới): khai báo interface kiểu declarative client (tương tự Feign) với `@ImportHttpServices` — thay cho nhiều template code thủ công.
> - **Jackson 3 thay Jackson 2:** group `com.fasterxml.jackson` → `tools.jackson` (riêng `jackson-annotations` giữ nguyên `com.fasterxml.jackson.core`). Nếu code của bạn tự viết serializer/deserializer, lưu ý: `JsonObjectSerializer` → `ObjectValueSerializer`, `@JsonComponent` → `@JacksonComponent`, `@JsonMixin` → `@JacksonMixin`. Các property cấu hình `spring.jackson.read.*` / `spring.jackson.write.*` chuyển thành `spring.jackson.json.read.*` / `spring.jackson.json.write.*`.
> - **Baseline web:** Jakarta Servlet 6.1 — chạy trên **Tomcat 11+** hoặc Jetty 12.1+ (Undertow đã bị loại bỏ khỏi Boot 4).
> - **Null safety:** Spring dùng JSpecify thay cho annotation JSR-305; IDE sẽ cảnh báo null-safety chuẩn hóa hơn với API mới.
> - **Lợi ích khác của thế hệ này:** built-in resilience (retry, concurrency throttling) có sẵn cho các endpoint, giúp giảm nhu cầu thêm thư viện ngoài.
> - Các khái niệm cốt lõi trong chương (HTTP methods, CRUD, DTO, status codes) **không thay đổi** — chỉ nền tảng và một số API được nâng cấp.

## REST API là gì?

**REST** (Representational State Transfer) là một kiểu kiến trúc để thiết kế API. RESTful API sử dụng các HTTP methods chuẩn như GET, POST, PUT, DELETE để thực hiện các thao tác CRUD (Create, Read, Update, Delete) trên resources.

> **Phiên bản**: Spring Boot 4.x, Java 17+. Chúng ta sẽ xây dựng REST API cho một ứng dụng Quản lý Công việc (Task Management).

## Các HTTP Methods và ý nghĩa

| Method | CRUD | Mục đích | HTTP Status |
|--------|------|----------|-------------|
| GET | Read | Lấy dữ liệu | 200 OK |
| POST | Create | Tạo mới | 201 Created |
| PUT | Update | Cập nhật toàn bộ | 200/204 OK |
| PATCH | Update | Cập nhật một phần | 200/204 OK |
| DELETE | Delete | Xóa | 204 No Content |

## Tạo Model/Entity

### Task.java

```java
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean completed = false;

    private Integer priority; // 1 = High, 2 = Medium, 3 = Low

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Task() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
```

## Tạo DTO (Data Transfer Object)

DTO giúp tách biệt data mà API trả về với Entity trong database.

### TaskDTO.java

```java
package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO cho request tạo/cập nhật task
public record TaskRequest(
    @NotBlank(message = "Title không được để trống")
    @Size(min = 3, max = 100, message = "Title phải từ 3-100 ký tự")
    String title,
    
    @Size(max = 1000, message = "Description tối đa 1000 ký tự")
    String description,
    
    Integer priority
) {}

// DTO cho response trả về
public record TaskResponse(
    Long id,
    String title,
    String description,
    boolean completed,
    Integer priority,
    String createdAt,
    String updatedAt
) {}
```

## Tạo Repository

### TaskRepository.java

```java
package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Tìm tất cả task chưa hoàn thành
    List<Task> findByCompletedFalse();
    
    // Tìm theo priority
    List<Task> findByPriority(Integer priority);
    
    // Tìm kiếm trong title hoặc description
    List<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        String title, String description
    );
}
```

## Tạo Service Layer

### TaskService.java

```java
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
```

## Tạo REST Controller

### TaskController.java

```java
package com.example.demo.controller;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskResponse;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") // Cho phép CORS (dev only)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ============ CREATE ============
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ============ READ ALL ============
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // ============ READ ONE ============
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse response = taskService.getTaskById(id);
        return ResponseEntity.ok(response);
    }

    // ============ UPDATE ============
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    // Cập nhật một phần (toggle completed)
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TaskResponse> toggleCompleted(@PathVariable Long id) {
        // Giả lập: lấy task, toggle completed, lưu lại
        TaskResponse current = taskService.getTaskById(id);
        TaskRequest request = new TaskRequest(
            current.title(),
            current.description(),
            current.priority()
        );
        TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    // ============ DELETE ============
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
```

## Xử lý Exception

### GlobalExceptionHandler.java

```java
package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Not Found");
        error.put("message", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Xử lý validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Validation Failed");
        
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.put("errors", fieldErrors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
```

## Test REST API với curl

```bash
# Tạo task mới (POST)
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Học Spring Boot","description":"Học REST API","priority":1}'

# Lấy tất cả tasks (GET)
curl http://localhost:8080/api/tasks

# Lấy task theo ID (GET)
curl http://localhost:8080/api/tasks/1

# Cập nhật task (PUT)
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Học Spring Boot Advanced","description":"Đã học REST API","priority":2}'

# Xóa task (DELETE)
curl -X DELETE http://localhost:8080/api/tasks/1
```

## Các Annotation quan trọng

| Annotation | Mô tả |
|------------|-------|
| `@RestController` | Combines `@Controller` và `@ResponseBody`, tự động serialize response thành JSON |
| `@RequestMapping` | Map URL base cho tất cả methods trong controller |
| `@GetMapping` | HTTP GET method |
| `@PostMapping` | HTTP POST method |
| `@PutMapping` | HTTP PUT method |
| `@PatchMapping` | HTTP PATCH method |
| `@DeleteMapping` | HTTP DELETE method |
| `@PathVariable` | Lấy giá trị từ URL path (e.g., `/tasks/{id}`) |
| `@RequestBody` | Deserialize JSON body thành Java object |
| `@Valid` | Trigger Bean Validation trước khi xử lý |
| `@CrossOrigin` | Cho phép CORS requests |

## Best Practices cho REST API

1. **Dùng plural nouns cho resource names**: `/tasks` thay vì `/task`
2. **Dùng HTTP status codes đúng**: 200, 201, 400, 404, 500...
3. **Luôn validate input**: Dùng Bean Validation (`@Valid`)
4. **Dùng DTOs**: Không expose trực tiếp JPA entities ra API
5. **Version API**: `/api/v1/tasks`, `/api/v2/tasks`
6. **Paginate large results**: `/tasks?page=0&size=20`
7. **Document API**: Dùng OpenAPI/Swagger

## Tài liệu tham khảo

- [Building a RESTful Web Service](https://spring.io/guides/tutorials/rest/)
- [REST API Design Best Practices](https://restfulapi.net/)
- [Baeldung REST with Spring](https://www.baeldung.com/rest-with-spring-series)

---

**Tiếp theo**: [3. Data Access với Spring Data JPA](../3_data-access-jpa/)
