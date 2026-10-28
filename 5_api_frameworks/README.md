# API Frameworks - Các Framework xây dựng REST API

## Giới thiệu tổng quan

Phần này sẽ hướng dẫn bạn xây dựng ứng dụng web hiện đại với **Spring Boot 4.x** - framework phổ biến nhất để phát triển ứng dụng Java trong doanh nghiệp. Chúng ta sẽ đi từ cơ bản đến nâng cao, từ việc tạo một dự án Spring Boot đầu tiên đến việc xây dựng kiến trúc microservices hoàn chỉnh.

## Nội dung chính

### 1. [Spring Boot Introduction](./1_spring-boot-intro/)
Giới thiệu Spring Boot, cách tạo project với Spring Initializr, cấu trúc project, và các khái niệm cơ bản về dependency management.

### 2. [REST API với Spring Boot](./2_rest-api/)
Xây dựng RESTful API với các annotation như `@RestController`, `@GetMapping`, `@PostMapping`. Thực hành CRUD operations đầy đủ.

### 3. [Data Access với Spring Data JPA](./3_data-access-jpa/)
Kết nối database với Spring Data JPA, tạo Entity, Repository, và làm việc với các mối quan hệ (relationships) giữa các bảng.

### 4. [Validation và Security](./4_validation-security/)
Validation dữ liệu đầu vào với Bean Validation API (`@Valid`, `@NotNull`, etc.), và bảo mật ứng dụng với Spring Security + JWT.

### 5. [Testing trong Spring Boot](./5_testing/)
Viết unit test và integration test với JUnit 5 và Mockito. Sử dụng `@SpringBootTest` và các best practices cho testing.

### 6. [Microservices với Spring Cloud](./6_microservices/)
Giới thiệu kiến trúc microservices, Service Discovery với Eureka, API Gateway, và cách kết nối các services với nhau.

## Yêu cầu

- **Java**: 17 trở lên (Khuyến nghị Java 21 hoặc 25)
- **Spring Boot**: 4.x (phiên bản mới nhất 2026)
- **Build Tool**: Maven hoặc Gradle
- **IDE**: IntelliJ IDEA (khuyến nghị) hoặc VS Code

## Phiên bản và Công nghệ sử dụng

| Công nghệ | Phiên bản | Ghi chú |
|-----------|-----------|---------|
| Java | 17+ | Minimum, khuyến nghị 21/25 |
| Spring Boot | 4.1.0 | Latest stable (June 2026) |
| Spring Framework | 7.x | Đi kèm Spring Boot 4.x |
| Spring Data JPA | 2026.x | Tương thích với Spring Boot 4.x |
| JUnit 5 | 5.13.4 | Latest (July 2025) |
| H2 Database | 2.x | Database cho development |
| Lombok | 1.18.x | Giảm boilerplate code |

## Tài liệu tham khảo

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Initializr](https://start.spring.io)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Spring Security Reference](https://spring.io/projects/spring-security)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

## Mục tiêu học tập

Sau khi hoàn thành phần này, bạn sẽ có thể:

1. ✅ Tạo và cấu hình một dự án Spring Boot từ đầu
2. ✅ Xây dựng REST API với đầy đủ CRUD operations
3. ✅ Kết nối và thao tác với database sử dụng Spring Data JPA
4. ✅ Validate dữ liệu và implement JWT authentication
5. ✅ Viết unit test và integration test cho ứng dụng Spring
6. ✅ Hiểu và áp dụng kiến trúc microservices cơ bản

---

**Tiếp theo**: [1. Spring Boot Introduction](./1_spring-boot-intro/)
