# SOLID Principles — 5 nguyên tắc thiết kế hướng đối tượng

> SOLID không phải "luật" mà là **5 nguyên tắc** giúp code của bạn dễ mở rộng, dễ test, dễ bảo trì. Vi phạm SOLID không làm chương trình lỗi ngay — nó làm chương trình **đau đớn khi thay đổi**. Đây là kiến thức bắt buộc trước khi vào Spring Boot.

## 1. SOLID là gì? Vì sao phải học?

**Tầng 1 — Trực giác:** Code không SOLID giống một **chiếc xe gắn các linh kiện bằng keo dán**: chạy được hôm nay, nhưng chỉ cần thay một linh kiện là rơi nát cả xe. Code SOLID giống **bộ LEGO**: mỗi khối độc lập, thay khối này không làm gãy khối kia.

**Tầng 2 — Nguồn gốc:** Do Robert C. Martin (Uncle Bob) hệ thống hóa, bao gồm:

| # | Nguyên tắc | Tên tiếng Việt | Câu hỏi cốt lõi |
|---|------------|----------------|-----------------|
| **S** | [Single Responsibility](./1-s/README.md) | Trách nhiệm duy nhất | Class này vì lý do nào phải thay đổi? Chỉ được có MỘT. |
| **O** | [Open/Closed](./2-o/README.md) | Mở để mở rộng, đóng để sửa | Thêm tính năng mới có phải sửa code cũ không? Không được phép. |
| **L** | [Liskov Substitution](./3-l/README.md) | Thay thế Liskov | Lớp con có thể đứng chỗ lớp cha mà không hỏng hành vi không? Phải được. |
| **I** | [Interface Segregation](./4-i/README.md) | Phân tách giao diện | Bị ép implement method không dùng? Không được phép. |
| **D** | [Dependency Inversion](./5-d/README.md) | Đảo ngược phụ thuộc | Code cấp cao có phụ thuộc vào code cấp thấp không? Không được — cả hai phụ thuộc vào abstraction. |

**Tầng 3 — Vì sao SOLID quan trọng?** Hãy xét chi phí thay đổi phần mềm:

```
Chi phí sửa code ∝ (số class bị ảnh hưởng khi thay 1 yêu cầu)
```

- Code không SOLID: 1 yêu cầu mới → sửa 10 class → mỗi sửa 1 chỗ có nguy cơ làm gãy 3 chỗ khác → không dám test lại → bug trôi vào production.
- Code SOLID: 1 yêu cầu mới → tạo 1 class mới (hoặc sửa duy nhất 1 class) → test class đó là xong.

SOLID không giúp code "chạy nhanh hơn" — nó giúp **tốc độ phát triển dài hạn không suy giảm**.

## 2. Cấu trúc thư mục

```
4_solid/
├── 1-s/    → Single Responsibility Principle (SRP)
├── 2-o/    → Open/Closed Principle (OCP)
├── 3-l/    → Liskov Substitution Principle (LSP)
├── 4-i/    → Interface Segregation Principle (ISP)
├── 5-d/    → Dependency Inversion Principle (DIP)
└── 6-pizza-app/ → Dự án thực hành: quán pizza áp dụng cả 5 nguyên tắc
```

## 3. Project thực hành: Pizza App

Toàn bộ thư mục `6-pizza-app` là một dự án áp dụng SOLID theo phong cách "Factory Method" kinh điển (từ cuốn Head First Design Patterns). Cấu trúc:

```
6-pizza-app/src/
├── Main.java                  → entry point
├── Pizza/Pizza.java           → abstract class cha
├── Pizza/DN/  DNCheesePizza, DNGreekPizza, DNPepperonPizza
├── Pizza/SG/  SGCheesePizza, SGGreekPizza, SGCheesePizza
├── Pizza/Hue/ HueCheesePizza, HueGreekPizza, HuePepperonPizza
└── Store/     PizzaStore (abstract), DNPizzaStore, SGPizzaStore, HuePizzaStore
```

Luồng hoạt động:

```java
// Main.java
HuePizzaStore huePizzaStore = new HuePizzaStore();
huePizzaStore.orderPizza("cheese");

// PizzaStore.java (abstract)
public void orderPizza(String type) {
    Pizza pizza = CreatePizza(type);  // phương thức factory — mỗi store tự tạo pizza riêng
    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
}
```

