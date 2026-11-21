# Testing trong Spring Boot

> ### 🆕 Cập nhật Spring Boot 4.x (kiểm tra ngày 28/10/2026)
>
> Những thay đổi quan trọng về testing trong thế hệ Boot 4 / Framework 7:
>
> - **JUnit 6 là baseline mới** của Spring Framework 7 (trước đây là JUnit 5). JUnit 6 chủ yếu là bản "làm sạch": dọn các API deprecated, nâng yêu cầu nền tảng — kiến thức JUnit 5 trong chương này **vẫn áp dụng được** vì các annotation (`@Test`, `@BeforeEach`, `@SpringBootTest`...) giữ nguyên.
> - **Hạ tầng test được modularize:** cùng với việc Boot 4 tách nhỏ codebase, hạ tầng test cũng chia thành nhiều module. Nếu dự án không dùng starter POM chuẩn, bạn có thể phải thêm dependency test cụ thể cho từng công nghệ (ví dụ test cho Jackson/TestRestTemplate giờ nằm trong module riêng).
> - **Mockito 5.x / AssertJ** vẫn là bộ đôi mặc định trong `spring-boot-starter-test` — chương này dùng đúng bộ đó.
> - **Null safety JSpecify:** Spring API giờ khai báo nullable/non-null bằng JSpecify — khi viết test, IDE sẽ cảnh báo sớm hơn các giá trị `null` bất hợp lệ.
> - **Mock servlet:** `MockHttpServletRequest`/`MockHttpServletResponse` được cập nhật để bám sát hành vi Servlet 6.1 (đặc biệt với header/name `null`) — liên quan đến `@WebMvcTest` và MockMvc.
> - Lưu ý khi cập nhật từ 3.x: trước khi nâng cấp, hãy chạy hết test trên **Boot 3.5.x mới nhất** và dọn các API deprecated, vì Boot 4 đã loại bỏ chúng.

## Giới thiệu

Mục này nói về vì sao test không phải "việc thêm sau" mà là cơ chế an toàn của cả project: bạn sẽ biết Spring Boot trang bị sẵn những gì.

Testing là một phần quan trọng trong phát triển phần mềm. Spring Boot cung cấp hệ sinh thái testing mạnh mẽ với JUnit 5, Mockito, và nhiều utility classes giúp viết test dễ dàng.

Hãy hình dung test như **bảng kiểm tra trước khi cất cánh của phi công**: mỗi lần sửa code là một lần "cất cánh" — không chạy qua bảng kiểm, bạn đang bay bằng niềm tin.

> **Phiên bản**: JUnit 5.13.4 (July 2025), Mockito 5.x, Spring Boot 4.x, Java 17+

## Các loại Test trong Spring Boot

Mục này phân loại "thang đo" của test: từ nhanh-nhỏ-cô lập tới chậm-lớn-thật, để bạn biết loại nào dùng cho tình huống nào.

```
┌────────────────────────────────────────────────┐
│         Unit Test (Nhanh, cô lập)              │
│    - Test 1 class, mock dependencies           │
├────────────────────────────────────────────────┤
│      Integration Test (Trung bình)             │
│    - Test nhiều layer, dùng H2/Testcontainers  │
├────────────────────────────────────────────────┤
│       End-to-End Test (Chậm, đầy đủ)           │
│    - Test toàn bộ ứng dụng với real DB         │
└────────────────────────────────────────────────┘
```

| Loại Test | Phạm vi | Tốc độ | Công cụ |
|-----------|---------|--------|---------|
| Unit Test | 1 class | ⚡ Nhanh | JUnit 5 + Mockito |
| Integration Test | Service + Repository | 🚗 Trung bình | @DataJpaTest, H2 |
| Web Layer Test | Controller | 🚗 Trung bình | @WebMvcTest, MockMvc |
| Full App Test | Toàn bộ | 🐢 Chậm | @SpringBootTest |

> **Cách đọc bảng này:** đọc từ trên xuống theo trật tự phạm vi tăng dần — càng xuống dưới càng "thật" nhưng càng chậm; khi test chậm khiến bạn ngại chạy, hãy đẩy thử nghiệm lên các dòng trên.

## JUnit 5 cơ bản

Mục này là "bảng chữ cái" của testing: cấu trúc một test và các hàm khẳng định (assert) bạn sẽ dùng hằng ngày.

