# Testing trong Spring Boot

> ### 🆕 Cập nhật Spring Boot 4.x (kiểm tra ngày 28/10/2026)
>
> Những thay đổi quan trọng về testing trong thế hệ Boot 4 / Framework 7:
>
> - **JUnit 6 là baseline mới** của Spring Framework 7 (trước đây là JUnit 5). JUnit 6 chủ yếu là bản "làm sạch": dọn các API deprecated, nâng yêu cầu nền tảng — kiến thức JUnit 5 trong chương này **vẫn áp dụng được** vì các annotation (`@Test`, `@BeforeEach`, `@SpringBootTest`...) giữ nguyên.
> - **Hạ tầng test được modularize:** cùng với việc Boot 4 tách nhỏ codebase, hạ tầng test cũng chia thành nhiều module. Nếu dự án không dùng starter POM chuẩn, bạn có thể phải thêm dependency test cụ thể cho từng công nghệ (ví dụ test cho Jackson/TestRestTemplate giờ nằm trong module riêng).
> - **Mockito 5.x / AssertJ** vẫn là bộ đôi mặc định trong `spring-boot-starter-test` — chương này dùng đúng bộ đó.
> - **Null safety JSpecify:** Spring API giờ khai báo nullable/non-null bằng JSpecify — khi viết test, IDE sẽ cảnh báo sớm hơn các giá trị `null` bất hợp lệ.
> - **Mock servlet:** `MockHttpServletRequest`/`MockHttpServletResponse` được cập nhật để bám sát hành vi Servlet 6.1 (đặc biệt với header/name `null`) — liên quan đến `@WebMvcTest` và MockMvc.
> - Lưu ý khi cập nhật từ 3.x: trước khi nâng cấp, hãy chạy hết test trên **Boot 3.5.x mới nhất** và dọn các API deprecated, vì Boot 4 đã loại bỏ chúng.

## Giới thiệu

Testing là một phần quan trọng trong phát triển phần mềm. Spring Boot cung cấp hệ sinh thái testing mạnh mẽ với JUnit 5, Mockito, và nhiều utility classes giúp viết test dễ dàng.

> **Phiên bản**: JUnit 5.13.4 (July 2025), Mockito 5.x, Spring Boot 4.x, Java 17+

## Các loại Test trong Spring Boot

```
┌────────────────────────────────────────────────┐
│         Unit Test (Nhanh, cô lập)              │
│    - Test 1 class, mock dependencies           │
├────────────────────────────────────────────────┤
│      Integration Test (Trung bình)             │
│    - Test nhiều layer, dùng H2/Testcontainers  │
├────────────────────────────────────────────────┤
│       End-to-End Test (Chậm, đầy đủ)           │
│    - Test toàn bộ ứng dụng với real DB         │
└────────────────────────────────────────────────┘
```

| Loại Test | Phạm vi | Tốc độ | Công cụ |
|-----------|---------|--------|---------|
| Unit Test | 1 class | ⚡ Nhanh | JUnit 5 + Mockito |
| Integration Test | Service + Repository | 🚗 Trung bình | @DataJpaTest, H2 |
| Web Layer Test | Controller | 🚗 Trung bình | @WebMvcTest, MockMvc |
| Full App Test | Toàn bộ | 🐢 Chậm | @SpringBootTest |

## JUnit 5 cơ bản

### Cấu trúc cơ bản

```java
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
```

### Các Assertions phổ biến

```java
@Test
void testAssertions() {
    // = (equals)
    assertEquals(5, 2 + 3);
    assertEquals("Hello", "Hel" + "lo");

    // So sánh object
    Task expected = new Task("Title", "Desc");
    Task actual = new Task("Title", "Desc");
    assertEquals(expected, actual);  // Dùng equals()

    // Null checks
    assertNull(getNullValue());
    assertNotNull(getNonNullValue());

    // Boolean checks
    assertTrue(isValid());
    assertFalse(isInvalid());

    // Exception
    assertThrows(IllegalArgumentException.class, () -> {
        throw new IllegalArgumentException("error");
    });

    // Multiple assertions (tất cả chạy dù có lỗi)
    assertAll("Task properties",
        () -> assertEquals("Title", task.getTitle()),
        () -> assertEquals("Desc", task.getDescription()),
        () -> assertFalse(task.isCompleted())
    );
}
```