Chúng ta sẽ dùng chính project này làm ví dụ xuyên suốt 5 bài lý thuyết.

## 4. Tóm tắt nhanh cả 5 nguyên tắc

### 4.1. SRP — mỗi class một trách nhiệm

```java
// ✗ vi phạm: một class vừa tính lương, vừa in báo cáo, vừa lưu DB
class NhanVien {
    void tinhLuong() {}
    void inBaoCao() {}
    void luuDatabase() {}
}

// ✅ tách: mỗi class một lý do thay đổi duy nhất
class TinhLuongService { void tinhLuong(NhanVien nv) {} }
class BaoCaoService     { void in(NhanVien nv) {} }
class NhanVienRepository{ void save(NhanVien nv) {} }
```

### 4.2. OCP — mở rộng bằng thêm code, không phải sửa code

```java
// ✗ mỗi loại khuyến mãi mới phải sửa if-else trong class cũ
double tinhGiam(double total, String loai) {
    if (loai.equals("VIP")) return total * 0.1;
    if (loai.equals("MỚI"))  return 50_000;
    return 0;
}

// ✅ mở rộng bằng implement interface mới
interface KhuyenMai { double giamGia(double total); }
class KhuyenMaiVip implements KhuyenMai { public double giamGia(double t) { return t * 0.1; } }
class KhuyenMaiMoi implements KhuyenMai { public double giamGia(double t) { return 50_000; } }
```

### 4.3. LSP — lớp con không được phá hành vi của lớp cha

```java
// ✗ vi phạm kinh điển: Vuong extends ChuNhat nhưng phá rule
class ChuNhat { void setW(int w); void setH(int h); }
class Vuong extends ChuNhat {  // setW đồng thời setH → phá kỳ vọng của caller
}

// ✅ tách interface riêng nếu hành vi khác nhau
interface Hinh { double dienTich(); }
```

### 4.4. ISP — nhiều interface nhỏ tốt hơn một interface béo

```java
// ✗ một interface ép implement những method không cần
interface MayIn { in(); scan(); fax(); }

// ✅ tách nhỏ
interface CoTheIn  { in(); }
interface CoTheScan{ scan(); }
// MayInInOnly chỉ implements CoTheIn
```

### 4.5. DIP — phụ thuộc abstraction, không phụ thuộc chi tiết

```java
// ✗ phụ thuộc cứng vào MySQL
class UserService {
    private MySQLDatabase db = new MySQLDatabase(); // muốn đổi PostgreSQL? sửa code!
}

// ✅ phụ thuộc interface
class UserService {
    private final UserRepository db;      // abstraction
    UserService(UserRepository db) { this.db = db; } // inject từ ngoài — chính là DI trong Spring
}
```

## 5. Các dấu hiệu nhận biết code vi phạm SOLID

- 🔴 Class dài > 300 dòng với nhiều nhóm method không liên quan.
- 🔴 Nhiều `if-else` / `switch` theo loại (type) ở nhiều nơi khác nhau.
- 🔴 Thêm 1 tính năng phải sửa 5+ file.
- 🔴 Unit test một class phải mock 7 dependency.
- 🔴 Lớp con phải override method của cha để "làm rỗng" (`throw new UnsupportedOperationException()`).
- 🔴 Interface có 20+ method, implementer chỉ dùng 3.

## 6. Lộ trình học

1. Đọc từng bài theo thứ tự 1 → 5, mỗi bài có ví dụ ❌/✅.
2. Mở `6-pizza-app/src` trong IDE, chạy `Main`, đọc code liên hệ từng nguyên tắc.
3. Làm bài tập cuối mỗi bài.
4. Về học lại **SOLID + Spring Boot** (Dependency Injection của Spring chính là DIP được framework tự động hóa).

## 7. Tài liệu tham khảo

- Robert C. Martin — *Agile Software Development: Principles, Patterns, and Practices* (nguồn gốc SOLID).
- Head First Design Patterns — chương Factory (nơi pizza app ra đời).
- Refactoring.Guru — minh họa trực quan các nguyên tắc & pattern.
