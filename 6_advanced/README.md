# Java Nâng Cao - Advanced Java

## Giới thiệu tổng quan

Phần nâng cao này dành cho những bạn đã nắm vững Java cơ bản và muốn nâng cao kỹ năng để trở thành lập trình viên Java chuyên nghiệp. Chúng ta sẽ đi sâu vào các tính năng hiện đại của Java 17+ và 21+, các design patterns phổ biến, và cách tối ưu hiệu suất ứng dụng.

Nếu các phần trước là học **đi bộ vững**, thì phần này là học **chạy, bơi và leo núi**: cùng ngôn ngữ Java, nhưng ở tầm cao hơn — thiết kế đẹp, xử lý dữ liệu thanh lịch, và hiểu cả "cỗ máy" JVM bên dưới.

> **Tóm gọn 1 câu:** Phần nâng cao biến bạn từ "viết được Java" thành "hiểu Java" — patterns, Stream, Optional, Record và cả cỗ máy JVM bên dưới.

## Nội dung chính

Mục này là mục lục 5 trạm dừng của phần nâng cao — đọc lướt để chọn điểm xuất phát phù hợp với mình.

### 1. [Design Patterns](./1_design-patterns/)
Các mẫu thiết kế phổ biến trong Java: Singleton, Factory, Builder, Observer, Strategy. Hiểu cách áp dụng và khi nào nên sử dụng từng pattern.

### 2. [Stream API và Lambda Expressions](./2_stream-api-lambda/)
Khai thác sức mạnh của Functional Programming trong Java: Stream API, Lambda expressions, Functional Interfaces, và các thao tác với collections.

### 3. [Optional và Null Safety](./3_optional-null-safety/)
Xử lý null an toàn với Optional<T>, tránh NullPointerException, và các best practices để viết code null-safe.

### 4. [Record và Sealed Classes](./4_record-sealed/)
Các tính năng hiện đại của Java 16-25: Record (data class), Sealed Classes (giới hạn kế thừa), và Pattern Matching.

### 5. [Performance và Garbage Collection](./5_performance-gc/)
Hiểu sâu về JVM, Garbage Collection, memory management, và các kỹ thuật tối ưu hiệu suất ứng dụng Java.

## Yêu cầu

- **Java**: 17 trở lên (Khuyến nghị Java 21 hoặc 25 LTS)
- Kiến thức Java cơ bản (biến, vòng lặp, OOP)
- Đã hoàn thành các phần 1-4 của tutorial Java

## Các tính năng Java được sử dụng

Mục này là "danh sách công cụ trong hộp": tính năng nào thuộc version Java nào, để bạn biết thứ nào dùng được trên project của mình.

| Tính năng | Java Version | Mục đích |
|-----------|--------------|----------|
| Lambda Expressions | Java 8 | Functional programming |
| Stream API | Java 8 | Xử lý dữ liệu dạng stream |
| Optional | Java 8 | Null safety |
| Records | Java 16 | Data class đơn giản |
| Sealed Classes | Java 17 | Kiểm soát inheritance |
| Pattern Matching | Java 16-25 | Switch expressions và instanceof |
| Virtual Threads | Java 21 | Concurrency hiệu quả |
| Scoped Values | Java 21-25 | Thay thế ThreadLocal |

> **Cách đọc bảng này:** xếp theo version tăng dần — dòng càng dưới càng "hiện đại"; đối chiếu cột Java Version với JDK của dự án để biết tính năng nào dùng được ngay.

## Java 25 LTS - Phiên bản mới nhất (2025)

Mục này cập nhật tin tức "bản vá mới nhất của thành phố": Java 25 mang gì mới đáng chú ý so với các bản LTS trước.

Java 25 được phát hành vào **September 2025** với nhiều tính năng mới:

- **Module Import Declarations** (JEP 511): Import toàn bộ module thay vì từng class
- **Flexible Constructor Bodies** (JEP 513): Cho phép code trước `super()`
- **Scoped Values** (JEP 506): Chia sẻ dữ liệu bất biến giữa các thread
- **Key Derivation Function API** (JEP 510): API cho mã hóa
- **Compact Source Files**: Viết nhiều class trong một file
- **Instance Main Methods**: Hàm main không cần static

## Tài liệu tham khảo

- [Java Official Documentation](https://docs.oracle.com/en/java/)
- [Baeldung Java Guides](https://www.baeldung.com/java-tutorial)
- [Java Language Updates](https://docs.oracle.com/java/tutorial/java/javaaoandj)

## Mục tiêu học tập

Mục này là "hợp đồng cam kết đầu ra" của phần nâng cao: hoàn thành 5 bài là bạn đủ 6 năng lực dưới đây.

Sau khi hoàn thành phần này, bạn sẽ:

1. ✅ Hiểu và áp dụng được các design patterns phổ biến
2. ✅ Sử dụng thành thạo Stream API và Lambda
3. ✅ Viết code an toàn với Optional, tránh NullPointerException
4. ✅ Sử dụng Record và Sealed Classes trong code hiện đại
5. ✅ Hiểu cách JVM hoạt động và tối ưu GC
6. ✅ Có nền tảng vững để học các chủ đề chuyên sâu hơn

## So sánh Java 8 vs Java 21+ (Nên nâng cấp)

Mục này là "hóa đơn so sánh" giữa hai thế hệ: bên trái là những gì Java 8 cho bạn, bên phải là thế giới đã đi xa bao nhiêu — đọc để có lý lẽ thuyết phục sếp nâng cấp.

```
Java 8 (2014)                    Java 21+ (2023-2025)
├── Lambda & Stream               ├── Lambda & Stream ✓
├── Optional                      ├── Optional ✓
├── CompletableFuture            ├── Virtual Threads
├──java.time (Date/Time)          ├── Pattern Matching
└── No Records                    ├── Records (Java 16+)
                                 ├── Sealed Classes (Java 17+)
                                 ├── Scoped Values (Java 21+)
                                 ├── String Templates (preview)
                                 └── Foreign Function API
```

---

**Tiếp theo**: [1. Design Patterns](./1_design-patterns/)
