# D — Dependency Inversion Principle (Đảo ngược phụ thuộc)

> **Code cấp cao không nên phụ thuộc code cấp thấp — cả hai phụ thuộc abstraction.** Đây là nguyên tắc nền tảng của Dependency Injection và là lý do Spring Boot tồn tại.

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Ổ điện trên tường không "biết" thiết bị nào cắm vào — nó chỉ cam kết **chuẩn 220V**. Thiết bị nào đạt chuẩn đều dùng được. DIP biến code của bạn thành "ổ điện": business logic (cấp cao) chỉ cam kết một **chuẩn giao tiếp** (interface), còn chi tiết (cấp thấp) cắm vào.

**Tầng 2 — Định nghĩa (Robert C. Martin):**

1. High-level modules should not depend on low-level modules. Both should depend on abstractions.
2. Abstractions should not depend on details. Details should depend on abstractions.

**Tầng 3 — Vì sao "đảo ngược"?** Thông thường luồng gọi đi từ trên xuống (business → DB), nên người ta tưởng phụ thuộc cũng đi từ trên xuống. DIP **đảo chiều mũi tên phụ thuộc**: business logic định nghĩa interface mình cần, tầng thấp **triển khai** interface đó. Luồng gọi vẫn từ trên xuống, nhưng **luồng phụ thuộc** quay ngược về abstraction.

Hãy hình dung tiệm sửa xe chỉ công bố "mình cần một thợ thay lốp biết làm chuẩn" mà không quan tâm thợ đó học ở đâu — ai đạt chuẩn đều xin việc được, và tiệm không bị dính líu khi thợ cũ nghỉ việc.

> **Tóm gọn 1 câu:** DIP là "đặt chuẩn trước, cắm chi tiết sau": tầng cao và tầng thấp cùng phụ thuộc vào một abstraction chung.

## 2. Ví dụ ❌ vi phạm

Mục này cho bạn thấy hậu quả của việc `new` thẳng hạ tầng ngay trong business logic — lúc đổi database hay viết test là lúc "hóa đơn" tới.

> 📄 **Code ví dụ:** [`examples/01-2-vi-du-vi-pham.java`](examples/01-2-vi-du-vi-pham.java)

**Hệ lụy:**

- Đổi database → sửa `OrderService` (một class business thuần) → test lại business logic vì một thay đổi hạ tầng.
- Unit test `OrderService` **bắt buộc** có MySQL thật → chậm, khó viết.
- `OrderService` không thể tồn tại độc lập với MySQL.

## 3. Ví dụ ✅ tuân thủ

Mục này lật ngược mũi tên phụ thuộc phía trên: business logic chỉ giữ "bản mô tả chuẩn" (interface), còn chi tiết cắm vào sau.

> 📄 **Code ví dụ:** [`examples/02-3-vi-du-tuan-thu.java`](examples/02-3-vi-du-tuan-thu.java)

## 4. Liên hệ với pizza-app

Mục này chỉ ra chỗ pizza-app đang làm đúng DIP: tầng Store không hề biết sự tồn tại của các loại pizza cụ thể.

Pizza app áp dụng DIP ở tầng **Store — Pizza**:

> 📄 **Code ví dụ:** [`examples/03-4-lien-he-voi-pizza-app.java`](examples/03-4-lien-he-voi-pizza-app.java)

- `PizzaStore` chỉ phụ thuộc **abstraction** `Pizza` + factory method — không import `Pizza.DN.*` hay `Pizza.SG.*`.
- Các lớp chi tiết (`DNPizzaStore`, `DNCheesePizza`...) phụ thuộc **ngược lại** vào `Pizza`/`PizzaStore`.

Đây là lý do thêm thành phố mới (Nghe, Hue...) không phải sửa gì tầng Store/Pizza — đúng tinh thần DIP + OCP.

## 5. DIP trong Spring Boot — Dependency Injection

Mục này là "cây cầu" dẫn sang phần Spring Boot của khóa học: bạn sẽ thấy Spring chính là người nối dây DIP thay cho bạn.

Spring Boot là DIP được **tự động hóa** ở quy mô lớn:

> 📄 **Code ví dụ:** [`examples/04-5-dip-trong-spring-boot-dependency-injection.java`](examples/04-5-dip-trong-spring-boot-dependency-injection.java)

Bạn đổi từ JPA sang JDBC? Viết class mới implements cùng interface — `OrderService` không đổi. Đây chính là "chương kết" của DIP: framework đảm nhiệm phần "nối dây".

## 6. Dấu hiệu nhận biết vi phạm

Mục này là bộ "kim chỉ nam chẩn đoán": thấy một trong các dấu hiệu dưới đây, business logic của bạn đang dính chặt vào hạ tầng.

- 🔴 Class business `new` trực tiếp class hạ tầng (DB, HTTP, file) bên trong.
- 🔴 Không viết được unit test cho business logic mà không dựng hạ tầng thật.
- 🔴 `import` của tầng business chứa thư viện driver/hạ tầng cụ thể.
- 🔴 Đổi một công nghệ phải "soi" toàn bộ codebase tìm chỗ `new`.

❌ **Pitfall — abstraction vo cam:** tạo interface chỉ có **một** implementer và chắc chắn không bao giờ có implementer thứ hai (ví dụ `StringUtilsInterface`). DIP có chi phí — chỉ áp dụng ở **ranh giới thay đổi** (DB, API bên ngoài, gateways), không phải mọi class.

❌ **Pitfall — pha lẫn DIP với DI:** DI (Dependency Injection) là **kỹ thuật** truyền dependency từ ngoài vào; DIP là **nguyên tắc thiết kế** hướng phụ thuộc về abstraction. Có thể làm DI mà vẫn vi phạm DIP (inject một concrete class cụ thể).

## 7. Bài tập

Mục này để bạn luyện tay "đảo ngược mũi tên" trong code của chính mình — làm bài 1 là đi được nửa đường tới Dependency Injection của Spring.

1. Cho `EmailThongBao { gui(String msg) }` được gọi trực tiếp từ `DangKyService`. Tách DIP: interface `ThongBaoGui`, hai implementer (Email, SMS), inject qua constructor. Viết test `DangKyService` với mock/fake.
2. Trong pizza-app: `Main` đang `new` trực tiếp `HuePizzaStore`. Hãy xem đó là hợp lệ hay vi phạm DIP — lập luận (gợi ý: ai là "composition root"?).
3. Tìm một chỗ trong dự án của bạn tạo connection/HTTP client bên trong business class. Refactor sang interface + inject.

## 8. Tóm tắt

Mục này gom lại bài thành năm gạch đầu dòng để ôn nhanh.

- DIP: cả tầng cao lẫn tầng thấp phụ thuộc **abstraction**; chi tiết triển khai abstraction.
- Kỹ thuật đi kèm: constructor injection, interface tách ở tầng business.
- Lợi ích: đổi hạ tầng không đụng business logic, unit test nhanh với fake/mock.
- Spring Boot = DIP tự động hóa qua Dependency Injection.
- Áp dụng DIP ở ranh giới dễ thay đổi; đừng tạo interface một-implementer cho mọi class.

> **Tóm gọn 1 câu:** DIP là "đặt chuẩn chung cho cả hai tầng phụ thuộc" — và Spring Boot chính là người cầm túi nối dây cho bạn.
