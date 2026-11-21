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

> **Cách đọc bảng này:** mỗi dòng là một nguyên tắc, đứng theo chữ cái S-O-L-I-D; đọc cột "Câu hỏi cốt lõi" là nắm được tinh thần ngay — mỗi nguyên tắc đều trả lời bằng một "được/không được" dứt khoát. Click vào tên để vào bài chi tiết.

**Tầng 3 — Vì sao SOLID quan trọng?** Hãy xét chi phí thay đổi phần mềm:

```
Chi phí sửa code ∝ (số class bị ảnh hưởng khi thay 1 yêu cầu)
```

- Code không SOLID: 1 yêu cầu mới → sửa 10 class → mỗi sửa 1 chỗ có nguy cơ làm gãy 3 chỗ khác → không dám test lại → bug trôi vào production.
- Code SOLID: 1 yêu cầu mới → tạo 1 class mới (hoặc sửa duy nhất 1 class) → test class đó là xong.

SOLID không giúp code "chạy nhanh hơn" — nó giúp **tốc độ phát triển dài hạn không suy giảm**.

> **Tóm gọn 1 câu:** SOLID là bộ LEGO cho code — mỗi khối một việc, thay khối này không làm gãy khối kia, nên dự án lớn vẫn sửa được nhanh sau nhiều năm.

## 2. Cấu trúc thư mục

Mục này cho bạn xem bản đồ kho tài liệu: 5 bài lý thuyết + 1 project thực hành, để bạn biết mình đang đứng đâu trên lộ trình.

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

Mục này giới thiệu "lực đẩy chính" của cả chương: một quán pizza ảo mà ta sẽ mổ xẻ xuyên suốt 5 bài lý thuyết.

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

> 📄 **Code ví dụ:** [`examples/01-3-project-thuc-hanh-pizza-app.java`](examples/01-3-project-thuc-hanh-pizza-app.java)

Chúng ta sẽ dùng chính project này làm ví dụ xuyên suốt 5 bài lý thuyết — giống như học giải phẫu trên cùng một "bệnh nhân" thay vì đổi bệnh nhân mỗi buổi học.

## 4. Tóm tắt nhanh cả 5 nguyên tắc

Mục này là bản "tóm tắt lướt" 5 bài phía dưới: mỗi nguyên tắc một đoạn ngắn kèm code mẫu, đủ để bạn ôn lại trong 5 phút trước buổi phỏng vấn.

### 4.1. SRP — mỗi class một trách nhiệm

> 📄 **Code ví dụ:** [`examples/02-4-1-srp-moi-class-mot-trach-nhiem.java`](examples/02-4-1-srp-moi-class-mot-trach-nhiem.java)

### 4.2. OCP — mở rộng bằng thêm code, không phải sửa code

> 📄 **Code ví dụ:** [`examples/03-4-2-ocp-mo-rong-bang-them-code.java`](examples/03-4-2-ocp-mo-rong-bang-them-code.java)

### 4.3. LSP — lớp con không được phá hành vi của lớp cha

> 📄 **Code ví dụ:** [`examples/04-4-3-lsp-lop-con-khong-uoc-pha.java`](examples/04-4-3-lsp-lop-con-khong-uoc-pha.java)

### 4.4. ISP — nhiều interface nhỏ tốt hơn một interface béo

> 📄 **Code ví dụ:** [`examples/05-4-4-isp-nhieu-interface-nho-tot-hon.java`](examples/05-4-4-isp-nhieu-interface-nho-tot-hon.java)

### 4.5. DIP — phụ thuộc abstraction, không phụ thuộc chi tiết

> 📄 **Code ví dụ:** [`examples/06-4-5-dip-phu-thuoc-abstraction-khong-phu.java`](examples/06-4-5-dip-phu-thuoc-abstraction-khong-phu.java)

## 5. Các dấu hiệu nhận biết code vi phạm SOLID

Mục này là "hộp kiểm tra sức khỏe": nếu code của bạn xuất hiện mấy triệu chứng dưới đây, khả năng cao nó đang vi phạm một nguyên tắc SOLID nào đó.

- 🔴 Class dài > 300 dòng với nhiều nhóm method không liên quan.
- 🔴 Nhiều `if-else` / `switch` theo loại (type) ở nhiều nơi khác nhau.
- 🔴 Thêm 1 tính năng phải sửa 5+ file.
- 🔴 Unit test một class phải mock 7 dependency.
- 🔴 Lớp con phải override method của cha để "làm rỗng" (`throw new UnsupportedOperationException()`).
- 🔴 Interface có 20+ method, implementer chỉ dùng 3.

> **Tóm gọn 1 câu:** Code vi phạm SOLID thường "kêu" trước khi gãy — class béo, if-else theo loại rải rác, và mỗi tính năng mới phải sửa một loạt file.

## 6. Lộ trình học

Mục này nói về cách học sao cho hiểu chứ không phải đọc cho xong — thứ tự và cách luyện tay với từng nguyên tắc.

1. Đọc từng bài theo thứ tự 1 → 5, mỗi bài có ví dụ ❌/✅.
2. Mở `6-pizza-app/src` trong IDE, chạy `Main`, đọc code liên hệ từng nguyên tắc.
3. Làm bài tập cuối mỗi bài.
4. Về học lại **SOLID + Spring Boot** (Dependency Injection của Spring chính là DIP được framework tự động hóa).

## 7. Tài liệu tham khảo

- Robert C. Martin — *Agile Software Development: Principles, Patterns, and Practices* (nguồn gốc SOLID).
- Head First Design Patterns — chương Factory (nơi pizza app ra đời).
- Refactoring.Guru — minh họa trực quan các nguyên tắc & pattern.
