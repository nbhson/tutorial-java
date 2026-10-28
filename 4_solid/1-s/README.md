# S — Single Responsibility Principle (Trách nhiệm duy nhất)

> **Một class chỉ nên có MỘT lý do để thay đổi.** Nếu bạn phải nói "class này làm A, B, C" thì nó đang làm quá nhiều việc.

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Một nhân viên giỏi là người **chuyên** về một việc. Một class cũng vậy: `Calculator` tính toán, `Printer` in ấn, `Database` lưu trữ. Đừng biến một class thành "nhân viên làm đủ thứ".

**Tầng 2 — Định nghĩa (Robert C. Martin):** *"A class should have only one reason to change."* — Một class chỉ nên có **một lý do duy nhất** để bị sửa đổi. "Lý do thay đổi" = nhóm các bên liên quan (stakeholder) yêu cầu sửa.

**Tầng 3 — Vì sao?** Nếu class làm 3 việc, 3 nhóm người khác nhau đều có lý do chạm vào nó:

- Kế toán thay công thức tính lương → sửa class.
- Marketing đổi mẫu báo cáo → sửa class.
- DBA đổi schema → sửa class.

Ba nhóm cùng đụng vào một file → conflict khi merge, một sửa làm gãy hai chỗ còn lại, test không biết tin ai.

## 2. Ví dụ ❌ vi phạm

```java
// ✗ mot class lam 3 viec: tinh luong, in bao cao, luu DB
class NhanVien {
    private String ten;
    private double luongCoBan;

    double tinhLuong() {
        // logic tinh luong — doi khi KE TOAN yeu cau sua
        return luongCoBan * 1.1 - 500;
    }

    void inBaoCaoLuong() {
        // dinh dang bao cao — doi khi BO PHAN KD yeu cau sua
        System.out.println("Bang luong cua " + ten + ": " + tinhLuong());
    }

    void luuDatabase() {
        // SQL — doi khi DBA yeu cau sua
        System.out.println("INSERT INTO nhan_vien VALUES ('" + ten + "')");
    }
}
```

Ba lý do thay đổi trong một class = tần suất sửa gấp 3 lần, nguy cơ hỏng chéo gấp nhiều hơn.

## 3. Ví dụ ✅ tuân thủ

```java
// ✅ tach theo trach nhiem
class NhanVien {                       // chi giu du lieu
    private final String ten;
    private final double luongCoBan;
    NhanVien(String ten, double luong) { this.ten = ten; this.luongCoBan = luong; }
    String ten()          { return ten; }
    double luongCoBan()   { return luongCoBan; }
}

class TinhLuongService {               // chi logic tinh toan
    double tinh(NhanVien nv) { return nv.luongCoBan() * 1.1 - 500; }
}

class BaoCaoLuongService {             // chi dinh dang xuat
    void in(NhanVien nv, TinhLuongService service) {
        System.out.println("Bang luong cua " + nv.ten() + ": " + service.tinh(nv));
    }
}

class NhanVienRepository {             // chi truy van du lieu
    void save(NhanVien nv) {
        System.out.println("INSERT INTO nhan_vien VALUES ('" + nv.ten() + "')");
    }
}
```

## 4. Áp dụng vào pizza-app

Trong `6-pizza-app/src`, mỗi class có một trách nhiệm rõ ràng:

- `Pizza.java` — mô tả quy trình chung của một chiếc pizza (prepare/bake/cut/box).
- `Pizza/DN/DNCheesePizza.java` — định nghĩa thành phần của pizza phô mai kiểu Đà Nẵng.
- `Store/PizzaStore.java` — điều phối: đặt hàng → tạo pizza → quy trình chế biến.
- `Store/DNPizzaStore.java` — quyết định tạo pizza nào cho khu vực Đà Nẵng.

```java
// PizzaStore KHONG tu tao pizza — giao cho subclass qua factory method
public abstract class PizzaStore {
    public void orderPizza(String type) {
        Pizza pizza = CreatePizza(type); // tao pizza la trach nhiem cua subclass
        pizza.prepare();                 // quy trinh la trach nhiem cua Pizza
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
    protected abstract Pizza CreatePizza(String type);
}
```

**Vì sao tách như vậy?** Lý do thay đổi của "quy trình chế biến" (nấu chín khác nhau) và "thành phần pizza" (món mới) là hai lý do **khác nhau** — tách class giúp sửa cái này không động vào cái kia.

## 5. Dấu hiệu nhận biết vi phạm

- 🔴 Tên class chứa `Manager`, `Util`, `Helper`, `Service` bao trùm mọi thứ.
- 🔴 Import của class gồm những "thế giới" không liên quan: vừa `java.sql`, vừa `java.io`, vừa `javax.swing`.
- 🔴 Method của class viết cho những **người dùng khác nhau**.
- 🔴 Muốn test tính lương nhưng phải mock database và máy in.

❌ **Pitfall 1 — Tách cứng nhắc theo tầng kỹ thuật:** chia `Controller/Service/Repository` rồi nhét **mọi** logic vào một `Service` khổng lồ. SRP không phải "mỗi tầng một class" mà là "mỗi **lý do thay đổi** một class".

❌ **Pitfall 2 — Tách quá mảnh:** mỗi method một class → class tạo object khổng lồ, phải đi lang thang qua 20 file để hiểu luồng. SRP là về **lý do thay đổi**, không phải số dòng.

## 6. Bài tập

1. Tìm trong code cũ của bạn một class dài > 200 dòng, liệt kê các nhóm method, tách thành các class theo trách nhiệm.
2. Refactor `Employee` (tính lương + gửi email + lưu DB) thành 4 class. Viết unit test riêng cho phần tính lương không cần mock gì.
3. Trong pizza-app, nếu thêm yêu cầu "in hóa đơn sau khi order" — bạn sẽ đặt class đó ở đâu, vì sao không nhét vào `PizzaStore`?

## 7. Tóm tắt

- SRP: một class = một lý do thay đổi = một nhóm stakeholder.
- Tách theo trách nhiệm, không phải theo tầng kỹ thuật.
- Lợi ích: ít conflict merge, test dễ, sửa không lo hỏng chéo.
