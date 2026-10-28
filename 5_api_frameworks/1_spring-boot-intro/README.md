# Spring Boot Introduction - Giới thiệu Spring Boot

> ### 🆕 Cập nhật Spring Boot 4.x (kiểm tra ngày 28/10/2026)
>
> Chương này được viết lại theo hướng Spring Boot hiện đại. Tại thời điểm cập nhật:
>
> - **Spring Boot 4.x** là thế hệ hiện hành: 4.0.0 GA ngày **20/11/2025**, bản ổn định mới nhất là **4.1.2** (22/10/2026); dòng 4.0.x ở 4.0.9, 4.2.0-RC1 đang trong giai đoạn release candidate. Nền tảng là **Spring Framework 7.0** (GA 13/11/2025).
> - **Baseline hệ sinh thái:** Spring Boot 4 dựa trên **Jakarta EE 11** — namespace `jakarta.*` (không còn `javax.*`, kể cả `@PostConstruct`, `@Resource`, `@Inject`). Yêu cầu Servlet 6.1 (Tomcat 11.0 / Jetty 12.1), JPA 3.2, Bean Validation 3.1.
> - **Java:** baseline vẫn là **Java 17**, nhưng Java 25 LTS được hỗ trợ "first-class" — bạn có thể chạy toàn bộ ví dụ trong chương này bằng JDK 25.
> - **Kiến trúc module mới:** Spring Boot 4 **modularize toàn bộ codebase** — các jar lớn (autoconfigure, starter) được tách thành nhiều module nhỏ, tập trung hơn. Nếu bạn dùng starter POM chuẩn, mã nguồn vẫn chạy như cũ; nếu tự quản lý dependency, hãy kiểm tra lại tên module.
> - **Khác biệt đáng chú ý so với 3.x:** loại bỏ Undertow (chưa tương thích Servlet 6.1), loại bỏ các API bị deprecated từ 3.x, Jackson 3 thay Jackson 2, JSpecify thay JSR-305 cho null safety.
> - Kiểm tra phiên bản mới nhất tại [spring.io/blog](https://spring.io/blog) và [Spring Boot 4.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide).

## Spring Boot là gì?

**Spring Boot** là một framework mở rộng của Spring Framework, giúp việc tạo ứng dụng Java trở nên nhanh chóng và đơn giản hơn. Thay vì phải cấu hình thủ công hàng loạt file XML hay properties phức tạp, Spring Boot tự động cấu hình (auto-configuration) dựa trên những gì bạn đang dùng.

> **Phiên bản**: Tutorial này dùng **Spring Boot 4.x** (released November 2025), yêu cầu **Java 17+** (khuyến nghị Java 21 hoặc 25 LTS).

## Tại sao nên dùng Spring Boot?

### 1. Auto-Configuration

Spring Boot tự động cấu hình dựa trên classpath của bạn. Ví dụ, nếu bạn thêm dependency `spring-boot-starter-web`, Spring Boot tự động cấu hình embedded Tomcat, Spring MVC, và Jackson.

### 2. Starter Dependencies

Thay vì thêm 10-20 dependency riêng lẻ, bạn chỉ cần 1 starter:

```xml
<!-- Thay vì khai báo từng cái: spring-web, spring-mvc, jackson-databind, tomcat... -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 3. Embedded Server

Không cần deploy file WAR lên Tomcat bên ngoài. Spring Boot nhúng Tomcat/Jetty/Undertow ngay trong file JAR:

```bash
# Chỉ cần chạy
java -jar myapp.jar
```

### 4. Production-Ready Features

Spring Boot Actuator cung cấp health checks, metrics, monitoring sẵn sàng cho production.

## Tạo Project với Spring Initializr

Bước 1: Truy cập [https://start.spring.io](https://start.spring.io)

Bước 2: Cấu hình:
- **Project**: Maven
- **Language**: Java
- **Spring Boot**: 4.1.x
- **Group**: com.example
- **Artifact**: demo
- **Packaging**: Jar
- **Java**: 21 (hoặc 25)

Bước 3: Thêm dependencies:
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Spring Boot Actuator

Bước 4: Nhấn **Generate**, tải về và giải nén.

## Cấu trúc Project

```
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java      # Entry point
│   │   │       ├── controller/               # REST Controllers
│   │   │       ├── service/                  # Business Logic
│   │   │       ├── repository/               # Data Access
│   │   │       ├── model/                    # Entity/DTO
│   │   │       └── config/                   # Configuration
│   │   └── resources/
│   │       ├── application.properties        # Cấu hình
│   │       ├── static/                       # Static files (JS, CSS)
│   │       └── templates/                    # Templates (Thymeleaf)
│   └── test/
│       └── java/
│           └── com/example/demo/             # Tests
├── pom.xml                                   # Maven configuration
└── mvnw / mvnw.cmd                           # Maven Wrapper
```

## Code ví dụ: Ứng dụng đầu tiên

**Yêu cầu**: Java 17+ (Spring Boot 4.x)

### DemoApplication.java

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@RestController
class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Xin chào Spring Boot!";
    }
}
```

### Chạy ứng dụng

```bash
# Dùng Maven Wrapper
./mvnw spring-boot:run

# Hoặc build rồi chạy
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Truy cập `http://localhost:8080/hello` để thấy kết quả.

## Các annotation quan trọng

| Annotation | Ý nghĩa |
|-----------|---------|
| `@SpringBootApplication` | Kết hợp 3 annotation: `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan` |
| `@RestController` | Đánh dấu class xử lý HTTP request, trả về JSON |
| `@GetMapping` | Map HTTP GET request |
| `@Autowired` | Inject dependency |
| `@Component` | Đánh dấu class được Spring quản lý |
| `@Service` | Tương tự `@Component`, dùng cho business logic |
| `@Repository` | Tương tự `@Component`, dùng cho data access |

## Cấu hình với application.properties

```properties
# Server
server.port=8080

# Database (H2 in-memory cho dev)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update

# Logging
logging.level.com.example.demo=DEBUG
```

Hoặc dùng `application.yml` (được ưa chuộng hơn):

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:testdb
  jpa:
    hibernate:
      ddl-auto: update

logging:
  level:
    com.example.demo: DEBUG
```

## Spring Boot 4.x - Điểm mới so với 3.x

1. **Spring Framework 7**: Modularization, jar nhỏ hơn
2. **JSpecify Null Safety**: Full null-safety annotations
3. **Java 25 Support**: Hỗ trợ chính thức Java 25 LTS
4. **API Versioning**: Quản lý version API tích hợp sẵn
5. **Declarative HTTP Clients**: Gọi API khác dễ dàng hơn
6. **BeanRegistrar**: Cách mới để đăng ký bean

## Kiến trúc phân lớp (Layered Architecture)

```
┌─────────────────────────────────────┐
│  Controller Layer (REST API)        │  ← Xử lý HTTP
├─────────────────────────────────────┤
│  Service Layer (Business Logic)     │  ← Logic nghiệp vụ
├─────────────────────────────────────┤
│  Repository Layer (Data Access)     │  ← Truy vấn DB
├─────────────────────────────────────┤
│  Database (H2/MySQL/PostgreSQL)     │
└─────────────────────────────────────┘
```

## Best Practices

1. **Package theo feature, không theo layer** (dễ maintain hơn với project lớn)
2. **Dùng Constructor Injection** thay vì Field Injection
3. **Đặt DTO riêng** khỏi Entity để tránh expose data nhạy cảm
4. **Cấu hình tách biệt** theo môi trường (`application-dev.yml`, `application-prod.yml`)
5. **Dùng profiles** để quản lý môi trường khác nhau

## Tài liệu tham khảo

- [Spring Boot Official Docs](https://spring.io/projects/spring-boot)
- [Spring Initializr](https://start.spring.io)
- [Spring Boot 4.0 Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Release-Notes)
- [Baeldung Spring Boot Tutorials](https://www.baeldung.com/spring-boot)

---

**Tiếp theo**: [2. REST API với Spring Boot](../2_rest-api/)
