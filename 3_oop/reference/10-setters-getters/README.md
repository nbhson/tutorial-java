# Setters, Getters và hạn chế của chúng — Builder Pattern

> Getter/setter là thứ đầu tiên bạn học về encapsulation — nhưng cũng là nơi **lạm dụng nhiều nhất**. Bài này chỉ ra khi nào setter là anti-pattern, và giới thiệu **Builder Pattern** — cách tạo object an toàn thay thế.

## 1. Nhắc lại: Getter/Setter là gì?

**Tầng 1 — Trực giác:** Biến `private` là **cửa khóa**; getter/setter là **lễ tân** đứng trước cửa — khách không tự vào phòng, phải thông qua lễ tân kiểm tra.

**Tầng 2 — Định nghĩa:** Encapsulation = ẩn dữ liệu (`private`) + chỉ cho thao tác qua method công khai. Getter đọc giá trị, setter gán giá trị.

```java
public class Person {
    private String name;
    private int age;

    public String getName()        { return name; }
    public void   setName(String n){ this.name = n; }

    public int    getAge()         { return age; }
    public void   setAge(int a)    { this.age = a; }
}
```

## 2. Khi nào setter là anti-pattern?

### 2.1. Setter "mù" — mọi invariant có thể bị phá

```java
// ✗ code client co the tao object o trang thai SAI bat cu luc nao
Person p = new Person();
p.setAge(-5);              // tuoi am? khong ai chan!
p.setName(null);           // ten null? van duoc!

// object "nua trong" — truyen lung tung trong he thong
p.setAge(25);              // ai do quen goi dong nay
xuLyDonHang(p);            // nhan object chua du du lieu
```

**Tầng 3 — Vì sao có hại?** Setter vô điều kiện biến class thành **túi dữ liệu mở**: trạng thái hợp lệ chỉ tồn tại "đôi lúc", và mọi method khác phải tự phòng thủ kiểm tra null/âm/rỗng. Object có **constructor hợp lệ duy nhất** thì trạng thái sai **không thể tồn tại**.

### 2.2. So sánh ❌/✅

```java
// ✗ anti-pattern: setter mù, object khong bao gio "hoan chinh"
public class TaiKhoan {
    private String soTk;
    private long soDu;

    public void setSoTk(String s) { this.soTk = s; }
    public void setSoDu(long d)   { this.soDu = d; }  // ai cung set duoc so du!
}

// ✅ tot hon: tao hop le trong constructor, thay doi qua method y nghia
public class TaiKhoan {
    private final String soTk;   // final: khong the thay sau khi tao
    private long soDu;

    public TaiKhoan(String soTk, long soDuBanDau) {
        if (soTk == null || soTk.isBlank()) throw new IllegalArgumentException("soTK rong");
        if (soDuBanDau < 0) throw new IllegalArgumentException("so du am");
        this.soTk = soTk;
        this.soDu = soDuBanDau;
    }

    public long soDu() { return soDu; }

    public void napTien(long tien) {        // hanh vi co nghia, khong phai "set"
        if (tien <= 0) throw new IllegalArgumentException("Tien nap phai > 0");
        soDu += tien;
    }

    public void rutTien(long tien) {
        if (tien > soDu) throw new IllegalArgumentException("Khong du so du");
        soDu -= tien;
    }
}
```

**Nguyên tắc: đặt câu hỏi "hành vi nghiệp vụ này là gì?"** — `napTien`, `rutTien`, `guiEmail`, `dongDon` — thay vì `setX`/`setY` máy móc.

### 2.3. Getter cũng có thể là vấn đề

❌ Getter trả về **tham chiếu mảng/collection** — bên ngoài sửa được nội dung bên trong:

```java
public class LopHoc {
    private final List<String> hs = new ArrayList<>();

    public List<String> getHocSinh() { return hs; } // ✗ nguoi ngoai hs.clear() duoc!
}

// ✅ tra ve ban sao khong the sua
public List<String> getHocSinh() {
    return List.copyOf(hs); // bang sao khong doi (Java 10+)
}
```

Xem code mẫu `10-setters-getters/src/Main.java`: `setSchoolYear` nhận mảng và lưu `schoolYear.clone()` — đây là cách **phòng thủ đúng** khi bắt buộc nhận mảng: copy dữ liệu vào, tránh bên ngoài thay đổi mảng gốc sau khi set.

## 3. Builder Pattern — thay thế constructor nhiều tham số

### 3.1. Vấn đề: "telescoping constructor"

```java
// ✗ constructor tham so dai — de nham vi tri!
new Pizza("L", true, false, true, false, "An");
// true, false, true la gi? da pho-mat, khong hanh, co oi? khong ai nho duoc
```

### 3.2. Builder — gọi bằng tên, đọc như tiếng Anh

