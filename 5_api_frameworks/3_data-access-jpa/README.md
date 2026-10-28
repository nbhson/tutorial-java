# Data Access với Spring Data JPA

> ### 🆕 Cập nhật Spring Boot 4.x (kiểm tra ngày 28/10/2026)
>
> Những thay đổi quan trọng về tầng data access trong thế hệ Boot 4 / Framework 7:
>
> - **JPA 3.2 + Hibernate ORM 7.1/7.2** là baseline mới (trước đây là JPA 3.1 + Hibernate 6.x).
> - **Inject `EntityManager` trực tiếp:** từ JPA 3.2, bạn có thể inject `EntityManagerFactory` và cả `EntityManager` dùng chung qua `@Autowired`/`@Inject` (kể cả với qualifier để chọn persistence unit) — không cần cấu hình `SharedEntityManagerBean` như trước.
> - **Hibernate 7 chặt chẽ hơn với detached entity:** một entity detached **không còn được gắn lại (re-associate)** vào persistence context. Cập nhật entity bằng `merge()` hoặc đọc lại trong phiên làm việc thay vì set trực tiếp đối tượng detached.
> - **Gói nội bộ đã đổi:** các lớp Hibernate "native" của Spring (từ `org.springframework.orm.hibernate5`) chuyển sang `orm.jpa.hibernate` — chỉ còn `LocalSessionFactoryBean/Builder`, `HibernateTransactionManager`, `SpringBeanContainer`, `SpringSessionContext`. Nếu bạn dùng JPA chuẩn (mục tiêu của chương này) thì không bị ảnh hưởng.
> - **Namespace:** mọi annotation persistence dùng `jakarta.persistence.*` (`jakarta.persistence.Entity`, `jakarta.persistence.Id`...) theo Jakarta EE 11 — chương này đã dùng chuẩn `jakarta.*` từ đầu.
> - **Jackson cho @Lob/@Convert JSON:** nếu bạn serialize JSON trong entity, nhớ Boot 4 dùng Jackson 3 (group `tools.jackson`).
> - Kiến thức cốt lõi trong chương (ORM, Entity, Repository, query derivation, transaction) **không thay đổi** — bạn vẫn áp dụng được nguyên trạng trên Boot 4.

## Giới thiệu Spring Data JPA

**Spring Data JPA** là phần của Spring Data ecosystem, giúp đơn giản hóa việc truy cập database. Thay vì viết boilerplate code JDBC hay Hibernate XML mappings, JPA (Java Persistence API) cho phép bạn ánh xạ Java objects sang database tables một cách tự động (ORM - Object-Relational Mapping).

> **Lưu ý phiên bản**: Hướng dẫn này dùng Spring Data JPA 2026.x, Spring Boot 4.x, Java 17+. Database: H2 (in-memory) cho dev.

## ORM là gì?

```
┌─────────────────┐         ┌─────────────────┐
│   Java Objects  │  ←→     │   Database      │
│                 │  JPA    │   Tables        │
│  Task {         │         │  tasks {        │
│    id: Long,    │         │    id: BIGINT,  │
│    title: Str,  │         │    title: VARCHAR│
│    completed    │         │    completed   │
│  }             │         │  }             │
└─────────────────┘         └─────────────────┘
```

## Cấu hình Database

### application.yml

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  
  jpa:
    hibernate:
      ddl-auto: create-drop  # Tạo schema tự động (dev)
    show-sql: true           # In SQL ra console (dev)
    properties:
      hibernate:
        format_sql: true     # Format SQL đẹp hơn
    open-in-view: false      # Tránh lazy loading issues

  h2:
    console:
      enabled: true          # Mở H2 Console: http://localhost:8080/h2-console
      path: /h2-console
```

### pom.xml - Thêm dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Thay H2 bằng MySQL/PostgreSQL khi deploy -->
    <!--
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
    -->
</dependencies>
```

## Entity Mapping

### Annotation quan trọng

| Annotation | Mô tả |
|------------|-------|
| `@Entity` | Đánh dấu class là một JPA Entity |
| `@Table(name="...")` | Map đến table cụ thể |
| `@Id` | Primary key |
| `@GeneratedValue` | Auto-generate giá trị ID |
| `@Column(name="...", nullable=false)` | Cấu hình column |
| `@Transient` | Không lưu vào database |
| `@Temporal` | Cho kiểu Date/Time (legacy, nên dùng java.time) |

### Task Entity (Hoàn chỉnh)

```java
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks", indexes = {
    @Index(name = "idx_title", columnList = "title"),
    @Index(name = "idx_completed", columnList = "completed")
})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Boolean completed = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Priority priority = Priority.MEDIUM;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    // Lifecycle Callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Task() {}

    public Task(String title, String description, Priority priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    // Getters, Setters...
}
```

## Mối quan hệ (Relationships)

### One-to-Many: User có nhiều Tasks

