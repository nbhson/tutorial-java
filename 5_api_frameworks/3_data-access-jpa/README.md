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

Mục này trả lời câu hỏi "tại sao cần JPA" trước khi code: làm sao để class Java và bảng SQL nói chuyện được với nhau.

**Spring Data JPA** là phần của Spring Data ecosystem, giúp đơn giản hóa việc truy cập database. Thay vì viết boilerplate code JDBC hay Hibernate XML mappings, JPA (Java Persistence API) cho phép bạn ánh xạ Java objects sang database tables một cách tự động (ORM - Object-Relational Mapping).

Hãy hình dung ORM như **phiên dịch viên song ngữ**: bên này nói tiếng Java (object), bên kia nói tiếng SQL (bảng) — bạn nói chuyện với phiên dịch viên bằng ngôn ngữ của mình, không cần học lại tiếng của đối phương.

> **Lưu ý phiên bản**: Hướng dẫn này dùng Spring Data JPA 2026.x, Spring Boot 4.x, Java 17+. Database: H2 (in-memory) cho dev.

> **Tóm gọn 1 câu:** Spring Data JPA là phiên dịch viên tự động giữa object Java và bảng SQL — bạn mô tả quan hệ, nó lo phần SQL.

## ORM là gì?

Mục này vẽ trực quan "bản đồ dịch thuật": object Java tương ứng với dòng bảng nào, field nào tương ứng với cột nào.

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

Mục này là bước "chuyển nhà": khai báo cho JPA biết database ở đâu, dùng driver gì, và nạp dependencies cần thiết vào pom.

### application.yml

> 📄 **Code ví dụ:** [`examples/01-application-yml.yaml`](examples/01-application-yml.yaml)

### pom.xml - Thêm dependencies

> 📄 **Code ví dụ:** [`examples/02-pom-xml-them-dependencies.xml`](examples/02-pom-xml-them-dependencies.xml)

## Entity Mapping

Mục này là "đăng ký hộ khẩu" cho class Java: bằng vài annotation, class của bạn trở thành một bảng chính thức trong database.

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

> **Cách đọc bảng này:** đọc theo trật tự từng dòng chính là trật tự bạn khai báo một Entity hoàn chỉnh — từ tên class, tên bảng, đến khóa chính và từng cột; `@Transient` là ngoại lệ đáng nhớ nhất (trường "không đăng ký hộ khẩu").

### Task Entity (Hoàn chỉnh)

> 📄 **Code ví dụ:** [`examples/03-task-entity-hoan-chinh.java`](examples/03-task-entity-hoan-chinh.java)

## Mối quan hệ (Relationships)

Mục này nói về "quan hệ gia đình" giữa các bảng: một người có nhiều việc, một việc gắn nhiều nhãn — JPA mô hình hóa chúng như các trường tham chiếu trong object.

### One-to-Many: User có nhiều Tasks

> 📄 **Code ví dụ:** [`examples/04-one-to-many-user-co-nhieu-tasks.java`](examples/04-one-to-many-user-co-nhieu-tasks.java)

> 📄 **Code ví dụ:** [`examples/05-one-to-many-user-co-nhieu-tasks.java`](examples/05-one-to-many-user-co-nhieu-tasks.java)

### Many-to-Many: Task có nhiều Tags

> 📄 **Code ví dụ:** [`examples/06-many-to-many-task-co-nhieu-tags.java`](examples/06-many-to-many-task-co-nhieu-tags.java)

> 📄 **Code ví dụ:** [`examples/07-many-to-many-task-co-nhieu-tags.java`](examples/07-many-to-many-task-co-nhieu-tags.java)

## Repository Interfaces

Mục này là "phép thuật" hấp dẫn nhất của Spring Data: chỉ viết đúng tên method, framework tự sinh SQL cho bạn.

Spring Data JPA tự động tạo implementation dựa trên method name. Giống như gọi nhân viên chuyển tiếp bằng đúng cú pháp quy định — "cho tôi tờ hóa đơn số 7" — bạn nói đúng ngữ pháp, hệ thống hiểu và đi lấy.

### TaskRepository.java

> 📄 **Code ví dụ:** [`examples/08-taskrepository-java.java`](examples/08-taskrepository-java.java)

## Phân Trang và Sắp xếp

Mục này giải quyết bài toán "một triệu dòng dữ liệu thì hiển thị sao": trả về từng trang nhỏ, sắp theo trật tự chỉ định.

### PaginatedTaskController.java

> 📄 **Code ví dụ:** [`examples/09-paginatedtaskcontroller-java.java`](examples/09-paginatedtaskcontroller-java.java)

## Custom Repository Implementation

Mục này nói về "khu vực tự do": khi method-name magic không đủ sức với truy vấn phức tạp, bạn tự viết SQL nhưng vẫn giữ được cấu trúc repository.

### TaskRepositoryCustom.java

> 📄 **Code ví dụ:** [`examples/10-taskrepositorycustom-java.java`](examples/10-taskrepositorycustom-java.java)

### TaskRepositoryCustomImpl.java

> 📄 **Code ví dụ:** [`examples/11-taskrepositorycustomimpl-java.java`](examples/11-taskrepositorycustomimpl-java.java)

### Kết hợp trong TaskRepository

> 📄 **Code ví dụ:** [`examples/12-ket-hop-trong-taskrepository.java`](examples/12-ket-hop-trong-taskrepository.java)

## Auditing (Tự động điền thông tin)

Mục này nói về "dấu thời gian tự động": ai tạo, khi nào tạo, ai sửa, khi nào sửa — Spring điền hộ, bạn không phải viết tay từng entity.

### Enable Auditing

> 📄 **Code ví dụ:** [`examples/13-enable-auditing.java`](examples/13-enable-auditing.java)

### Sử dụng @CreatedDate, @LastModifiedDate

> 📄 **Code ví dụ:** [`examples/14-su-dung-createddate-lastmodifieddate.java`](examples/14-su-dung-createddate-lastmodifieddate.java)

## Best Practices

Mục này là "nội quy sử dụng JPA": sáu thói quen giúp tránh những vụ sập kinh điển như N+1 query hay lộ dữ liệu nhạy cảm.

1. **Dùng DTOs**: Không return Entity trực tiếp từ API (security risk)
2. **Fetch Strategy**: Dùng `LAZY` cho relationships, `EAGER` chỉ khi cần thiết
3. **Batch Operations**: Dùng `saveAll()` cho batch insert thay vì vòng lặp
4. **Transactions**: Đánh dấu `@Transactional` cho tất cả methods modify data
5. **Indexes**: Tạo index cho các cột thường xuyên query
6. **N+1 Problem**: Tránh bằng `JOIN FETCH` trong JPQL hoặc `@EntityGraph`

> **Tóm gọn 1 câu:** JPA là phiên dịch viên tiện nhưng phải biết cách dùng — LAZY, DTO, batch và JOIN FETCH là bốn "mẹo giữ chớp điện" cho ứng dụng lớn.

## Tài liệu tham khảo

- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Baeldung JPA/Hibernate](https://www.baeldung.com/learn-jpa-hibernate/)
- [JPA Entity Mappings](https://www.baeldung.com/jpa-entity-relationships)

---

**Tiếp theo**: [4. Validation và Security](../4_validation-security/)
