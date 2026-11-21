# REST API với Spring Boot

> ### 🆕 Cập nhật Spring Boot 4.x (kiểm tra ngày 28/10/2026)
>
> Những điểm liên quan trực tiếp đến việc xây dựng REST API trong chương này:
>
> - **API Versioning "first-class"** (mới trong Framework 7 / Boot 4): Spring MVC hỗ trợ version API chính thức qua annotation `@RequestMapping` thuộc tính `version` / `ApiVersion` và version resolver. Với REST API thực tế, bạn có thể đánh version theo header hoặc media type thay vì tự viết interceptor như cách làm thủ công trước đây.
> - **HTTP Service Clients** (mới): khai báo interface kiểu declarative client (tương tự Feign) với `@ImportHttpServices` — thay cho nhiều template code thủ công.
> - **Jackson 3 thay Jackson 2:** group `com.fasterxml.jackson` → `tools.jackson` (riêng `jackson-annotations` giữ nguyên `com.fasterxml.jackson.core`). Nếu code của bạn tự viết serializer/deserializer, lưu ý: `JsonObjectSerializer` → `ObjectValueSerializer`, `@JsonComponent` → `@JacksonComponent`, `@JsonMixin` → `@JacksonMixin`. Các property cấu hình `spring.jackson.read.*` / `spring.jackson.write.*` chuyển thành `spring.jackson.json.read.*` / `spring.jackson.json.write.*`.
> - **Baseline web:** Jakarta Servlet 6.1 — chạy trên **Tomcat 11+** hoặc Jetty 12.1+ (Undertow đã bị loại bỏ khỏi Boot 4).
> - **Null safety:** Spring dùng JSpecify thay cho annotation JSR-305; IDE sẽ cảnh báo null-safety chuẩn hóa hơn với API mới.
> - **Lợi ích khác của thế hệ này:** built-in resilience (retry, concurrency throttling) có sẵn cho các endpoint, giúp giảm nhu cầu thêm thư viện ngoài.
> - Các khái niệm cốt lõi trong chương (HTTP methods, CRUD, DTO, status codes) **không thay đổi** — chỉ nền tảng và một số API được nâng cấp.

## REST API là gì?

Mục này trả lời câu hỏi nền tảng trước khi viết bất cứ dòng code nào: REST là gì, và API của chúng ta sẽ "trông" như thế nào trên HTTP.

**REST** (Representational State Transfer) là một kiểu kiến trúc để thiết kế API. RESTful API sử dụng các HTTP methods chuẩn như GET, POST, PUT, DELETE để thực hiện các thao tác CRUD (Create, Read, Update, Delete) trên resources.

Hãy hình dung REST API như **quầy thu ngân nhà hàng**: bạn gọi món (gửi request), quầy trả hóa đơn/món ăn (response), và mỗi loại yêu cầu (xem menu, đặt món, đổi món, hủy món) có cách gọi riêng chuẩn hóa — ai cũng hiểu dù chưa từng vào nhà hàng này.

> **Phiên bản**: Spring Boot 4.x, Java 17+. Chúng ta sẽ xây dựng REST API cho một ứng dụng Quản lý Công việc (Task Management).

> **Tóm gọn 1 câu:** REST là "khuân vác dữ liệu bằng HTTP chuẩn" — mỗi hành động CRUD có một method và một đường dẫn đúng phép.

## Các HTTP Methods và ý nghĩa

Mục này là "bảng cửu chương" của API: method nào dùng cho việc gì và trả status nào, để bạn không tạo task bằng GET kiểu "rừng lên".

| Method | CRUD | Mục đích | HTTP Status |
|--------|------|----------|-------------|
| GET | Read | Lấy dữ liệu | 200 OK |
| POST | Create | Tạo mới | 201 Created |
| PUT | Update | Cập nhật toàn bộ | 200/204 OK |
| PATCH | Update | Cập nhật một phần | 200/204 OK |
| DELETE | Delete | Xóa | 204 No Content |

> **Cách đọc bảng này:** mỗi dòng là một method, đọc theo trật tự GET → POST → PUT → PATCH → DELETE chính là trật tự bạn gặp trong thực tế; so cột "CRUD" với "HTTP Status" để nhớ kết quả mong đợi của từng thao tác.

## Tạo Model/Entity

Mục này bắt đầu dựng "khung nhà" của API: lớp Task đại diện cho một công việc trong hệ thống.

### Task.java

> 📄 **Code ví dụ:** [`examples/01-task-java.java`](examples/01-task-java.java)

## Tạo DTO (Data Transfer Object)

