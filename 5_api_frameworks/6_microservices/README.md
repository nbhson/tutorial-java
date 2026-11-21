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

Mục này trả lời câu hỏi nền tảng trước khi đụng Spring Cloud: microservices là gì, và đổi lại sự linh hoạt ta phải trả giá gì.

**Microservices** là kiến trúc chia ứng dụng thành các services nhỏ, độc lập, mỗi service đảm nhiệm một business capability riêng. Điều này giúp dễ mở rộng, deploy độc lập, và giảm rủi ro khi có lỗi.

Hãy hình dung monolith như một **siêu thị lớn duy nhất trong thành phố** — mọi thứ ở một chỗ, dễ quản lý nhưng một sự cố hỏng thang máy là cả siêu thị tê liệt. Microservices là **các cửa hàng chuyên doanh riêng lẻ**: tiệm bánh sập không ảnh hưởng tiệm sách, nhưng giờ bạn phải lo tìm đường tới từng tiệm — đó chính là chi phí Spring Cloud lo thay bạn.

### So sánh Monolith vs Microservices

Sơ đồ dưới đây đặt hai mô hình cạnh nhau để bạn nhìn rõ sự khác biệt về "mặt bằng":

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

> **Cách đọc bảng này:** đọc theo từng "khía cạnh" dọc cột trái, so sánh ngang hai cột Monolith/Microservices — bạn sẽ thấy Microservices thắng ở deploy/scale nhưng thua ở Complexity: đó là cái giá cần cân nhắc trước khi chọn.

## Spring Cloud là gì?

Mục này giới thiệu "bộ công cụ quản lý chung cư nhiều tòa": các pattern chung của hệ phân tán mà Spring Cloud đóng gói sẵn cho bạn.

**Spring Cloud** cung cấp các công cụ để xây dựng các patterns phổ biến trong hệ thống distributed:

- **Service Discovery**: Tự động tìm services
- **API Gateway**: Điểm vào duy nhất cho clients
- **Load Balancing**: Phân phối tải
- **Circuit Breaker**: Xử lý lỗi khi service down

> **Phiên bản**: Spring Cloud 2026.x (tương thích Spring Boot 4.x), Java 17+

> **Tóm gọn 1 câu:** Spring Cloud là "bộ tiện ích chung cho cả khu dân cư service" — tìm nhau, dẫn đường, chịu lỗi, cấu hình tập trung.

## Thành phần chính

Mục này lần lượt giới thiệu 6 mảnh ghép của một hệ microservices chuẩn — mỗi mảnh giải một bài toán riêng, đọc theo thứ tự là ghép được sơ đồ kiến trúc ở cuối bài.

### 1. Service Discovery - Eureka Server

Eureka cho phép các services đăng ký và tìm thấy nhau mà không cần hardcode IP/port — giống như **sổ điện thoại của tòa nhà**: tenant mới vào là tự ghi tên, ai cần gặp thì tra sổ chứ không cần nhớ số phòng từng người.

#### Eureka Server

> 📄 **Code ví dụ:** [`examples/01-eureka-server.xml`](examples/01-eureka-server.xml)

> 📄 **Code ví dụ:** [`examples/02-eureka-server.java`](examples/02-eureka-server.java)

> 📄 **Code ví dụ:** [`examples/03-eureka-server.yaml`](examples/03-eureka-server.yaml)

#### Eureka Client (Service)

> 📄 **Code ví dụ:** [`examples/04-eureka-client-service.java`](examples/04-eureka-client-service.java)

> 📄 **Code ví dụ:** [`examples/05-eureka-client-service.yaml`](examples/05-eureka-client-service.yaml)

### 2. API Gateway

API Gateway là điểm vào duy nhất cho client, route requests đến services tương ứng — hãy hình dung nó như **lễ tân sảnh chính**: khách chỉ cần đến một cửa, lễ tân biết chính xác hướng dẫn lên phòng nào.

> 📄 **Code ví dụ:** [`examples/06-2-api-gateway.xml`](examples/06-2-api-gateway.xml)

> 📄 **Code ví dụ:** [`examples/07-2-api-gateway.yaml`](examples/07-2-api-gateway.yaml)

> 📄 **Code ví dụ:** [`examples/08-2-api-gateway.java`](examples/08-2-api-gateway.java)

### 3. Service-to-Service Communication

Mục này nói về "cách các tiệm gọi nhau": chọn công cụ gọi HTTP nào cho hợp thời, và cách cân bằng tải giữa các bản sao của một service.

#### RestTemplate (Legacy) vs WebClient vs RestClient

