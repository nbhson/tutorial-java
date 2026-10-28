# JAVA Tutorial — Lộ trình học Java toàn diện

> Chào bạn! Đây là kho tài liệu học **Java** từ cơ bản đến nâng cao, được viết bằng **tiếng Việt** với code mẫu rõ ràng, bài tập thực hành và minh họa trực quan. Dù bạn mới bắt đầu hay đã có kinh nghiệm, bạn sẽ tìm được lộ trình phù hợp để nâng cấp kỹ năng Java của mình.

## ⚡ Công nghệ phiên bản hiện tại (cập nhật 28/10/2026)

| Công nghệ | Phiên bản | Ghi chú |
|-----------|-----------|---------|
| Java | **25 LTS** (09/2025) | LTS mới nhất, baseline Java 17 — xem [jdk.java.net](https://jdk.java.net/) |
| Spring Boot | **4.1.2** (stablest) / 4.0.9 (LTS) | 4.0.0 GA 20/11/2025; thế hệ mới, Framework 7, Jakarta EE 11 |
| Spring Security | **7.1.x** | Đi kèm Boot 4.1 |
| Spring Cloud | **2025.1.x / 2026.0-RC** | Tùy phiên bản Boot |
| JUnit | **6** (Framework 7) | Dọn API; kiến thức JUnit 5 vẫn tương thích |
| Jakarta EE | **11** | Namespace `jakarta.*` (không còn `javax.*`) |
| Servlet | **6.1** | Tomcat 11 / Jetty 12.1+ |
| Maven / Gradle | Cập nhật mới nhất | Build tool hiện đại |

---

## 📚 Cấu trúc toàn khóa học

Khoá học chia thành **7 phần chính**, đọc theo thứ tự từ trên xuống dưới:

```
0_setup          ← Cài đặt môi trường (JDK 25, IDE, Maven/Gradle)
       ↓
1_basic-part-1   ← Cơ bản: JDK/JRE/JVM, biến, kiểu dữ liệu, toán tử, control statements
       ↓
2_basic-part-2   ← Nâng cao cơ bản: exception, file I/O, collections, đa luồng, networking
       ↓
3_oop            ← Lập trình hướng đối tượng: class/object, kế thừa, đa hình, trừu tượng, đóng gói
       ↓
4_solid          ← Nguyên tắc thiết kế SOLID + project thực hành Pizza App
       ↓
5_api_frameworks ← Thực chiến: Spring Boot 4.x, REST API, JPA, Validation/Security, Testing, Microservices
       ↓
6_advanced       ← Nâng cao: Design Patterns, Stream API & Lambda, Record/Sealed, JVM & Garbage Collection
```

---

### Phần 0 — [0_setup](./0_setup/README.md) ✅

Cài đặt JDK 25 LTS, IntelliJ IDEA / VS Code, Maven / Gradle. Chạy chương trình Java đầu tiên (`Hello World`), sử dụng `jshell`.

**Nội dung chính:**
- Tải và cài JDK 25 từ [Adoptium](https://adoptium.net/) hoặc Oracle
- Cấu hình biến môi trường `JAVA_HOME`
- Cài đặt plugin Java cho IntelliJ IDEA / VS Code
- Khởi tạo project Maven (`mvn archetype:generate`) và Gradle
- Biên dịch và chạy: `javac` + `java`, `./mvnw spring-boot:run`

---

### Phần 1 — [1_basic-part-1](./1_basic-part-1/) ✅

Nền tảng ngôn ngữ Java: JDK/JRE/JVM, biến và kiểu dữ liệu, toán tử, câu lệnh điều khiển, các lớp có sẵn.

**Các chủ đề:**

| Bài | Nội dung |
|-----|---------|
| `0_jdk_jre_jvm` | JVM, JRE, JDK — ba tầng nền tảng của hệ sinh thái Java |
| `1_basic/2_variable` | Khai báo và sử dụng biến, quy tắc đặt tên |
| `1_basic/4_operators_casting` | Toán tử, ép kiểu (casting) |
| `2_control-statements/1_if-else` | Câu lệnh rẽ nhánh if/else và switch (Java 21+ pattern matching) |
| `2_control-statements/2_switch-case` | Switch expression, multi-label case |
| `2_control-statements/3_for-loop` | Vòng lặp for, for-each |
| `2_control-statements/4_while-loop` | Vòng lặp while |
| `2_control-statements/5_do-while-loop` | Vòng lặp do-while |
| `2_control-statements/6_break-continue` | break, continue, labeled break |
| `3_built-in-classes/1_number` | Wrapper classes (Number, Integer, Double...) |
| `3_built-in-classes/2_boolean` | Boolean wrapper |
| `3_built-in-classes/3_array` | Mảng, Arrays class |
| `1_basic/6_unicode-system` | Hệ thống Unicode trong Java |
| `1_basic/8_date` | Lớp Date (java.util.Date, SimpleDateFormat) |

---

### Phần 2 — [2_basic-part-2](./2_basic-part-2/) ✅

Các chủ đề nâng cao nhưng vẫn thuộc nền tảng: xử lý ngoại lệ, thao tác file, collections, đa luồng, mạng.

**Các chủ đề:**

| Bài | Nội dung |
|-----|---------|
| `1_error-exceptions/1_exceptions-introduce` | Exception là gì, checked/unchecked/error, try/catch |
| `1_error-exceptions/2_try-catch-block` | try, catch, multiple catch, catch gộp `IOException\|Exception` |
| `1_error-exceptions/3_try-with-resource` | Try-with-resources, AutoCloseable, nhiều tài nguyên |
| `1_error-exceptions/4_multiple-catch-blocks` | Nhiều catch, thứ tự catch |
| `1_error-exceptions/5_nested-try-block` | Khối try lồng nhau |
| `1_error-exceptions/6_finally-block` | finally — luôn thực thi |
| `1_error-exceptions/7_throw-error` | throw chủ động ném exception |
| `1_error-exceptions/8_built-in-exceptions` | Exception có sẵn phổ biến trong Java |
| `1_error-exceptions/9_custom-exceptions` | Tự tạo exception (checked vs unchecked) |
| `2_file-handling/1_files` | Lớp File, các constructor và phương thức |
| `2_file-handling/2_create-files` | Tạo file, thư mục |
| `2_file-handling/3_write-to-files` | Ghi file với FileWriter, BufferedWriter, PrintWriter |
| `2_file-handling/6_directory-operators` | Thao tác thư mục: tạo, liệt kê, xóa |
| `2_file-handling/7_files-io` | Stream I/O: FileInputStream, FileOutputStream, BufferedReader |
| `3_collections` | List, Set, Map, Queue — interface và implementation |
| `4_multithreading` | Thread, Runnable, join, sleep, daemon thread |
| `5_synchronization` | synchronized, ReentrantLock, wait/notify |
| `6_networking` | Socket, ServerSocket, URL, HttpURLConnection |
| `7_miscellaneous` | Các chủ đề bổ sung: var (Java 10+), sealed classes preview |
| `8_debugger` | Sử dụng debugger trong IntelliJ IDEA |

---

### Phần 3 — [3_oop](./3_oop/) ✅

Lập trình hướng đối tượng — bốn trụ cột của OOP trong Java:

| Bài | Nội dung |
|-----|---------|
| `1-class-and-object` | Tạo và sử dụng đối tượng, tham chiếu đối tượng |
| `2-inheritance` | Kế thừa: extends, super, đa kế thừa lớp |
| `3-polymorphism` | Đa hình: overriding, overloading |
| `4-abstraction` | Abstract class và interface |
| `5-encapsulation` | Đóng gói: private fields, getter/setter |

**Thư mục `reference/`:** các khái niệm bổ sung OOP — constructor, access modifier, overloading/overriding, `this`, `final`, static binding, dynamic binding, setter/getter, interface.

---

### Phần 4 — [4_solid](./4_solid/README.md) ✅

Nguyên tắc thiết kế SOLID (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion) kèm project **Pizza Ordering App** thực hành.

| Bài | Nội dung |
|-----|---------|
| `1-s` | Single Responsibility Principle |
| `2-o` | Open/Closed Principle |
| `3-l` | Liskov Substitution Principle |
| `4-i` | Interface Segregation Principle |
| `5-d` | Dependency Inversion Principle |

---

### Phần 5 — [5_api_frameworks](./5_api_frameworks/README.md) 🆕

Thực chiến — xây dựng ứng dụng Spring Boot 4.x thực tế:

| Bài | Nội dung |
|-----|---------|
| `1_spring-boot-intro` | Giới thiệu Spring Boot 4, auto-configuration, starter POM |
| `2_rest-api` | RESTful API với `@RestController`, `@GetMapping`, CRUD |
| `3_data-access-jpa` | Spring Data JPA, Entity, Repository, ORM |
| `4_validation-security` | Bean Validation (`@Valid`), Spring Security (JWT, BCrypt) |
| `5_testing` | JUnit 6, `@SpringBootTest`, MockMvc, Mockito |
| `6_microservices` | Microservices, Spring Cloud, Gateway, Config Server |

> **Lưu ý Spring Boot 4.x:** baseline là **Framework 7 + Jakarta EE 11** (namespace `jakarta.*`). Cần Java 17+ (khuyến nghị 25 LTS). Thay đổi lớn: modular hóa Spring Boot (jar nhỏ hơn, tập trung hơn), JSpecify thay JSR-305, Jackson 3 (group `tools.jackson`), Servlet 6.1, loại bỏ Undertow. Kiểm tra chi tiết trong **ghi chú cập nhật** ở đầu mỗi bài trong phần này.

---

### Phần 6 — [6_advanced](./6_advanced/README.md) 🆕

Các chủ đề nâng cao dành cho lập trình viên muốn đào sâu:

| Bài | Nội dung |
|-----|---------|
| `1_design-patterns` | Các pattern thường dùng: Singleton, Factory, Observer, Strategy... |
| `2_stream-api-lambda` | Stream API, Lambda expression, functional interface |
| `3_optional-null-safety` | Optional<T> — tránh NullPointerException |
| `4_record-sealed` | Record (Java 14+), Sealed class (Java 17+) |
| `5_performance-gc` | JVM internals, garbage collectors, memory model, performance tuning |

---

## 🗺️ Đọc theo lộ trình học đề xuất

```
Bước 1:  0_setup          → Cài đặt JDK, IDE
Bước 2:  1_basic-part-1   → Cú pháp cơ bản
Bước 3:  2_basic-part-2   → Exception, file, collections, threads
Bước 4:  3_oop            → OOP — hiểu cách Java tổ chức code
Bước 5:  4_solid          → Thiết kế tốt hơn
Bước 6:  5_api_frameworks → Xây ứng dụng thực tế với Spring Boot 4.x
Bước 7:  6_advanced       → Những thứ chỉ senior mới hỏi
```

Mỗi bài đều có **README.md** (lý thuyết) và **code mẫu** chạy trực tiếp được. Đọc lý thuyết → chạy code → thử thay đổi → so sánh kết quả.

---

## 🔗 Nguồn tham khảo chính thức

- [Oracle Java Documentation](https://docs.oracle.com/en/java/)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/index.html)
- [Spring Framework 7.0 Release Notes](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-7.0-Release-Notes)
- [Spring Boot 4.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide)
- [Dev.java — The Destination for Java Developers](https://dev.java/)
- [Baeldung — Java Tutorials](https://www.baeldung.com/)
- [Jakarta EE 11 Specification](https://jakarta.ee/specifications/)
- [JUnit 6 — JUnit User Guide](https://junit.org/junit6/)
