# Validation và Security với Spring Boot

> ### 🆕 Cập nhật Spring Boot 4.x (kiểm tra ngày 28/10/2026)
>
> Những thay đổi liên quan đến validation và security trong thế hệ Boot 4:
>
> - **Bean Validation 3.1** (Jakarta EE 11) với **Hibernate Validator 9.x** là baseline mới — các annotation như `@NotNull`, `@Size`, `@Valid` vẫn dùng nguyên trạng, nhưng namespace là `jakarta.validation.*`.
> - **Spring Security 7.0** GA (11/2025) đi kèm Boot 4; bản ổn định mới nhất tính đến 10/2026 là **7.1.x** (7.2.0-RC1 đang trong giai đoạn RC). Các thay đổi chính:
>   - Loại bỏ hoàn toàn API/method bị deprecated từ Security 5.x/6.x — cấu hình `WebSecurityConfigurerAdapter` đã bị xóa từ trước; duy nhất dùng kiểu lambda DSL `SecurityFilterChain` như trong chương này.
>   - Chuẩn hóa null safety bằng JSpecify.
> - **Namespace:** toàn bộ annotation security/validation dùng `jakarta.*` — `jakarta.annotation.PostConstruct`, `jakarta.validation.constraints.*`... Spring Framework 7 **không còn hỗ trợ** `javax.annotation`/`javax.inject`.
> - **Jackson 3** (`tools.jackson`) cho serializer/deserializer trong tầng web/security — nếu bạn tùy biến JSON output, nhớ đổi group/package theo hướng dẫn của chapter 2_rest-api.
> - Các khái niệm (validation qua annotation, SecurityFilterChain, BCrypt, JWT) **không thay đổi** — đây vẫn là cách làm chuẩn trên Boot 4.

## Phần 1: Bean Validation

### Validation là gì?

Validation là quá trình kiểm tra dữ liệu đầu vào có hợp lệ hay không trước khi xử lý. Spring Boot tích hợp **Jakarta Bean Validation 3.x** (JSR 380) để validate dữ liệu một cách khai báo (declarative) qua annotations.

Hãy hình dung validation như **cửa kiểm soát hành lý ở sân bay**: vali (dữ liệu) phải qua bàn soi trước khi lên máy bay (xử lý nghiệp vụ) — đồ cấm (email sai, tuổi âm) bị chặn ngay cửa, không để bay được nữa mới phát hiện.

> **Phiên bản**: Jakarta Bean Validation 3.1 (đi kèm Spring Boot 4.x), yêu cầu Java 17+.

> **Tóm gọn 1 câu:** Validation là "chốt cửa từ bên ngoài": dữ liệu bẩn bị chặn trước khi vào logic nghiệp vụ, và bạn khai báo luật bằng annotation thay vì if-else.

### Thêm Dependency

> 📄 **Code ví dụ:** [`examples/01-them-dependency.xml`](examples/01-them-dependency.xml)

### Các Annotation Validation phổ biến

| Annotation | Mô tả | Ví dụ |
|------------|-------|-------|
| `@NotNull` | Không được null | `@NotNull` |
| `@NotBlank` | Không null, không rỗng, có ký tự | `@NotBlank` (String only) |
| `@NotEmpty` | Không null, không rỗng | `@NotEmpty` (String, Collection) |
| `@Size(min, max)` | Giới hạn độ dài | `@Size(min=3, max=50)` |
| `@Min` / `@Max` | Giới hạn số | `@Min(1) @Max(100)` |
| `@Email` | Định dạng email | `@Email` |
| `@Pattern(regex)` | Khớp regex | `@Pattern("[a-z]+")` |
| `@Positive` / `@PositiveOrZero` | Số dương | `@Positive` |
| `@Past` / `@Future` | Thời gian quá khứ/tương lai | `@Past` |
| `@AssertTrue` | Phải là true | `@AssertTrue` |

> **Cách đọc bảng này:** đây là "menu món ăn" của validation — đọc cột "Mô tả" để chọn món, cột "Ví dụ" là cách gọi đúng cú pháp; ba dòng đầu (`@NotNull`/`@NotBlank`/`@NotEmpty`) dễ nhầm nhất, phân biệt bằng chỗ áp dụng (mọi kiểu / chỉ String / String + Collection).

### Ví dụ: DTO với Validation

> 📄 **Code ví dụ:** [`examples/02-vi-du-dto-voi-validation.java`](examples/02-vi-du-dto-voi-validation.java)

### Áp dụng Validation trong Controller

