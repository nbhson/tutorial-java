# Design Patterns - Các Mẫu Thiết Kế

## Design Patterns là gì?

Mục này trả lời câu hỏi "pattern là gì" trước khi học từng pattern: chúng không phải code copy-paste mà là các bản thiết kế đã được cả ngành kiểm chứng.

**Design Pattern** là giải pháp tổng quát, có thể tái sử dụng cho các vấn đề thường gặp trong thiết kế phần mềm. Chúng không phải là code hoàn chỉnh, mà là "blueprint" (bản thiết kế) mô tả cách giải quyết vấn đề.

Hãy hình dung pattern như **mẫu số của những bộ quần áo may sẵn**: mỗi mẫu ra đời từ một "dáng người" (vấn đề) lặp đi lặp lại — bạn không cần tự cắt may từ đầu, chỉ chọn đúng mẫu và chỉnh lại theo số đo của mình.

> **Yêu cầu**: Java 17+. Các ví dụ dùng tính năng hiện đại như Records, Sealed Classes, Pattern Matching.

> **Tóm gọn 1 câu:** Design pattern là "bản thiết kế chuẩn" cho những bài toán lặp lại — không copy nguyên xi, mà điều chỉnh theo bài toán của bạn.

## Phân loại Design Patterns

Mục này xếp 23 pattern kinh điển (GoF) vào 3 nhóm theo mục đích — biết nhóm nào làm việc gì là tìm đúng pattern nhanh hơn.

| Loại | Mục đích | Ví dụ |
|------|----------|-------|
| **Creational** | Cách tạo object | Singleton, Factory, Builder |
| **Structural** | Cấu trúc class/object | Adapter, Decorator, Facade |
| **Behavioral** | Giao tiếp giữa objects | Observer, Strategy, Command |

> **Cách đọc bảng này:** ba nhóm tương ứng ba câu hỏi — "tạo object thế nào?" (Creational), "gắn kết object ra sao?" (Structural), "object nói chuyện với nhau ra sao?" (Behavioral); 5 pattern của bài này nằm rải trong ba nhóm đó.

Trong tutorial này, chúng ta học 5 patterns phổ biến nhất.

---

## 1. Singleton Pattern

Mục này nói về pattern đầu tiên và gây tranh cãi nhất: đảm bảo một class chỉ tồn tại đúng một instance trên toàn ứng dụng.

**Mục đích**: Đảm bảo một class chỉ có **một instance duy nhất** và cung cấp điểm truy cập toàn cục. Hãy hình dung một thành phố chỉ có **một văn phòng đăng ký kết hôn** — ai cũng phải đến đúng chỗ đó, không thể dựng cái thứ hai tự phong.

### Khi nào dùng?
- Database connection pool
- Logger
- Configuration manager
- Cache

### Code ví dụ

> 📄 **Code ví dụ:** [`examples/01-code-vi-du.java`](examples/01-code-vi-du.java)

> 📄 **Code ví dụ:** [`examples/02-code-vi-du.java`](examples/02-code-vi-du.java)

### Ưu/Nhược điểm

| Ưu điểm | Nhược điểm |
|---------|-----------|
| Chỉ 1 instance toàn cục | Khó test (global state) |
| Lazy initialization | Phải cẩn thận thread-safety |
| Tránh overhead tạo nhiều objects | Có thể che giấu dependencies |

> **Cách đọc bảng này:** đọc theo cặp ngang — mỗi ưu điểm đi kèm một nhược điểm như "hai mặt của một đồng xu"; ví dụ tiện ở chỗ "1 instance toàn cục" nhưng chính điều đó khiến nó khó test.

---

## 2. Factory Pattern

Mục này nói về "trạm đặt hàng chung": client chỉ cần nói muốn gì, việc chọn đúng lớp cụ thể để tạo là việc của factory.

**Mục đích**: Tạo objects mà **không expose logic khởi tạo** cho client. Client chỉ cần biết interface chung — giống như gọi đồ ăn qua app: bạn chỉ đặt "một ly trà sữa", không cần biết quán nào pha, pha thế nào.

### Code ví dụ: Notification Factory

> 📄 **Code ví dụ:** [`examples/03-code-vi-du-notification-factory.java`](examples/03-code-vi-du-notification-factory.java)

> 📄 **Code ví dụ:** [`examples/04-code-vi-du-notification-factory.java`](examples/04-code-vi-du-notification-factory.java)

### Khi nào dùng Factory?

- Không biết trước chính xác loại object cần tạo (chỉ biết interface)
- Muốn tập trung logic tạo object ở một nơi
- Muốn giảm coupling giữa client và concrete classes

