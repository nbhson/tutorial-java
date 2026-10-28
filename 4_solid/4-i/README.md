# I — Interface Segregation Principle (Phân tách giao diện)

> **Đừng ép client phụ thuộc những method nó không dùng.** Nhiều interface nhỏ, chuyên biệt tốt hơn một interface "béo".

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Bạn thuê thợ sửa ống nước, hợp đồng bắt anh ta phải ký cam kết cả "sửa điện, sơn tường, làm móng". Vô lý — anh ta chỉ cần **một hợp đồng về nước**. Interface béo cũng vậy: ép người implement ký vào những cam kết họ không quan tâm.

**Tầng 2 — Định nghĩa (Robert C. Martin):** *"Clients should not be forced to depend on methods they do not use."* — Client không nên bị ép phụ thuộc method mà nó không dùng.

**Tầng 3 — Vì sao?** Interface béo tạo **coupling giả**: một thay đổi nhỏ ở method X (mà client A không dùng) vẫn buộc client A recompile/redeploy. Interface nhỏ theo **nhóm người dùng** (client) giúp thay đổi ảnh hưởng đúng phạm vi.

## 2. Ví dụ ❌ vi phạm

```java
// ✗ mot interface "beo" — ep tat ca may in lam moi thu
interface MayIn {
    void in(String taiLieu);
    void scan(String taiLieu);
    void fax(String soDienThoai);
}

class MayInInOnly implements MayIn {
    public void in(String taiLieu)   { System.out.println("Dang in: " + taiLieu); }

    public void scan(String taiLieu) {
        throw new UnsupportedOperationException(); // may nay khong scan!
    }

    public void fax(String soDienThoai) {
        throw new UnsupportedOperationException(); // may nay khong fax!
    }
}
```

**Vấn đề:** `MayInInOnly` phải ký hợp đồng scan/fax dù không làm. Runtime sẽ nổ khi ai đó gọi — lỗi mà compiler không cảnh báo.

## 3. Ví dụ ✅ tuân thủ

```java
// ✅ tach nho theo nang luc
interface CoTheIn {
    void in(String taiLieu);
}

interface CoTheScan {
    void scan(String taiLieu);
}

interface CoTheFax {
    void fax(String soDienThoai);
}

class MayInInOnly implements CoTheIn {
    public void in(String taiLieu) { System.out.println("Dang in: " + taiLieu); }
}

// may da chuc nang implement nhung interface can
class MayInDaNang implements CoTheIn, CoTheScan, CoTheFax {
    public void in(String taiLieu)         { System.out.println("In: " + taiLieu); }
    public void scan(String taiLieu)       { System.out.println("Scan: " + taiLieu); }
    public void fax(String soDienThoai)    { System.out.println("Fax: " + soDienThoai); }
}

// client chi phu thuoc nang luc minh can
class TrungTamPhoto {
    void xongViec(CoTheIn mayIn) { mayIn.in("tai lieu"); } // khong biet fax lam gi
}
```

**Vì sao tốt hơn?**
- Không còn `UnsupportedOperationException` nào.
- Thêm method mới vào `CoTheFax` chỉ ảnh hưởng các class fax thật — `MayInInOnly` không phải recompile vì lý do liên quan fax.

## 4. Liên hệ với pizza-app

Pizza app không vi phạm ISP: mỗi interface/abstract class đều gọn.

- `Pizza` (abstract) chỉ gồm các bước chung mà **mọi** pizza đều có; phần khác nhau (`box()`) là abstract — mọi subclass buộc phải có, hợp lý vì mọi pizza đều được đóng hộp.
- `PizzaStore` chỉ yêu cầu một method duy nhất `CreatePizza(type)` — đây là interface "vừa khít" với nhu cầu của `Main` và của quy trình order.

```java
// PizzaStore: hop dong nho, vua khit — khong method nao thua
public abstract class PizzaStore {
    public void orderPizza(String type) { /* quy trinh chung */ }
    protected abstract Pizza CreatePizza(String type); // duy nhat subclass phai lam
}
```

Nếu `PizzaStore` ép subclass override thêm `doanhThu()`, `nhanVien()`, `khoHang()`... thì đó là ISP bị vi phạm — không phải store nào cũng báo cáo doanh thu theo cùng cách.

## 5. Interface mặc định (default method) — khi nào được dùng?

Từ Java 8, interface có `default method`. Đây **không phải** cái cớ để giữ interface béo:

```java
interface CoTheIn {
    void in(String taiLieu);

    // default method: hop ly khi no la HANH VI CHUNG cua TAT CA implementer
    default void inNhieu(String... taiLieus) {
        for (String tl : taiLieus) in(tl);   // dung lai in() — khong phai method "thua"
    }
}
```

✅ `default` hợp lệ khi hành vi được xây từ các method trừu tượng **mà mọi implementer đều có**.
❌ `default` là anti-pattern khi nó tồn tại chỉ để "cho qua" những implementer không cần method đó.

## 6. Dấu hiệu nhận biết vi phạm

- 🔴 Interface có nhiều method mà 1 implementer chỉ dùng một phần.
- 🔴 Xuất hiện `UnsupportedOperationException` khi implement.
- 🔴 Method hoặc field được comment "không dùng cho trường hợp X".
- 🔴 Client import cả interface để gọi 1-2 method.

❌ **Pitfall — tách quá nhỏ:** một interface một method (trừ trường hợp functional interface như `Runnable`) khiến hệ thống thành "bánh mì sandwich 100 lớp". Tách theo **nhóm hành vi cùng quan tâm của một loại client**, không phải theo số dòng.

## 7. Bài tập

1. Cho interface `NhanVien { lamViec(); nghiPhep(); nhanLuong(); lamThemGio(); }` và các loại nhân viên (toàn thời gian, bán thời gian, thực tập sinh không hưởng lương). Tách interface đúng ISP.
2. Trong một dự án, tìm interface có > 10 method; liệt kê từng implementer dùng method nào; đề xuất cách tách.
3. Áp dụng cho pizza-app: nếu thêm `BanhMiStore`, `TraSuaStore` — `PizzaStore` có cần tách thành interface `Store` chung không? Tranh luận lúc nào nên, lúc nào không.

## 8. Tóm tắt

- ISP: interface nhỏ theo nhóm client; không ép implement method không dùng.
- `UnsupportedOperationException` khi implement = mùi ISP (và LSP).
- `default method` chỉ dùng cho hành vi chung thật sự của mọi implementer.
- Cân bằng: tách theo quan điểm nghiệp vụ, tránh một-interface-một-method máy móc.