```java
public class Pizza {
    private final String size;          // bat buoc
    private final boolean phoMat;       // tuy chon
    private final boolean hanh;
    private final boolean oi;
    private final String ghiChu;

    private Pizza(Builder b) {          // constructor private — chi Builder tao duoc
        this.size    = b.size;
        this.phoMat  = b.phoMat;
        this.hanh    = b.hanh;
        this.oi      = b.oi;
        this.ghiChu  = b.ghiChu;
    }

    public static class Builder {
        // bat buoc
        private final String size;
        // tuy chon — khoi tao gia tri mac dinh
        private boolean phoMat = true;
        private boolean hanh   = false;
        private boolean oi     = false;
        private String ghiChu  = "";

        public Builder(String size) { this.size = size; }

        public Builder phoMat(boolean v) { this.phoMat = v; return this; }
        public Builder hanh(boolean v)   { this.hanh = v;   return this; }
        public Builder oi(boolean v)     { this.oi = v;     return this; }
        public Builder ghiChu(String s)  { this.ghiChu = s; return this; }

        public Pizza build() {
            if (size == null || size.isBlank())
                throw new IllegalArgumentException("Size bat buoc");
            return new Pizza(this);
        }
    }
}

// ✅ su dung — doc nhu cau thoai mai
Pizza p = new Pizza.Builder("L")
        .phoMat(true)
        .oi(true)
        .ghiChu("Cat thanh 8 mieng")
        .build();
```

### 3.3. Vì sao Builder tốt hơn setter?

| Tiêu chí | Setter + no-arg constructor | Builder |
|----------|------------------------------|---------|
| Trạng thái "nửa vời" tồn tại? | ✗ Có — object được tạo trước, điền sau | ✅ Không — object chỉ tồn tại khi đủ dữ liệu |
| Validation tập trung? | ✗ Rải rác mỗi setter | ✅ Một chỗ trong `build()` |
| Immutable được? | ✗ Không (setter = mutable) | ✅ Có — tất cả field `final` |
| Thread-safe? | ✗ Phải lo đồng bộ | ✅ Nếu immutable |
| Đọc code gọi | `setX(...); setY(...)` — thứ tự tùy ý | Chuỗi method có tên rõ nghĩa |

**Tầng 3 — Vì sao immutable đáng giá?** Object không đổi trạng thái thì: chia sẻ giữa các thread an toàn tuyệt đối, dùng làm key Map/phần tử Set không lo hash thay đổi, và dễ suy luận (nhìn là biết giá trị — không cần tìm "ai set nó ở đâu").

**Khi nào vẫn dùng setter hợp lệ?**
- Framework yêu cầu (JPA entity, Jackson deserialize, JavaBean của thư viện cũ) — thỏa hiệp chấp nhận được.
- Object thật sự mutable theo nghiệp vụ, có phương thức nghiệp vụ bảo vệ invariant (như `napTien`).

## 4. Record + validation — lựa chọn hiện đại (Java 16+)

Khi object chỉ là dữ liệu, `record` + compact constructor thay thế phần lớn nhu cầu Builder:

```java
public record KhachHang(String ten, String email, int tuoi) {
    public KhachHang {
        if (ten == null || ten.isBlank()) throw new IllegalArgumentException("Ten rong");
        if (!email.contains("@"))         throw new IllegalArgumentException("Email sai");
        if (tuoi < 0 || tuoi > 150)       throw new IllegalArgumentException("Tuoi sai");
    }
}
```

Chỉ khi có **nhiều tham số tùy chọn** thì Builder mới hơn hẳn record.

## 5. Bài tập

1. Refactor `TaiKhoan` có 4 setter thành class immutable với constructor + `napTien`/`rutTien`. Chứng minh không thể tạo tài khoản số dư âm.
2. Viết Builder cho `TaiKhoan` khi có nhiều field tùy chọn (chi nhánh, loại TK, hạn mức...).
3. Viết record `SanPham` với validation, dùng nó làm key của `HashMap` — giải thích vì sao an toàn.
4. Tìm trong dự án của bạn class có > 5 setter; liệt kê invariant bị phá khả dĩ; chuyển sang constructor/Builder.

## 6. Tóm tắt

- Getter/setter là công cụ encapsulation cơ bản, nhưng **setter vô điều kiện = anti-pattern**: cho phép object ở trạng thái không hợp lệ.
- Ưu tiên: **constructor/record với validation** → object hợp lệ ngay từ khi sinh ra.
- Nhiều tham số tùy chọn → **Builder Pattern**: immutable, validation tập trung, code gọi dễ đọc.
- Getter trả collection/mảng → trả bản sao không đổi (`List.copyOf`, `clone()` như code mẫu).
- Setter chỉ chấp nhận khi framework bắt buộc hoặc khi object thật sự mutable có method nghiệp vụ bảo vệ.