> 📄 **Code ví dụ:** [`examples/09-resttemplate-legacy-vs-webclient-vs-restclient.java`](examples/09-resttemplate-legacy-vs-webclient-vs-restclient.java)

#### Load Balanced với @LoadBalanced

> 📄 **Code ví dụ:** [`examples/10-load-balanced-voi-loadbalanced.java`](examples/10-load-balanced-voi-loadbalanced.java)

### 4. Circuit Breaker - Resilience4j

Mục này nói về "cầu chì điện" của hệ phân tán: khi một service chết, đừng để cả chuỗi bị cháy theo.

**Circuit Breaker** ngăn cascading failures khi một service down. Hãy hình dung cầu dao điện trong nhà: khi dòng quá tải, nó ngắt sớm để bảo vệ toàn bộ mạch — rồi thỉnh thoảng "thử lại" xem điện đã ổn chưa. Spring Boot 4.x tích hợp resilience trực tiếp (không cần Resilience4j dependency riêng).

> 📄 **Code ví dụ:** [`examples/11-4-circuit-breaker-resilience4j.java`](examples/11-4-circuit-breaker-resilience4j.java)

> 📄 **Code ví dụ:** [`examples/12-4-circuit-breaker-resilience4j.yaml`](examples/12-4-circuit-breaker-resilience4j.yaml)

### 5. Centralized Configuration - Spring Cloud Config

Mục này giải quyết bài toán "nơi nào lưu cấu hình": một nguồn duy nhất, mọi service kéo về — thay vì mỗi service giữ một bản cấu hình riêng lẻ dễ lệch nhau.

Sơ đồ dưới đây mô tả dòng chảy cấu hình: Git lưu file gốc, Config Server phân phát, các service kéo về dùng.

```
┌──────────────┐     ┌──────────────────┐     ┌───────────────┐
│ Config Server│←────│  Git Repository  │     │ Config Client │
│  (Port 8888) │     │  (config files)  │     │  (Services)   │
└──────────────┘     └──────────────────┘     └───────────────┘
```

> 📄 **Code ví dụ:** [`examples/13-5-centralized-configuration-spring-cloud-config.java`](examples/13-5-centralized-configuration-spring-cloud-config.java)

> 📄 **Code ví dụ:** [`examples/14-5-centralized-configuration-spring-cloud-config.yaml`](examples/14-5-centralized-configuration-spring-cloud-config.yaml)

### 6. Distributed Tracing - Micrometer Tracing

> 📄 **Code ví dụ:** [`examples/15-6-distributed-tracing-micrometer-tracing.yaml`](examples/15-6-distributed-tracing-micrometer-tracing.yaml)

## Kiến trúc hoàn chỉnh

Mục này ghép cả 6 mảnh phía trên vào một sơ đồ duy nhất — nhìn một lần là thấy vị trí từng thành phần trong bức tranh tổng thể.

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

> 📄 **Code ví dụ:** [`examples/16-vi-du-goi-service-qua-eureka.java`](examples/16-vi-du-goi-service-qua-eureka.java)

## Deployment với Docker

### Dockerfile

> 📄 **Code ví dụ:** [`examples/17-dockerfile.dockerfile`](examples/17-dockerfile.dockerfile)

### docker-compose.yml

> 📄 **Code ví dụ:** [`examples/18-docker-compose-yml.yaml`](examples/18-docker-compose-yml.yaml)

## Best Practices

Mục này là "nội quy vận hành" của hệ microservices: bảy nguyên tắc mà nếu bỏ qua một, bạn sẽ trả giá lúc 3 giờ sáng.

1. **Database per service**: Mỗi service có database riêng
2. **API Gateway**: Điểm vào duy nhất, xử lý cross-cutting concerns
3. **Circuit Breaker**: Bắt buộc cho service-to-service calls
4. **Centralized Config**: Không hardcode config trong service
5. **Distributed Tracing**: Trace requests qua nhiều services
6. **Health Checks**: Expose `/actuator/health` cho monitoring
7. **Event-Driven**: Dùng Kafka/RabbitMQ cho async communication

> **Tóm gọn 1 câu:** Microservices là "các cửa hàng chuyên doanh tự lo đời sống của mình" — Spring Cloud là hệ thống điện nước, đường phố và sổ đăng ký giúp chúng sống chung hòa bình.

## Tài liệu tham khảo

- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
- [Spring Cloud Netflix (Eureka)](https://spring.io/projects/spring-cloud-netflix)
- [Baeldung Spring Cloud](https://www.baeldung.com/spring-cloud)

---

**Quay lại**: [5. Testing trong Spring Boot](../5_testing/) | **Về trang chủ**: [API Frameworks](../README.md)
