# Microservices với Spring Cloud

> ### 🆕 Cập nhật Spring Boot 4.x (kiểm tra ngày 28/10/2026)
>
> Những điểm cần biết khi dựng microservices trên thế hệ Boot 4:
>
> - **Spring Cloud phiên bản tương thích:** với Boot 4.0 dùng release train **Spring Cloud 2025.1.x** (bản vá mới nhất 2025.1.4); với Boot 4.1 dùng **Spring Cloud 2026.0** (2026.0.0-RC1 đang RC tính đến 27/10/2026). Luôn tra [spring.io/projects/spring-cloud](https://spring.io/projects/spring-cloud) để ghép đúng cặp phiên bản.
> - **Spring Boot 4.1 bổ sung Spring gRPC hỗ trợ chính thức** (server/client gRPC, có thể chạy standalone trên Netty hoặc qua HTTP/2 + Servlet) — một lựa chọn giao tiếp giữa các service bên cạnh REST.
> - **HTTP Service Clients** (Framework 7): thay cho việc tự viết client code, bạn khai báo interface declarative — rất hợp cho giao tiếp service-to-service trong kiến trúc microservices.
> - **Resilience built-in:** Framework 7 có sẵn retry, concurrency throttling... nên một phần nhu cầu trước đây của Resilience4j được phục vụ ngay trong nền tảng.
> - **Modular hóa Boot 4:** jar lớn tách thành module nhỏ — khởi động nhanh hơn, ít dependency thừa hơn cho từng service; nhưng nếu dự án không dùng starter POM chuẩn, hãy rà lại danh sách dependency.
> - **Undertow đã bị loại bỏ** khỏi Boot 4 (chưa tương thích Servlet 6.1) — nếu hạ tầng của bạn từng dùng Undertow, chuyển sang Tomcat 11+ / Jetty 12.1+.
> - Các khái niệm cốt lõi trong chương (tách service, service discovery, gateway, config server) **không thay đổi** — chỉ nền tảng được nâng cấp.

## Microservices là gì?

**Microservices** là kiến trúc chia ứng dụng thành các services nhỏ, độc lập, mỗi service đảm nhiệm một business capability riêng. Điều này giúp dễ mở rộng, deploy độc lập, và giảm rủi ro khi có lỗi.

### So sánh Monolith vs Microservices

```
┌─────────────────────────────────┐      ┌──────────┐  ┌──────────┐  ┌──────────┐
│         Monolith                │      │  User    │  │  Order   │  │ Product  │
│  ┌────────┐ ┌────────┐         │      │ Service  │  │ Service  │  │ Service  │
│  │  User  │ │ Order  │         │      └────┬─────┘  └────┬─────┘  └────┬─────┘
│  │ Module │ │ Module │  VS     │           │             │             │
│  ├────────┼─┼────────┤         │      ┌────┴─────────────┴─────────────┴───┐
│  │Product │ │ Email  │         │      │         API Gateway                │
│  └────────┴─┴────────┘         │      └────────────────────────────────────┘
│      [Single Database]         │      [Multiple Databases]
└────────────────────────────────┘
```

| Khía cạnh | Monolith | Microservices |
|-----------|----------|---------------|
| Deploy | Toàn bộ app | Từng service riêng |
| Scale | Toàn bộ | Từng service |
| Technology | 1 stack | Nhiều stack |
| Database | Shared | Per service |
| Complexity | Thấp ban đầu | Cao hơn |

## Spring Cloud là gì?

**Spring Cloud** cung cấp các công cụ để xây dựng các patterns phổ biến trong hệ thống distributed:

- **Service Discovery**: Tự động tìm services
- **API Gateway**: Điểm vào duy nhất cho clients
- **Load Balancing**: Phân phối tải
- **Circuit Breaker**: Xử lý lỗi khi service down

> **Phiên bản**: Spring Cloud 2026.x (tương thích Spring Boot 4.x), Java 17+

## Thành phần chính

### 1. Service Discovery - Eureka Server

Eureka cho phép các services đăng ký và tìm thấy nhau mà không cần hardcode IP/port.

#### Eureka Server

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

```yaml
# application.yml cho Eureka Server
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false   # Server không tự đăng ký
    fetch-registry: false          # Server không cần fetch registry
```

#### Eureka Client (Service)

```java
@SpringBootApplication
@EnableDiscoveryClient  // Đăng ký với Eureka
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

```yaml
spring:
  application:
    name: user-service   # Tên service (dùng để gọi nhau)

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

### 2. API Gateway

API Gateway là điểm vào duy nhất cho client, route requests đến services tương ứng.

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

```yaml
# application.yml cho API Gateway
server:
  port: 8080

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://USER-SERVICE         # lb = load balanced
          predicates:
            - Path=/api/users/**
        - id: task-service
          uri: lb://TASK-SERVICE
          predicates:
            - Path=/api/tasks/**
      default-filters:
        - DedupeResponseHeader=Access-Control-Allow-Credentials Access-Control-Allow-Origin
```

```java
// Programmatic configuration (alternative)
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r
                .path("/api/users/**")
                .uri("lb://USER-SERVICE"))
            .route("task-service", r -> r
                .path("/api/tasks/**")
                .uri("lb://TASK-SERVICE"))
            .build();
    }
}
```

### 3. Service-to-Service Communication

#### RestTemplate (Legacy) vs WebClient vs RestClient

```java
// ============ RestClient (Mới, Spring 6.1+) - Khuyến nghị ============
@Service
public class UserServiceClient {

    private final RestClient restClient;

    public UserServiceClient(RestClient.Builder builder) {
        this.restClient = builder
            .baseUrl("http://user-service")  // Tên từ Eureka, không cần IP
            .build();
    }

    public UserDTO getUser(Long id) {
        return restClient
            .get()
            .uri("/api/users/{id}", id)
            .retrieve()
            .body(UserDTO.class);
    }
}
```

#### Load Balanced với @LoadBalanced

```java
@Configuration
public class RestClientConfig {

    @Bean
    @LoadBalanced  // Tự động resolve service name qua Eureka
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}
```

### 4. Circuit Breaker - Resilience4j

**Circuit Breaker** ngăn cascading failures khi một service down. Spring Boot 4.x tích hợp resilience trực tiếp (không cần Resilience4j dependency riêng).

```java
@Service
public class TaskService {

    private final UserServiceClient userServiceClient;

    public TaskService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "getDefaultUser")
    @Retry(name = "userService")
    @TimeLimiter(name = "userService")
    public UserDTO getUserWithResilience(Long userId) {
        return userServiceClient.getUser(userId);
    }

    // Fallback method - được gọi khi circuit breaker mở
    private UserDTO fallbackGetUser(Long userId, Throwable t) {
        // Trả về default user thay vì fail
        return new UserDTO(userId, "Unknown User", "unknown@example.com");
    }
}
```

```yaml
resilience4j:
  circuitbreaker:
    instances:
      userService:
        sliding-window-size: 10
        failure-rate-threshold: 50
        wait-duration-in-open-state: 10s
        permitted-number-of-calls-in-half-open-state: 3
```

### 5. Centralized Configuration - Spring Cloud Config

```
┌──────────────┐     ┌──────────────────┐     ┌───────────────┐
│ Config Server│←────│  Git Repository  │     │ Config Client │
│  (Port 8888) │     │  (config files)  │     │  (Services)   │
└──────────────┘     └──────────────────┘     └───────────────┘
```

```java
// Config Server
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

```yaml
# application.yml cho Config Server
server:
  port: 8888
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/yourorg/config-repo
          default-label: main
```

### 6. Distributed Tracing - Micrometer Tracing

```yaml
# Tracing với Micrometer + Zipkin
management:
  tracing:
    sampling:
      probability: 1.0  # Trace 100% requests (dev)
  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans
```

## Kiến trúc hoàn chỉnh

```
                    ┌─────────────┐
                    │   Client    │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │ API Gateway │  (Port 8080)
                    │  Port 8080  │
                    └──────┬──────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
  ┌─────▼─────┐     ┌─────▼─────┐     ┌─────▼─────┐
  │   User    │     │   Task    │     │  Notify   │
  │  Service  │     │  Service  │     │  Service  │
  │Port: 8081 │     │Port: 8082 │     │Port: 8083 │
  └─────┬─────┘     └─────┬─────┘     └─────┬─────┘
        │                  │                  │
  ┌─────┴──────────────────┴──────────────────┴─────┐
  │           Service Discovery (Eureka)             │
  │               Port: 8761                         │
  └──────────────────────────────────────────────────┘
```

## Ví dụ: Gọi Service qua Eureka

```java
// Trong Task Service, gọi User Service
@Service
public class TaskService {

    private final RestClient restClient;

    public TaskService(@LoadBalanced RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public TaskResponse createTask(TaskRequest request) {
        // Gọi User Service để validate user tồn tại
        UserDTO user = restClient
            .get()
            .uri("http://user-service/api/users/{id}", request.userId())
            .retrieve()
            .body(UserDTO.class);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // Tạo task...
        Task task = new Task(request.title(), request.description());
        return taskRepository.save(task);
    }
}
```

## Deployment với Docker

### Dockerfile

```dockerfile
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### docker-compose.yml

```yaml
version: '3.8'
services:
  eureka-server:
    build: ./eureka-server
    ports:
      - "8761:8761"

  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - eureka-server
    environment:
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/

  user-service:
    build: ./user-service
    depends_on:
      - eureka-server
    environment:
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/

  task-service:
    build: ./task-service
    ports:
      - "8082:8082"
    depends_on:
      - eureka-server
    environment:
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
```

## Best Practices

1. **Database per service**: Mỗi service có database riêng
2. **API Gateway**: Điểm vào duy nhất, xử lý cross-cutting concerns
3. **Circuit Breaker**: Bắt buộc cho service-to-service calls
4. **Centralized Config**: Không hardcode config trong service
5. **Distributed Tracing**: Trace requests qua nhiều services
6. **Health Checks**: Expose `/actuator/health` cho monitoring
7. **Event-Driven**: Dùng Kafka/RabbitMQ cho async communication

## Tài liệu tham khảo

- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
- [Spring Cloud Netflix (Eureka)](https://spring.io/projects/spring-cloud-netflix)
- [Baeldung Spring Cloud](https://www.baeldung.com/spring-cloud)

---

**Quay lại**: [5. Testing trong Spring Boot](../5_testing/) | **Về trang chủ**: [API Frameworks](../README.md)
