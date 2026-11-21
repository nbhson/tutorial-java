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

Hãy hình dung truyền thống là **nấu ăn từ trắng**: bạn phải chọn nồi, đun bếp, nêm nếm từng chút một. Spring Boot là **bộ nồi cơm điện kiểu Nhật** — nhấn một nút, mọi cấu hình mặc định hợp lý đã được set sẵn, bạn chỉ lo phần "nguyên liệu" của mình.

> **Phiên bản**: Tutorial này dùng **Spring Boot 4.x** (released November 2025), yêu cầu **Java 17+** (khuyến nghị Java 21 hoặc 25 LTS).

> **Tóm gọn 1 câu:** Spring Boot = Spring đã "lắp sẵn" — tự cấu hình theo những gì bạn dùng, để bạn lo nghiệp vụ thay vì lo dây điện.

## Tại sao nên dùng Spring Boot?

Mục này trả lời câu hỏi "vì sao chọn Spring Boot" qua 4 lợi ích — đọc để hiểu nó tiết kiệm cho bạn những gì.

### 1. Auto-Configuration

Spring Boot tự động cấu hình dựa trên classpath của bạn. Ví dụ, nếu bạn thêm dependency `spring-boot-starter-web`, Spring Boot tự động cấu hình embedded Tomcat, Spring MVC, và Jackson. Giống như khi bạn mua tủ lạnh mới, bếp tự nhận biết và sắp xếp phích cắm đúng chỗ — không cần bạn đọc hướng dẫn lắp đặt.

### 2. Starter Dependencies

Thay vì thêm 10-20 dependency riêng lẻ, bạn chỉ cần 1 starter — giống như mua **bộ dụng cụ trà** thay vì lần lượt mua ấm, chén, khay, xúc xích... rồi tự lo chúng có khớp nhau hay không:

> 📄 **Code ví dụ:** [`examples/01-2-starter-dependencies.xml`](examples/01-2-starter-dependencies.xml)

### 3. Embedded Server

Không cần deploy file WAR lên Tomcat bên ngoài. Spring Boot nhúng Tomcat/Jetty/Undertow ngay trong file JAR — như cách xe con **kèm sẵn động cơ** thay vì phải kéo đến trạm gắn máy riêng trước khi chạy:

> 📄 **Code ví dụ:** [`examples/02-3-embedded-server.sh`](examples/02-3-embedded-server.sh)

### 4. Production-Ready Features

Spring Boot Actuator cung cấp health checks, metrics, monitoring sẵn sàng cho production.

## Tạo Project với Spring Initializr

Mục này là "cây dạy bước đầu": bốn bước để sinh ra khung project Spring Boot đầu tiên mà chưa cần viết một dòng code nào.

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

Mục này cho bạn xem "mặt bằng căn hộ" của project vừa sinh ra — từng phòng (thư mục) dùng để làm gì, để sau này đặt code đúng chỗ.

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

Mục này là khoảnh khắc "nhấn công tắc đèn lần đầu": viết ứng dụng nhỏ nhất có thể và chạy nó.

**Yêu cầu**: Java 17+ (Spring Boot 4.x)

### DemoApplication.java

> 📄 **Code ví dụ:** [`examples/03-demoapplication-java.java`](examples/03-demoapplication-java.java)

### Chạy ứng dụng

> 📄 **Code ví dụ:** [`examples/04-chay-ung-dung.sh`](examples/04-chay-ung-dung.sh)

Truy cập `http://localhost:8080/hello` để thấy kết quả.

## Các annotation quan trọng

Mục này là "từ điển mini" của Spring: những dòng chữ bắt đầu bằng `@` mà bạn sẽ gặp khắp nơi trong code Spring Boot.

| Annotation | Ý nghĩa |
|-----------|---------|
| `@SpringBootApplication` | Kết hợp 3 annotation: `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan` |
| `@RestController` | Đánh dấu class xử lý HTTP request, trả về JSON |
| `@GetMapping` | Map HTTP GET request |
| `@Autowired` | Inject dependency |
| `@Component` | Đánh dấu class được Spring quản lý |
| `@Service` | Tương tự `@Component`, dùng cho business logic |
| `@Repository` | Tương tự `@Component`, dùng cho data access |

> **Cách đọc bảng này:** dòng đầu (`@SpringBootApplication`) là annotation "cửa ngõ" chỉ xuất hiện một lần trong class chính; các dòng còn lại là annotation "đời thường" bạn sẽ gặp hằng ngày — đọc cột Ý nghĩa và quay lại tra khi gặp trong code.

## Cấu hình với application.properties

Mục này nói về "bảng điều khiển" của ứng dụng: cổng chạy, kết nối database... đều chỉnh từ đây, không cần đụng code.

> 📄 **Code ví dụ:** [`examples/05-cau-hinh-voi-application-properties.properties`](examples/05-cau-hinh-voi-application-properties.properties)

Hoặc dùng `application.yml` (được ưa chuộng hơn):

> 📄 **Code ví dụ:** [`examples/06-cau-hinh-voi-application-properties.yaml`](examples/06-cau-hinh-voi-application-properties.yaml)

## Spring Boot 4.x - Điểm mới so với 3.x

Mục này tóm tắt sáu thay đổi đáng chú ý của thế hệ 4.x — cần biết nếu bạn đọc code mẫu 3.x trên mạng và thấy "hơi khác".

1. **Spring Framework 7**: Modularization, jar nhỏ hơn
2. **JSpecify Null Safety**: Full null-safety annotations
3. **Java 25 Support**: Hỗ trợ chính thức Java 25 LTS
4. **API Versioning**: Quản lý version API tích hợp sẵn
5. **Declarative HTTP Clients**: Gọi API khác dễ dàng hơn
6. **BeanRegistrar**: Cách mới để đăng ký bean

## Kiến trúc phân lớp (Layered Architecture)

Mục này vẽ "sơ đồ tầng nhà" của ứng dụng: request đi từ tầng nào xuống tầng nào để lấy được dữ liệu.

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

> **Tóm gọn 1 câu:** Request đi xuống từng tầng như khách đi thang bộ — Controller đón khách, Service xử lý việc, Repository ra kho lấy đồ.

## Best Practices

Mục này là "mẹo nội quy cư trú": năm thói quen giúp project Spring Boot của bạn sống khỏe khi lớn lên.

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