> 📄 **Code ví dụ:** [`examples/03-ap-dung-validation-trong-controller.java`](examples/03-ap-dung-validation-trong-controller.java)

### Custom Validation

> 📄 **Code ví dụ:** [`examples/04-custom-validation.java`](examples/04-custom-validation.java)

> 📄 **Code ví dụ:** [`examples/05-custom-validation.java`](examples/05-custom-validation.java)

## Phần 2: Spring Security và JWT

Mục này chuyển sang "hệ thống an ninh tòa nhà": ai được vào (xác thực), vào rồi được lên tầng nào (phân quyền), và vé điện tử JWT giữ cửa ra vào.

### Spring Security là gì?

**Spring Security** là framework bảo mật cho ứng dụng Spring, cung cấp:
- **Authentication** (Xác thực): Xác minh danh tính người dùng
- **Authorization** (Phân quyền): Kiểm soát quyền truy cập
- **Protection** chống lại các attacks: CSRF, Session Fixation, Clickjacking

> **Phiên bản**: Spring Security 7.x (đi kèm Spring Boot 4.x)

### Cơ chế JWT (JSON Web Token)

Sơ đồ dưới đây mô tả cả "hai hồi kịch" của JWT: hồi một là login lấy vé, hồi hai là xưng danh bằng vé không cần tra database lại.

```
┌─────────────┐      ┌──────────────┐      ┌─────────────┐
│   Client    │      │   Server     │      │  Database   │
└──────┬──────┘      └──────┬───────┘      └──────┬──────┘
       │                     │                     │
       │ 1. Login            │                     │
       │────────────────────→│  2. Verify          │
       │   (email, password) │────────────────────→│
       │                     │                     │
       │                     │  3. User data       │
       │                     │←────────────────────│
       │                     │                     │
       │                     │  4. Generate JWT    │
       │  5. Return JWT      │                     │
       │←────────────────────│                     │
       │                     │                     │
       │ 6. API Request      │                     │
       │   + Bearer Token    │                     │
       │────────────────────→│  7. Verify JWT      │
       │                     │    (no DB call!)    │
       │  8. Response        │                     │
       │←────────────────────│                     │
```

### Thêm Dependencies

> 📄 **Code ví dụ:** [`examples/06-them-dependencies.xml`](examples/06-them-dependencies.xml)

### Cấu hình Security Filter Chain

> 📄 **Code ví dụ:** [`examples/07-cau-hinh-security-filter-chain.java`](examples/07-cau-hinh-security-filter-chain.java)

### Tạo JWT Service

> 📄 **Code ví dụ:** [`examples/08-tao-jwt-service.java`](examples/08-tao-jwt-service.java)

### JWT Authentication Filter

> 📄 **Code ví dụ:** [`examples/09-jwt-authentication-filter.java`](examples/09-jwt-authentication-filter.java)

### Auth Controller (Login/Register)

> 📄 **Code ví dụ:** [`examples/10-auth-controller-login-register.java`](examples/10-auth-controller-login-register.java)

### application.yml cho JWT

> 📄 **Code ví dụ:** [`examples/11-application-yml-cho-jwt.yaml`](examples/11-application-yml-cho-jwt.yaml)

## Best Practices

Mục này là "nội quy an ninh": vài thói quen sống còn để validation và security không thành hình thức.

### Validation
1. **Validate ở cả 2 tầng**: Client-side (UX) và Server-side (security)
2. **Dùng DTO riêng cho request/response**: Không validate trên Entity
3. **Trả về error messages rõ ràng** cho người dùng
4. **Custom validator** cho business rules phức tạp

### Security
1. **Luôn hash password** (BCrypt), không bao giờ lưu plain text
2. **JWT secret đủ dài** (ít nhất 256 bits cho HS256)
3. **HTTPS everywhere** trong production
4. **Token expiration ngắn** (15 phút - 1 giờ) + Refresh Token
5. **Rate limiting** để chống brute-force attacks
6. **Validate role/permission** ở cả Controller và Service layer

> **Tóm gọn 1 câu:** Security là "két sắt nhiều lớp": hash mật khẩu, vé JWT ngắn hạn, HTTPS, giới hạn tốc độ — thiếu một lớp là cả tòa nhà rủi ro.

## Tài liệu tham khảo

- [Jakarta Bean Validation](https://jakarta.ee/specifications/bean-validation/3.1/)
- [Spring Security Reference](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io/)
- [Baeldung Spring Security with JWT](https://www.baeldung.com/spring-security-jwt)

---

**Tiếp theo**: [5. Testing trong Spring Boot](../5_testing/)
