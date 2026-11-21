# Record, Sealed Classes và Pattern Matching

## Giới thiệu

Java 16-21 giới thiệu các tính năng ngôn ngữ hiện đại giúp code ngắn gọn, an toàn và dễ đọc hơn: **Records** (Java 16), **Sealed Classes** (Java 17), và **Pattern Matching** (Java 16-21).

Cả ba là một bộ "ba món ăn kèm" của cùng bữa ăn: Record làm món chính (dữ liệu), Sealed làm gia vị (giới hạn), Pattern Matching là cách ăn (phân xử) — dùng chung sẽ ngon hơn dùng riêng.

> **Yêu cầu**: Java 17+ (LTS). Một số tính năng cần Java 21.

> **Tóm gọn 1 câu:** Record rút gọn data class, Sealed khóa số lượng "con cháu", Pattern Matching phân xử kiểu — ba tính năng hợp thành cặp bài trùng của modeling hiện đại.

---

## 1. Record (Java 16+)

Mục này nói về "khuôn in dữ liệu": một dòng khai báo thay cho cả chục dòng constructor/getter/equals/hashCode.

**Record** là một cách ngắn gọn để tạo **immutable data class** - class chỉ chứa dữ liệu, tự động generate constructor, getters, equals, hashCode, toString. Ví von: class truyền thống là **nhà xây thủ công** (đổ móng, lên tường, sơn từng bước), Record là **nhà lắp ghép** — báo trước danh sách phòng, mọi thứ còn lại được dựng sẵn đúng chuẩn.

### So sánh Class truyền thống vs Record

> 📄 **Code ví dụ:** [`examples/01-so-sanh-class-truyen-thong-vs-record.java`](examples/01-so-sanh-class-truyen-thong-vs-record.java)

### Sử dụng Record

> 📄 **Code ví dụ:** [`examples/02-su-dung-record.java`](examples/02-su-dung-record.java)

### Record trong Spring Boot (DTOs)

> 📄 **Code ví dụ:** [`examples/03-record-trong-spring-boot-dtos.java`](examples/03-record-trong-spring-boot-dtos.java)

### Compact Constructor với validation

> 📄 **Code ví dụ:** [`examples/04-compact-constructor-voi-validation.java`](examples/04-compact-constructor-voi-validation.java)

---

## 2. Sealed Classes (Java 17+)

Mục này nói về "khóa cửa gia phả": class cha tự quyết định chính xác ai được làm con, ai không.

**Sealed Classes** cho phép kiểm soát **chính xác** class nào được phép extend/implement. Hãy hình dung một **câu lạc bộ có danh sách thành viên kín**: chỉ ai có tên trong danh sách mới được gia nhập — không ai tự ý "extends" vào được.

### Code ví dụ

> 📄 **Code ví dụ:** [`examples/05-code-vi-du.java`](examples/05-code-vi-du.java)

### Pattern Matching với Sealed Classes

> 📄 **Code ví dụ:** [`examples/06-pattern-matching-voi-sealed-classes.java`](examples/06-pattern-matching-voi-sealed-classes.java)

### Lợi ích của Sealed Classes

1. **Type safety**: Compiler biết chính xác các subclasses
2. **Exhaustive switch**: Không cần default branch
3. **Domain modeling**: Mô hình hóa chính xác business rules
4. **API design**: Kiểm soát API surface

> **Tóm gọn 1 câu:** Sealed class là "câu lạc bộ danh sách kín" — compiler biết đủ mọi thành viên nên switch không cần default, và business rule được đóng khung chặt.

---

## 3. Pattern Matching

Mục này nói về "kiểm tra và gán một phát": thay vì instanceof rồi cast riêng lẻ, pattern matching gộp hai bước thành một.

### instanceof Pattern Matching (Java 16+)

> 📄 **Code ví dụ:** [`examples/07-instanceof-pattern-matching-java-16.java`](examples/07-instanceof-pattern-matching-java-16.java)

### Switch Expressions (Java 14+)

> 📄 **Code ví dụ:** [`examples/08-switch-expressions-java-14.java`](examples/08-switch-expressions-java-14.java)

### Pattern Matching for Switch (Java 21)

> 📄 **Code ví dụ:** [`examples/09-pattern-matching-for-switch-java-21.java`](examples/09-pattern-matching-for-switch-java-21.java)

---

## Ví dụ tổng hợp: Domain Model với Records + Sealed

Mục này là "bữa tiệc cả bộ ba": Record + Sealed + Pattern Matching cùng vận hành trong một domain model hoàn chỉnh.

> 📄 **Code ví dụ:** [`examples/10-vi-du-tong-hop-domain-model-voi-records.java`](examples/10-vi-du-tong-hop-domain-model-voi-records.java)

> 📄 **Code ví dụ:** [`examples/11-vi-du-tong-hop-domain-model-voi-records.java`](examples/11-vi-du-tong-hop-domain-model-voi-records.java)

---

## So sánh các tính năng theo version

Mục này là "bảng niên đại": tính năng nào ra mắt ở version nào, để bạn biết dùng được gì trên JDK của dự án.

| Tính năng | Java Version | Mô tả |
|-----------|--------------|-------|
| Lambda | 8 | Anonymous functions |
| Record | 16 (preview 14) | Immutable data class |
| Pattern Matching instanceof | 16 | Type check + cast trong 1 dòng |
| Sealed Classes | 17 | Kiểm soát inheritance |
| Pattern Matching for switch | 21 | Switch với patterns |
| Record Patterns | 21 | Deconstruct records |
| Virtual Threads | 21 | Lightweight concurrency |
| Flexible Constructor Bodies | 25 (final) | Code trước super() |
| Scoped Values | 21-25 | Thread-safe context sharing |

> **Cách đọc bảng này:** đọc từ trên xuống theo version tăng dần — ba dòng đầu (Java 8-16) gần như chắc chắn dùng được, các dòng từ 17 trở lên cần đối chiếu JDK của dự án.

## Best Practices

Mục này là "năm bài học xương máu" khi áp dụng cả ba tính năng vào project thật.

1. **Dùng Record cho DTOs, Value Objects**: Thay vì class với Lombok
2. **Dùng Sealed + Pattern Matching**: Cho domain models với finite variants
3. **Records là immutable**: Không thể có setters
4. **Sealed cần cẩn thận**: Chỉ dùng khi số variants thực sự cố định
5. **Kết hợp cả 3**: Records + Sealed + Pattern Matching = powerful domain modeling

> **Tóm gọn 1 câu:** "Nhà lắp ghép" (Record) + "câu lạc bộ kín" (Sealed) + "phân xử một phát ăn ngay" (Pattern Matching) = cách modeling gọn nhất của Java hiện đại.

## Tài liệu tham khảo

- [Java Records (JEP 395)](https://openjdk.org/jeps/395)
- [Sealed Classes (JEP 409)](https://openjdk.org/jeps/409)
- [Pattern Matching for switch](https://openjdk.org/jeps/441)
- [Baeldung Java Records](https://www.baeldung.com/java-record-keyword)

---

**Tiếp theo**: [5. Performance và Garbage Collection](../5_performance-gc/)