```java
// User.java
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    // CascadeType.ALL: Khi xóa User, xóa luôn Tasks
    // orphanRemoval: Xóa Task khỏi list cũng xóa trong DB
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private List<Task> tasks = new ArrayList<>();

    // Helper method để thêm task
    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setUser(null);
    }
}
```

```java
// Task.java - Thêm field user
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Trong class Task, có getter:
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
```

### Many-to-Many: Task có nhiều Tags

```java
// Tag.java
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Task> tasks = new HashSet<>();
}
```

```java
// Task.java - Cập nhật
@Entity
public class Task {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "task_tags",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getTasks().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getTasks().remove(this);
    }
}
```

## Repository Interfaces

Spring Data JPA tự động tạo implementation dựa trên method name.

### TaskRepository.java

```java
package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;        // Phân trang
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Custom query
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // ============ Derived Queries (Tự động generate SQL) ============

    // SELECT * FROM tasks WHERE completed = false
    List<Task> findByCompletedFalse();

    // SELECT * FROM tasks WHERE priority = ?
    List<Task> findByPriority(Task.Priority priority);

    // SELECT * FROM tasks WHERE title LIKE '%keyword%'
    List<Task> findByTitleContaining(String keyword);

    // SELECT * FROM tasks WHERE user_id = ?
    List<Task> findByUserId(Long userId);

    // ============ Custom Queries với @Query ============

    @Query("SELECT t FROM Task t WHERE t.completed = false AND t.priority = :priority ORDER BY t.createdAt DESC")
    List<Task> findIncompleteTasksByPriority(@Param("priority") Task.Priority priority);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.user.id = :userId AND t.completed = true")
    long countCompletedTasksByUser(@Param("userId") Long userId);

    // ============ Pagination ============

    Page<Task> findByUser(User user, Pageable pageable);

    Page<Task> findByCompleted(boolean completed, Pageable pageable);

    // ============ Optional Queries ============

    Optional<Task> findByTitle(String title);
}
```

## Phân Trang và Sắp xếp

### PaginatedTaskController.java

```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        // Tạo Pageable object
        Sort sort = direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Lấy dữ liệu phân trang
        Page<Task> tasks = taskRepository.findAll(pageable);

        return ResponseEntity.ok(tasks);
    }

    // Response ví dụ:
    // {
    //   "content": [...],           // Danh sách tasks
    //   "totalElements": 100,        // Tổng số records
    //   "totalPages": 10,           // Tổng số pages
    //   "size": 10,                 // Kích thước page
    //   "number": 0,                // Page hiện tại (0-indexed)
    //   "first": true,              // Là page đầu tiên?
    //   "last": false               // Là page cuối cùng?
    // }
}
```

## Custom Repository Implementation

### TaskRepositoryCustom.java

```java
// Interface cho custom methods
public interface TaskRepositoryCustom {
    List<Task> searchTasks(String keyword, Task.Priority priority, Boolean completed);
}
```

### TaskRepositoryCustomImpl.java

```java
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Task> searchTasks(String keyword, Task.Priority priority, Boolean completed) {
        StringBuilder jpql = new StringBuilder("SELECT t FROM Task t WHERE 1=1");

        if (keyword != null) {
            jpql.append(" AND (t.title LIKE :keyword OR t.description LIKE :keyword)");
        }
        if (priority != null) {
            jpql.append(" AND t.priority = :priority");
        }
        if (completed != null) {
            jpql.append(" AND t.completed = :completed");
        }

        TypedQuery<Task> query = entityManager.createQuery(jpql.toString(), Task.class);

        if (keyword != null) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        if (priority != null) {
            query.setParameter("priority", priority);
        }
        if (completed != null) {
            query.setParameter("completed", completed);
        }

        return query.getResultList();
    }
}
```

### Kết hợp trong TaskRepository

```java
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom {
    // Có cả methods của JpaRepository và TaskRepositoryCustom
}
```

## Auditing (Tự động điền thông tin)

### Enable Auditing

```java
@SpringBootApplication
@EnableJpaAuditing
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

### Sử dụng @CreatedDate, @LastModifiedDate

```java
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}
```

## Best Practices

1. **Dùng DTOs**: Không return Entity trực tiếp từ API (security risk)
2. **Fetch Strategy**: Dùng `LAZY` cho relationships, `EAGER` chỉ khi cần thiết
3. **Batch Operations**: Dùng `saveAll()` cho batch insert thay vì vòng lặp
4. **Transactions**: Đánh dấu `@Transactional` cho tất cả methods modify data
5. **Indexes**: Tạo index cho các cột thường xuyên query
6. **N+1 Problem**: Tránh bằng `JOIN FETCH` trong JPQL hoặc `@EntityGraph`

## Tài liệu tham khảo

- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Baeldung JPA/Hibernate](https://www.baeldung.com/learn-jpa-hibernate/)
- [JPA Entity Mappings](https://www.baeldung.com/jpa-entity-relationships)

---

**Tiếp theo**: [4. Validation và Security](../4_validation-security/)
