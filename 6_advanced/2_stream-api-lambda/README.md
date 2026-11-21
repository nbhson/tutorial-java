# Stream API và Lambda Expressions

## Giới thiệu Functional Programming

**Lambda Expressions** và **Stream API** được giới thiệu từ Java 8, mang Functional Programming vào Java. Đây là những tính năng quan trọng nhất để xử lý collections hiện đại.

Hãy hình dung cách cũ là **rót nước từng ly bằng tay** (vòng lặp for chạm vào từng phần tử), còn Stream là **ống dẫn nước có các van lọc**: bạn lắp van (lọc, biến đổi) theo trật tự và chỉ mở khóa cuối — nước tự chảy qua.

> **Yêu cầu**: Java 8+ (một số tính năng nâng cao cần Java 16+)

> **Tóm gọn 1 câu:** Lambda + Stream là cách khai báo "muốn gì" thay vì "làm từng bước như thế nào" khi xử lý collections.

## Lambda Expressions

Mục này giới thiệu "cú pháp rút gọn" của hàm không tên — nền móng để mọi thứ phía sau hoạt động.

Lambda là một cách viết ngắn gọn cho anonymous function (hàm không tên). Ví von: nếu anonymous class là **viết cả lá đơn xin việc** để xin làm một việc nhỏ, thì lambda là **đưa một tấm danh thiếp** — chỉ nói đúng thứ người ta cần.

### Cú pháp

> 📄 **Code ví dụ:** [`examples/01-cu-phap.java`](examples/01-cu-phap.java)

### So sánh trước/sau Lambda

> 📄 **Code ví dụ:** [`examples/02-so-sanh-truoc-sau-lambda.java`](examples/02-so-sanh-truoc-sau-lambda.java)

## Functional Interfaces

Mục này nói về "khuôn khuôn cho lambda": interface chỉ có đúng một phương thức trừu tượng, chính là nơi lambda được gắn vào.

Functional interface là interface có **đúng 1 abstract method**. Java 8 cung cấp 4 interfaces chính trong `java.util.function`. Hãy hình dung chúng như **4 loại phích cắm chuẩn**: cắm 2 chân ra 1 kết quả (Function), chỉ kiểm tra đúng/sai (Predicate), chỉ thu vào không trả ra (Consumer), chỉ phát ra không cần đầu vào (Supplier).

### 4 Functional Interfaces chính

> 📄 **Code ví dụ:** [`examples/03-4-functional-interfaces-chinh.java`](examples/03-4-functional-interfaces-chinh.java)

### Các Functional Interfaces khác

| Interface | Signature | Mô tả |
|-----------|-----------|-------|
| `Predicate<T>` | `T → boolean` | Kiểm tra điều kiện |
| `Consumer<T>` | `T → void` | Nhận giá trị, không trả về |
| `Supplier<T>` | `() → T` | Chỉ trả về giá trị |
| `Function<T,R>` | `T → R` | Chuyển đổi T thành R |
| `BiFunction<T,U,R>` | `(T,U) → R` | 2 tham số vào, 1 ra |
| `UnaryOperator<T>` | `T → T` | Function với cùng input/output type |
| `BinaryOperator<T>` | `(T,T) → T` | 2 tham số cùng type, 1 ra |

> **Cách đọc bảng này:** cột "Signature" là quan trọng nhất — mũi tên `→` nghĩa là "cho vào... để nhận ra..."; 4 dòng đầu là bộ tứ chính, 3 dòng sau là biến thể khi cần nhiều tham số hay cùng kiểu vào/ra.

## Stream API

Mục này là "bảo mật chính" của bài: pipeline xử lý dữ liệu khai báo, gồm 3 công đoạn tạo stream → biến đổi → thu kết quả.

Stream là một "pipeline" các operations để xử lý dữ liệu theo cách declarative.

### Luồng xử lý

```
Source → Intermediate Operations → Terminal Operation
(List)   (filter, map, sorted)     (collect, forEach, reduce)
```

### Tạo Stream

> 📄 **Code ví dụ:** [`examples/04-tao-stream.java`](examples/04-tao-stream.java)

## Các Operations

Mục này phân chia công nhân trên dây chuyền thành hai ca: ca "trung gian" chưa chạy (lazy) và ca "kết thúc" mới kích hoạt cả dây chuyền.

### Intermediate Operations (Lazy)

> 📄 **Code ví dụ:** [`examples/05-intermediate-operations-lazy.java`](examples/05-intermediate-operations-lazy.java)

### Terminal Operations

> 📄 **Code ví dụ:** [`examples/06-terminal-operations.java`](examples/06-terminal-operations.java)

## Ví dụ thực tế

Mục này đưa stream ra "đường đua thật": xử lý danh sách sản phẩm và chạy song song khi dữ liệu lớn.

### Xử lý List Products

> 📄 **Code ví dụ:** [`examples/07-xu-ly-list-products.java`](examples/07-xu-ly-list-products.java)

### Parallel Streams

> 📄 **Code ví dụ:** [`examples/08-parallel-streams.java`](examples/08-parallel-streams.java)

## Optional với Stream

> 📄 **Code ví dụ:** [`examples/09-optional-voi-stream.java`](examples/09-optional-voi-stream.java)

## Best Practices

Mục này là "nội quy sử dụng stream": sáu thói quen giúp code vừa đẹp vừa không tự gây ra lỗi hiệu năng.

1. **Prefer method references** khi có thể: `String::toUpperCase` thay vì `s -> s.toUpperCase()`
2. **Tránh side effects**: Stream không nên mutate external state
3. **Dùng Collectors utilities**: `toList()`, `joining()`, `groupingBy()`
4. **Lazy evaluation**: Intermediate ops không chạy cho đến khi có terminal op
5. **Không reuse Stream**: Mỗi stream chỉ được consume 1 lần
6. **Parallel stream cẩn thận**: Chỉ dùng cho CPU-bound + dataset lớn

> **Tóm gọn 1 câu:** Stream là dây chuyền lắp ráp khai báo — lắp van (intermediate) chưa chạy, mở khóa (terminal) mới chảy, và dây chuyền chỉ chạy được một lần duy nhất.

## Tài liệu tham khảo

- [Java Stream API Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/package-summary.html)
- [Baeldung Java Stream API](https://www.baeldung.com/java-8-streams)
- [Baeldung Lambda Expressions](https://www.baeldung.com/java-8-lambda-expressions-tutorial)

---

**Tiếp theo**: [3. Optional và Null Safety](../3_optional-null-safety/)