## Mockito - Mocking Framework

### Mockito là gì?

**Mockito** giúp tạo mock objects (đối tượng giả) thay thế các dependencies thật, giúp test cô lập (isolated).

### Ví dụ: Mock Repository trong Service Test

```java
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
```

### Các Mockito Methods quan trọng

```java
// ============ WHEN/THEN - Cấu hình mock ============
when(mock.method()).thenReturn(value);          // Trả về giá trị
when(mock.method()).thenThrow(exception);        // Ném exception
when(mock.method(anyString())).thenReturn(v);    // Với any parameter

// ============ VERIFY - Kiểm tra mock được gọi ============
verify(mock, times(1)).method();       // Được gọi 1 lần
verify(mock, never()).method();        // Không bao giờ được gọi
verify(mock, atLeastOnce()).method();  // Ít nhất 1 lần
verify(mock).method(argThat(x -> x > 5));  // Với điều kiện

// ============ Argument Matchers ============
any()           // Any object
anyString()     // Any String
anyLong()       // Any Long
eq(value)       // Exact value
anyInt()
```

## Integration Testing với Spring Boot

### @SpringBootTest - Test toàn bộ ứng dụng

```java
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
```

### @WebMvcTest - Test Controller Layer

```java
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
```

### @DataJpaTest - Test Repository Layer

```java
@DataJpaTest
@DisplayName("TaskRepository Tests")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Tìm tasks theo priority")
    void shouldFindTasksByPriority() {
        // Arrange
        Task highPriority = new Task("High", "Desc", Task.Priority.HIGH);
        Task lowPriority = new Task("Low", "Desc", Task.Priority.LOW);
        taskRepository.saveAll(List.of(highPriority, lowPriority));

        // Act
        List<Task> result = taskRepository.findByPriority(Task.Priority.HIGH);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPriority()).isEqualTo(Task.Priority.HIGH);
    }
}
```

## Test Pyramid và Coverage

```
        /\
       /  \     E2E Tests (ít)
      /----\
     /      \   Integration Tests (vừa)
    /--------\
   /          \  Unit Tests (nhiều nhất)
  /____________\
```

**Quy tắc chung**: 70% Unit Tests, 20% Integration Tests, 10% E2E Tests.

## Chạy Tests với Maven

```bash
# Chạy tất cả tests
./mvnw test

# Chạy test cụ thể
./mvnw test -Dtest=TaskServiceTest

# Chạy nhiều tests
./mvnw test -Dtest="TaskServiceTest,TaskRepositoryTest"

# Bỏ qua tests khi build
./mvnw clean package -DskipTests

# Generate coverage report (cần JaCoCo plugin)
./mvnw clean test jacoco:report
# Report tại: target/site/jacoco/index.html
```

## Best Practices

1. **AAA Pattern**: Arrange - Act - Assert rõ ràng
2. **Một test một mục đích**: Không test nhiều thứ trong 1 test method
3. **Tên test rõ ràng**: `shouldXxxWhenYyy` hoặc tiếng Việt có @DisplayName
4. **Unit test không phụ thuộc**: Mock tất cả dependencies bên ngoài
5. **Dùng AssertJ**: Fluent assertions dễ đọc hơn JUnit assertions
6. **Test cả happy path và error path**
7. **Dùng @ActiveProfiles("test")** với database riêng cho test

## Tài liệu tham khảo

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://site.mockito.org/)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)
- [Baeldung Spring Boot Testing](https://www.baeldung.com/spring-boot-testing)

---

**Tiếp theo**: [6. Microservices với Spring Cloud](../6_microservices/)
