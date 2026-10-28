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

> **Phiên bản**: Jakarta Bean Validation 3.1 (đi kèm Spring Boot 4.x), yêu cầu Java 17+.

### Thêm Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

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

### Ví dụ: DTO với Validation

```java
package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record RegisterRequest(
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 3, max = 50, message = "Tên phải từ 3 đến 50 ký tự")
    String name,

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    String email,

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu tối thiểu 8 ký tự")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
        message = "Mật khẩu phải chứa chữ thường, chữ hoa và số"
    )
    String password,

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải trong quá khứ")
    LocalDate birthDate,

    @Min(value = 18, message = "Tuổi tối thiểu 18")
    @Max(value = 100, message = "Tuổi tối đa 100")
    Integer age,

    @Pattern(
        regexp = "^[0-9]{10,11}$",
        message = "Số điện thoại phải có 10-11 chữ số"
    )
    String phone
) {}
```

### Áp dụng Validation trong Controller

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {
        // Nếu validation fail, MethodArgumentNotValidException sẽ được throw
        // và GlobalExceptionHandler sẽ xử lý
        return ResponseEntity.ok("Đăng ký thành công: " + request.email());
    }
}
```

### Custom Validation

```java
// Tạo annotation tùy chỉnh
package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Mật khẩu không đủ mạnh";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

```java
// Implement Validator
package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        // Mật khẩu phải có ít nhất 8 ký tự, 1 chữ hoa, 1 chữ thường, 1 số, 1 ký tự đặc biệt
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}
```

## Phần 2: Spring Security và JWT

### Spring Security là gì?

**Spring Security** là framework bảo mật cho ứng dụng Spring, cung cấp:
- **Authentication** (Xác thực): Xác minh danh tính người dùng
- **Authorization** (Phân quyền): Kiểm soát quyền truy cập
- **Protection** chống lại các attacks: CSRF, Session Fixation, Clickjacking

> **Phiên bản**: Spring Security 7.x (đi kèm Spring Boot 4.x)

### Cơ chế JWT (JSON Web Token)

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

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.6</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### Cấu hình Security Filter Chain

```java
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF (chỉ dùng với REST API + JWT)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()      // Public endpoints
                .requestMatchers("/h2-console/**").permitAll()    // H2 Console
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Chỉ ADMIN
                .anyRequest().authenticated()                     // Còn lại phải đăng nhập
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Không dùng session
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### Tạo JWT Service

```java
package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")           // Tự cấu hình trong application.yml
    private String secretKey;

    @Value("${jwt.expiration:86400000}")  // 24 hours in ms
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // ============ Generate Token ============
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
            .claims(claims)
            .subject(email)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(getSigningKey())
            .compact();
    }

    // ============ Extract Data from Token ============
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    // ============ Validate Token ============
    public boolean isTokenValid(String token, String email) {
        final String tokenEmail = extractEmail(token);
        return tokenEmail.equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
```

### JWT Authentication Filter

```java
package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Không có token hoặc sai format → pass through
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);  // Bỏ "Bearer "
            final String email = jwtService.extractEmail(jwt);
            final String role = jwtService.extractRole(jwt);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (!jwtService.isTokenValid(jwt, email)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                List<SimpleGrantedAuthority> authorities = 
                    List.of(new SimpleGrantedAuthority("ROLE_" + role));

                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            // Token invalid - tiếp tục filter chain (không set authentication)
            logger.error("JWT validation error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
```

### Auth Controller (Login/Register)

```java
package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // Trong thực tế: query user từ database và so sánh password
        // Ví dụ đơn giản:
        if ("user@example.com".equals(email) && "Password123".equals(password)) {
            String token = jwtService.generateToken(email, "USER");
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        // Hash password trước khi lưu
        String hashedPassword = passwordEncoder.encode(request.password());
        // Lưu user vào database...
        return ResponseEntity.ok("Đăng ký thành công");
    }
}
```

### application.yml cho JWT

```yaml
jwt:
  secret: my-very-long-secret-key-at-least-32-characters-for-hs256
  expiration: 86400000  # 24 hours in milliseconds
```

## Best Practices

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

## Tài liệu tham khảo

- [Jakarta Bean Validation](https://jakarta.ee/specifications/bean-validation/3.1/)
- [Spring Security Reference](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io/)
- [Baeldung Spring Security with JWT](https://www.baeldung.com/spring-security-jwt)

---

**Tiếp theo**: [5. Testing trong Spring Boot](../5_testing/)