### Cấu trúc cơ bản

> 📄 **Code ví dụ:** [`examples/01-cau-truc-co-ban.java`](examples/01-cau-truc-co-ban.java)

### Các Assertions phổ biến

> 📄 **Code ví dụ:** [`examples/02-cac-assertions-pho-bien.java`](examples/02-cac-assertions-pho-bien.java)

## Mockito - Mocking Framework

Mục này nói về nghệ thuật "diễn viên đóng thế": thay dependencies thật bằng giả để test chỉ kiểm đúng một thứ.

### Mockito là gì?

**Mockito** giúp tạo mock objects (đối tượng giả) thay thế các dependencies thật, giúp test cô lập (isolated). Ví von dễ hiểu: thay vì mời cả dàn nhạc thật để tập vai hát chính, bạn dùng bản nhạc thu sẵn — muốn nó phát đoạn nào, nó phát đúng đoạn đó.

### Ví dụ: Mock Repository trong Service Test

> 📄 **Code ví dụ:** [`examples/03-vi-du-mock-repository-trong-service-test.java`](examples/03-vi-du-mock-repository-trong-service-test.java)

### Các Mockito Methods quan trọng

> 📄 **Code ví dụ:** [`examples/04-cac-mockito-methods-quan-trong.java`](examples/04-cac-mockito-methods-quan-trong.java)

## Integration Testing với Spring Boot

Mục này là phần "cho các diễn viên thật lên sân khấu": test nhiều tầng cùng nhau với đúng cấu hình Spring, thay vì mock tất cả.

### @SpringBootTest - Test toàn bộ ứng dụng

> 📄 **Code ví dụ:** [`examples/05-springboottest-test-toan-bo-ung-dung.java`](examples/05-springboottest-test-toan-bo-ung-dung.java)

### @WebMvcTest - Test Controller Layer

> 📄 **Code ví dụ:** [`examples/06-webmvctest-test-controller-layer.java`](examples/06-webmvctest-test-controller-layer.java)

### @DataJpaTest - Test Repository Layer

> 📄 **Code ví dụ:** [`examples/07-datajpatest-test-repository-layer.java`](examples/07-datajpatest-test-repository-layer.java)

## Test Pyramid và Coverage

Mục này trả lời câu hỏi "nên đầu tư test loại nào bao nhiêu": kim tự tháp dưới đây là phân bổ đúc kết của cả ngành.

```
        /\
       /  \     E2E Tests (ít)
      /----\
     /      \   Integration Tests (vừa)
    /--------\
   /          \  Unit Tests (nhiều nhất)
  /____________\
```

**Quy tắc chung**: 70% Unit Tests, 20% Integration Tests, 10% E2E Tests.

> **Tóm gọn 1 câu:** Test càng ở đáy kim tự tháp càng rẻ và nhanh — đầu tư đông đảo vào unit test, giữ số ít cho E2E.

## Chạy Tests với Maven

Mục này nói về cách "lên sân khấu" mọi test từ dòng lệnh — chỗ CI/CD sẽ gọi khi mỗi lần bạn push code.

> 📄 **Code ví dụ:** [`examples/08-chay-tests-voi-maven.sh`](examples/08-chay-tests-voi-maven.sh)

## Best Practices

Mục này là "bảy bài học xương máu" khi viết test: mỗi gạch đầu dòng là một lỗi phổ biến mà người mới hay mắc.

1. **AAA Pattern**: Arrange - Act - Assert rõ ràng
2. **Một test một mục đích**: Không test nhiều thứ trong 1 test method
3. **Tên test rõ ràng**: `shouldXxxWhenYyy` hoặc tiếng Việt có @DisplayName
4. **Unit test không phụ thuộc**: Mock tất cả dependencies bên ngoài
5. **Dùng AssertJ**: Fluent assertions dễ đọc hơn JUnit assertions
6. **Test cả happy path và error path**
7. **Dùng @ActiveProfiles("test")** với database riêng cho test

## Tài liệu tham khảo

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://site.mockito.org/)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)
- [Baeldung Spring Boot Testing](https://www.baeldung.com/spring-boot-testing)

---

**Tiếp theo**: [6. Microservices với Spring Cloud](../6_microservices/)
