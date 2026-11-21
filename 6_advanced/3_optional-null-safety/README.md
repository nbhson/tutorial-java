# Optional và Null Safety

## Vấn đề NullPointerException

Mục này nói về "kẻ thù số một" của lập trình viên Java: lỗi nổ lúc runtime vì một biến trống mà bạn quên kiểm tra.

**NullPointerException (NPE)** là một trong những lỗi phổ biến nhất trong Java. Tony Hoare, người phát minh null reference, gọi đây là ["billion-dollar mistake"](https://en.wikipedia.org/wiki/Tony_Hoare#Apologies_and_retractions). Hãy hình dung `null` như một **phích cắm rút ra mà dây vẫn còn đó** — bạn cứ tưởng điện còn, cắm thiết bị vào là nổ cầu chì; NPE chính là "cầu chì nổ" của chương trình.

### Vấn đề với null

Mục này cho bạn xem mấy vụ nổ kinh điển do quên kiểm null — để hiểu vì sao Optional ra đời.

> 📄 **Code ví dụ:** [`examples/01-van-e-voi-null.java`](examples/01-van-e-voi-null.java)

## Optional<T> là gì?

Mục này giới thiệu "hộp đựng giá trị có thể trống": thay vì giả định luôn có giá trị, Optional ép bạn mở hộp kiểm tra trước.

**Optional<T>** là một container có thể chứa hoặc không chứa một giá trị. Nó được giới thiệu từ Java 8 để giải quyết vấn đề null checks. Ví von: Optional giống **hộp pizza trống/không trống** — bạn được bắt buộc mở hộp nhìn xem có bánh không trước khi cắn, thay vì cắn mù quáng rồi trúng "không có bánh".

> **Yêu cầu**: Java 8+ (nên dùng Java 17+ với các cải tiến mới)

> **Tóm gọn 1 câu:** Optional là "hộp có thể trống" — type system nhắc bạn kiểm tra hộp trước khi dùng, thay vì để NPE nổ lúc chạy.

### Tạo Optional

> 📄 **Code ví dụ:** [`examples/02-tao-optional.java`](examples/02-tao-optional.java)

### Các Methods cơ bản

> 📄 **Code ví dụ:** [`examples/03-cac-methods-co-ban.java`](examples/03-cac-methods-co-ban.java)

## Optional Chaining

Mục này nói về "nối chuỗi hộp": khi một hộp chứa hộp khác, Optional cho bạn nối thao tác liền mạch thay vì mở từng lớp bằng if.

### Nối Optional

> 📄 **Code ví dụ:** [`examples/04-noi-optional.java`](examples/04-noi-optional.java)

### Optional trong Streams

> 📄 **Code ví dụ:** [`examples/05-optional-trong-streams.java`](examples/05-optional-trong-streams.java)

## Ví dụ thực tế

Mục này đặt Optional vào bối cảnh quen thuộc nhất: repository trả về "có thể không tìm thấy" bản ghi.

### Repository với Optional

> 📄 **Code ví dụ:** [`examples/06-repository-voi-optional.java`](examples/06-repository-voi-optional.java)

## Best Practices

Mục này là "nên và không nên" khi dùng Optional — bên nào cũng có ví dụ code để soi.

### ✅ Nên làm

> 📄 **Code ví dụ:** [`examples/07-nen-lam.java`](examples/07-nen-lam.java)

### ❌ Không nên làm

> 📄 **Code ví dụ:** [`examples/08-khong-nen-lam.java`](examples/08-khong-nen-lam.java)

## So sánh với các kỹ thuật khác

| Kỹ thuật | Ưu điểm | Nhược điểm |
|----------|---------|-----------|
| `null` check | Nhanh, đơn giản | Dễ quên, verbose |
| `Optional` | Type-safe, chainable | Overhead nhỏ |
| `@NonNull` annotation | Compile-time warning | Cần IDE/library support |
| Objects.requireNonNull() | Fail-fast | Chỉ xử lý parameter |

> **Cách đọc bảng này:** bốn kỹ thuật không loại trừ nhau mà dùng cho mục đích khác nhau — Optional cho "kết quả có thể trống", còn `@NonNull`/`requireNonNull` cho "tham số bắt buộc"; đọc cột Nhược điểm để biết giới hạn từng cái.

## Tiện ích khác

Mục này bổ sung vài "dụng cụ phụ trợ" trong cùng họ null-safety mà bạn sẽ gặp trong code chuẩn.

> 📄 **Code ví dụ:** [`examples/09-tien-ich-khac.java`](examples/09-tien-ich-khac.java)

> **Tóm gọn 1 câu:** Optional là "hộp ép bạn kiểm tra trước khi dùng" — dùng cho kết quả trả về có thể trống, đừng lạm dụng cho tham số hay field.

## Tài liệu tham khảo

- [Optional Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Optional.html)
- [Baeldung Java Optional](https://www.baeldung.com/java-optional)
- [Effective Java - Item 55](https://www.oreilly.com/library/view/effective-java-3rd/9780134686097/)

---

**Tiếp theo**: [4. Record và Sealed Classes](../4_record-sealed/)