---

## 3. Builder Pattern

Mục này giải quyết bài toán "constructor quá dài": khi một object cần rất nhiều tham số tùy chọn, Builder cho phép lắp ghép từng bước cho dễ đọc.

**Mục đích**: Tạo objects phức tạp với **nhiều optional parameters** một cách dễ đọc, tránh "telescoping constructors" — như đặt bánh theo menu thay vì đọc cả đoạn "bánh không hành, thêm phô mai, ít đường, cut đôi, ăn tại chỗ..." vào một câu gọi đơn duy nhất.

### Vấn đề với Constructor

> 📄 **Code ví dụ:** [`examples/05-van-e-voi-constructor.java`](examples/05-van-e-voi-constructor.java)

### Code ví dụ: Builder với Record

> 📄 **Code ví dụ:** [`examples/06-code-vi-du-builder-voi-record.java`](examples/06-code-vi-du-builder-voi-record.java)

### Builder trong Spring Boot (thường thấy)

> 📄 **Code ví dụ:** [`examples/07-builder-trong-spring-boot-thuong-thay.java`](examples/07-builder-trong-spring-boot-thuong-thay.java)

---

## 4. Observer Pattern

Mục này nói về "hệ thống thông báo": một nơi thay đổi, mọi bên đăng ký theo dõi đều được báo tự động — không ai phải đi hỏi từng người.

**Mục đích**: Định nghĩa **one-to-many** dependency - khi một object thay đổi, tất cả dependents được thông báo tự động. Ví von: như **bảng điện tử sân bay** — chuyến bay trễ một lần, cả trăm hành khách đứng nhìn bảng đều biết ngay, không cần nhân viên chạy báo từng người.

### Code ví dụ: Event System

> 📄 **Code ví dụ:** [`examples/08-code-vi-du-event-system.java`](examples/08-code-vi-du-event-system.java)

> 📄 **Code ví dụ:** [`examples/09-code-vi-du-event-system.java`](examples/09-code-vi-du-event-system.java)

### Observer trong Spring (ApplicationEventPublisher)

> 📄 **Code ví dụ:** [`examples/10-observer-trong-spring-applicationeventpublisher.java`](examples/10-observer-trong-spring-applicationeventpublisher.java)

---

## 5. Strategy Pattern

Mục này nói về "đổi chiến thuật giữa trận": cùng một việc nhưng nhiều cách làm, và cách làm được thay đổi lúc runtime mà không sửa code client.

**Mục đích**: Định nghĩa **một tập hợp algorithms**, đóng gói từng cái, và cho phép swap linh hoạt tại runtime — như app gọi xe cho bạn chọn **xe máy, xe 4 chỗ hay xe 7 chỗ** cùng một hành trình, chỉ khác "chiến thuật" di chuyển.

### Code ví dụ: Thanh toán

> 📄 **Code ví dụ:** [`examples/11-code-vi-du-thanh-toan.java`](examples/11-code-vi-du-thanh-toan.java)

> 📄 **Code ví dụ:** [`examples/12-code-vi-du-thanh-toan.java`](examples/12-code-vi-du-thanh-toan.java)

---

## Pattern Comparison

Mục này là bảng "so găng" 5 pattern vừa học: đặt cạnh nhau để bạn chọn đúng pattern cho từng tình huống.

| Pattern | Mục đích chính | Khi nào dùng |
|---------|----------------|--------------|
| **Singleton** | 1 instance duy nhất | Config, Logger, Connection Pool |
| **Factory** | Tạo object, che giấu logic | Không biết trước class cụ thể |
| **Builder** | Tạo object phức tạp | Nhiều optional parameters |
| **Observer** | Thông báo khi state thay đổi | Event systems, pub/sub |
| **Strategy** | Swap algorithms linh hoạt | Nhiều cách làm 1 việc |

> **Cách đọc bảng này:** mỗi dòng là một pattern, đọc cột "Khi nào dùng" trước — đó là phần bạn tra khi gặp bài toán thật; quay lại cột "Mục đích chính" nếu cần phân biệt hai pattern dễ nhầm (ví dụ Factory vs Builder, đều là "tạo object").

## Tài liệu tham khảo

- [Refactoring.Guru - Design Patterns](https://refactoring.guru/design-patterns/java)
- [Baeldung Design Patterns](https://www.baeldung.com/java-design-patterns)
- [GoF Design Patterns](https://en.wikipedia.org/wiki/Design_Patterns)

---

**Tiếp theo**: [2. Stream API và Lambda Expressions](../2_stream-api-lambda/)