Mục này nói về lớp "quần áo của dữ liệu ra vào": Entity là nội y của database, còn DTO là bộ đồ công sở API mặc ra ngoài — không mặc nội y ra đường.

DTO giúp tách biệt data mà API trả về với Entity trong database.

### TaskDTO.java

> 📄 **Code ví dụ:** [`examples/02-taskdto-java.java`](examples/02-taskdto-java.java)

## Tạo Repository

Mục này giới thiệu "người giao hàng giữa code và database": bạn chỉ khai báo cần gì, Spring tự sinh mã truy vấn.

### TaskRepository.java

> 📄 **Code ví dụ:** [`examples/03-taskrepository-java.java`](examples/03-taskrepository-java.java)

## Tạo Service Layer

Mục này nói về "phòng xử lý đơn": mọi nghiệp vụ (kiểm tra, biến đổi, điều phối) nằm ở tầng này, controller không tự làm việc nặng.

### TaskService.java

> 📄 **Code ví dụ:** [`examples/04-taskservice-java.java`](examples/04-taskservice-java.java)

## Tạo REST Controller

Mục này là nơi "gắn biển chỉ đường" cho API: ánh xạ mỗi URL + method HTTP tới một hàm Java cụ thể.

### TaskController.java

> 📄 **Code ví dụ:** [`examples/05-taskcontroller-java.java`](examples/05-taskcontroller-java.java)

## Xử lý Exception

Mục này nói về "bộ phận chăm sóc khách hàng": khi có lỗi, ai trả lời người dùng, trả cái gì, ở đâu — tập trung một chỗ thay vì mỗi handler tự xử.

### GlobalExceptionHandler.java

> 📄 **Code ví dụ:** [`examples/06-globalexceptionhandler-java.java`](examples/06-globalexceptionhandler-java.java)

## Test REST API với curl

Mục này là phần "đi thử đường": dùng curl gọi từng endpoint vừa viết để xác nhận API chạy đúng như thiết kế.

> 📄 **Code ví dụ:** [`examples/07-test-rest-api-voi-curl.sh`](examples/07-test-rest-api-voi-curl.sh)

## Các Annotation quan trọng

Mục này là "từ điển mini" của REST API Spring: các annotation điều khiển URL, body, validation và CORS.

| Annotation | Mô tả |
|------------|-------|
| `@RestController` | Combines `@Controller` và `@ResponseBody`, tự động serialize response thành JSON |
| `@RequestMapping` | Map URL base cho tất cả methods trong controller |
| `@GetMapping` | HTTP GET method |
| `@PostMapping` | HTTP POST method |
| `@PutMapping` | HTTP PUT method |
| `@PatchMapping` | HTTP PATCH method |
| `@DeleteMapping` | HTTP DELETE method |
| `@PathVariable` | Lấy giá trị từ URL path (e.g., `/tasks/{id}`) |
| `@RequestBody` | Deserialize JSON body thành Java object |
| `@Valid` | Trigger Bean Validation trước khi xử lý |
| `@CrossOrigin` | Cho phép CORS requests |

> **Cách đọc bảng này:** nhóm 5 dòng giữa (Get/Put/Patch/DeleteMapping) là các biến thể của cùng một ý "map HTTP method" — hiểu một cái là hiểu cả nhóm; hai dòng cuối (`@Valid`, `@CrossOrigin`) là "phụ kiện" gắn thêm khi cần.

## Best Practices cho REST API

Mục này là "nội quy giao thông" cho API: bảy thói quen giúp API của bạn dễ dùng, an toàn và sống sót khi lên production.

1. **Dùng plural nouns cho resource names**: `/tasks` thay vì `/task`
2. **Dùng HTTP status codes đúng**: 200, 201, 400, 404, 500...
3. **Luôn validate input**: Dùng Bean Validation (`@Valid`)
4. **Dùng DTOs**: Không expose trực tiếp JPA entities ra API
5. **Version API**: `/api/v1/tasks`, `/api/v2/tasks`
6. **Paginate large results**: `/tasks?page=0&size=20`
7. **Document API**: Dùng OpenAPI/Swagger

> **Tóm gọn 1 câu:** REST API tử tế là API mà người mới đọc URL cũng đoán được hành vi — danh từ số nhiều, status code đúng, input đã validate, kết quả có phân trang.

## Tài liệu tham khảo

- [Building a RESTful Web Service](https://spring.io/guides/tutorials/rest/)
- [REST API Design Best Practices](https://restfulapi.net/)
- [Baeldung REST with Spring](https://www.baeldung.com/rest-with-spring-series)

---

**Tiếp theo**: [3. Data Access với Spring Data JPA](../3_data-access-jpa/)
