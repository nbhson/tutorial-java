# D — Dependency Inversion Principle (Đảo ngược phụ thuộc)

> **Code cấp cao không nên phụ thuộc code cấp thấp — cả hai phụ thuộc abstraction.** Đây là nguyên tắc nền tảng của Dependency Injection và là lý do Spring Boot tồn tại.

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Ổ điện trên tường không "biết" thiết bị nào cắm vào — nó chỉ cam kết **chuẩn 220V**. Thiết bị nào đạt chuẩn đều dùng được. DIP biến code của bạn thành "ổ điện": business logic (cấp cao) chỉ cam kết một **chuẩn giao tiếp** (interface), còn chi tiết (cấp thấp) cắm vào.

**Tầng 2 — Định nghĩa (Robert C. Martin):**

1. High-level modules should not depend on low-level modules. Both should depend on abstractions.
2. Abstractions should not depend on details. Details should depend on abstractions.

**Tầng 3 — Vì sao "đảo ngược"?** Thông thường luồng gọi đi từ trên xuống (business → DB), nên người ta tưởng phụ thuộc cũng đi từ trên xuống. DIP **đảo chiều mũi tên phụ thuộc**: business logic định nghĩa interface mình cần, tầng thấp **triển khai** interface đó. Luồng gọi vẫn từ trên xuống, nhưng **luồng phụ thuộc** quay ngược về abstraction.

## 2. Ví dụ ❌ vi phạm

```java
// ✗ business logic "cam co" vao MySQL
class OrderService {
    private MySQLDatabase db = new MySQLDatabase(); // tao cung, phu thuoc chi tiet

    void datHang(DonHang don) {
        // ... logic dat hang ...
        db.save(don);   // neu doi sang PostgreSQL hoac them cache? sua code nay!
    }
}

class MySQLDatabase {
    void save(DonHang don) {
        System.out.println("Luu vao MySQL: " + don);
    }
}
```

**Hệ lụy:**

- Đổi database → sửa `OrderService` (một class business thuần) → test lại business logic vì một thay đổi hạ tầng.
- Unit test `OrderService` **bắt buộc** có MySQL thật → chậm, khó viết.
- `OrderService` không thể tồn tại độc lập với MySQL.

## 3. Ví dụ ✅ tuân thủ

```java
// ✅ 1. business logic DINH NGHIA abstraction minh can
interface DonHangRepository {
    void save(DonHang don);
}

// ✅ 2. business logic chi phu thuoc abstraction
class OrderService {
    private final DonHangRepository repo;   // phu thuoc interface

    OrderService(DonHangRepository repo) {  // 3. inject tu ngoai (constructor injection)
        this.repo = repo;
    }

    void datHang(DonHang don) {
        // ... logic dat hang ...
        repo.save(don);
    }
}

// ✅ 4. chi tiet phu thuoc abstraction (muoi ten nguoc lai)
class MySQLDonHangRepository implements DonHangRepository {
    public void save(DonHang don) { System.out.println("Luu vao MySQL: " + don); }
}

class MemoryDonHangRepository implements DonHangRepository { // de test
    private final List<DonHang> daLuu = new ArrayList<>();
    public void save(DonHang don) { daLuu.add(don); }
    List<DonHang> daLuu() { return daLuu; }
}

// su dung
OrderService prod = new OrderService(new MySQLDonHangRepository());
OrderService test = new OrderService(new MemoryDonHangRepository()); // test khong can DB!
```

## 4. Liên hệ với pizza-app

Pizza app áp dụng DIP ở tầng **Store — Pizza**:

```java
// Store (cap cao) khong biet ton tai DNCheesePizza (chi tiet)
public abstract class PizzaStore {
    public void orderPizza(String type) {
        Pizza pizza = CreatePizza(type);  // chi biet Pizza (abstraction)
        pizza.prepare();                  // goi qua hop dong cua Pizza
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
    protected abstract Pizza CreatePizza(String type);
}
```

- `PizzaStore` chỉ phụ thuộc **abstraction** `Pizza` + factory method — không import `Pizza.DN.*` hay `Pizza.SG.*`.
- Các lớp chi tiết (`DNPizzaStore`, `DNCheesePizza`...) phụ thuộc **ngược lại** vào `Pizza`/`PizzaStore`.

Đây là lý do thêm thành phố mới (Nghe, Hue...) không phải sửa gì tầng Store/Pizza — đúng tinh thần DIP + OCP.

## 5. DIP trong Spring Boot — Dependency Injection

Spring Boot là DIP được **tự động hóa** ở quy mô lớn:

```java
@Service
public class OrderService {
    private final DonHangRepository repo;       // chi biet interface

    public OrderService(DonHangRepository repo) { // Spring tu tim bean trien khai va inject
        this.repo = repo;
    }
}

@Repository
public class JpaDonHangRepository implements DonHangRepository { /* JPA */ }
```

Bạn đổi từ JPA sang JDBC? Viết class mới implements cùng interface — `OrderService` không đổi. Đây chính là "chương kết" của DIP: framework đảm nhiệm phần "nối dây".

## 6. Dấu hiệu nhận biết vi phạm

- 🔴 Class business `new` trực tiếp class hạ tầng (DB, HTTP, file) bên trong.
- 🔴 Không viết được unit test cho business logic mà không dựng hạ tầng thật.
- 🔴 `import` của tầng business chứa thư viện driver/hạ tầng cụ thể.
- 🔴 Đổi một công nghệ phải "soi" toàn bộ codebase tìm chỗ `new`.

❌ **Pitfall — abstraction vo cam:** tạo interface chỉ có **một** implementer và chắc chắn không bao giờ có implementer thứ hai (ví dụ `StringUtilsInterface`). DIP có chi phí — chỉ áp dụng ở **ranh giới thay đổi** (DB, API bên ngoài, gateways), không phải mọi class.

❌ **Pitfall — pha lẫn DIP với DI:** DI (Dependency Injection) là **kỹ thuật** truyền dependency từ ngoài vào; DIP là **nguyên tắc thiết kế** hướng phụ thuộc về abstraction. Có thể làm DI mà vẫn vi phạm DIP (inject một concrete class cụ thể).

## 7. Bài tập

1. Cho `EmailThongBao { gui(String msg) }` được gọi trực tiếp từ `DangKyService`. Tách DIP: interface `ThongBaoGui`, hai implementer (Email, SMS), inject qua constructor. Viết test `DangKyService` với mock/fake.
2. Trong pizza-app: `Main` đang `new` trực tiếp `HuePizzaStore`. Hãy xem đó là hợp lệ hay vi phạm DIP — lập luận (gợi ý: ai là "composition root"?).
3. Tìm một chỗ trong dự án của bạn tạo connection/HTTP client bên trong business class. Refactor sang interface + inject.

## 8. Tóm tắt

- DIP: cả tầng cao lẫn tầng thấp phụ thuộc **abstraction**; chi tiết triển khai abstraction.
- Kỹ thuật đi kèm: constructor injection, interface tách ở tầng business.
- Lợi ích: đổi hạ tầng không đụng business logic, unit test nhanh với fake/mock.
- Spring Boot = DIP tự động hóa qua Dependency Injection.
- Áp dụng DIP ở ranh giới dễ thay đổi; đừng tạo interface một-implementer cho mọi class.
