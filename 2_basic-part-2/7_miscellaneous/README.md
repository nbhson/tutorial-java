# Miscellaneous (Java 21+ — Records, Sealed, Pattern Matching)

> Java đang thay đổi nhanh hơn bao giờ hết. Bài này tổng hợp các tính năng hiện đại bạn nên dùng ngay: **record**, **sealed class/interface**, **pattern matching** cho switch và instanceof, cùng vài chi tiết nhỏ khác. Chúng giúp code ngắn hơn, an toàn hơn, dễ đọc hơn.

## 1. Record — immutable data carrier

### 1.1. Vấn đề trước record

```java
// ✗ Pojo day du: hon 70 dong chi de luu 3 truong
public class Person {
    private final String name;
    private final int age;
    private final String email;

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName()  { return name; }
    public int    getAge()   { return age; }
    public String getEmail() { return email; }

    @Override public boolean equals(Object o) { /* ... */ }
    @Override public int hashCode() { /* ... */ }
    @Override public String toString() { /* ... */ }
}
```

### 1.2. Record — 1 dòng thay thế 70 dòng

```java
// ✅ mot dong thay the 70 dong
public record Person(String name, int age, String email) {}

// su dung
Person p = new Person("An", 25, "an@mail.com");
System.out.println(p.name());  // getter ten truong: name(), KHONG phai getName()
System.out.println(p);         // Person[name=An, age=25, email=an@mail.com]
```

**Trình biên dịch tự động tạo:**

1. Các trường `private final` cho mỗi tham số.
2. Constructor nhận đủ tham số (canonical constructor).
3. Accessor: `name()`, `age()`, `email()` (không có prefix `get`).
4. `equals()` / `hashCode()` / `toString()` — dựa trên tất cả các trường.

### 1.3. Record với validation — compact constructor

```java
// kiem tra du lieu ngay trong constructor (compact form)
public record AgeRange(int minAge, int maxAge) {
    public AgeRange {
        if (minAge > maxAge) {
            throw new IllegalArgumentException(
                    "minAge (" + minAge + ") > maxAge (" + maxAge + ")");
        }
        // khong can gan this.minAge = minAge — tu dong lam
    }
}
```

### 1.4. Record có phương thức và field tĩnh

```java
public record Rect(double width, double height) {
    // field static duoc phep
    public static Rect square(double side) {
        return new Rect(side, side);
    }

    // method instance duoc phep
    public double area() {
        return width * height;
    }
}

Rect r = Rect.square(5);
System.out.println(r.area()); // 25.0
```

### 1.5. Giới hạn của record — phải hiểu rõ

❌ **Record KHÔNG cho phép:**

```java
// 1. khong the extends lop khac (record da ngam dinh extends java.lang.Record)
public record A(int x) extends Base {} // ✗ loi bien dich

// 2. field instance phai khai bao trong header — khong duoc them field khac
public record B(int x) {
    private int y; // ✗ loi
}

// 3. record la IMMUTABLE — khong co setter
Person p = new Person("An", 25, "an@mail.com");
p.setAge(26); // ✗ khong ton tai
```

✅ **Vì sao thiết kế như vậy?** Record đại diện cho **dữ liệu thuần túy bất biến** (data carrier). Immutability mang lại 3 lợi ích: thread-safe miễn phí, dùng được làm key Map/phần tử Set an toàn, dễ suy luận trạng thái. Đó cũng là lý do record dùng tốt cho DTO, event, message, kết quả query.

## 2. Sealed classes — giới hạn cây kế thừa

### 2.1. Vấn đề: kế thừa mở quá mức

```java
// ✗ ai cung co the extends Shape — khong the kiem soat
public interface Shape { double area(); }

public class Circle implements Shape { /* ... */ }
// ... va ben duoi, mot ai do viet:
public class TriangleThatBreaksEverything implements Shape { /* ... */ }
```

**Hậu quả:** code xử lý Shape phải phỏng đoán "còn những loại nào nữa?" — không thể đảm bảo xử lý hết mọi case.

### 2.2. Sealed — khai báo "chỉ những con này mới được"

```java
// chi 3 class duoc phep implements Shape
public sealed interface Shape permits Circle, Rectangle, Triangle {}

// bat buoc: class con phai la final, sealed, hoac non-sealed
public record Circle(double radius) implements Shape {
    public double area() { return Math.PI * radius * radius; }
}

public record Rectangle(double width, double height) implements Shape {
    public double area() { return width * height; }
}

public non-sealed class Triangle implements Shape {  // mo cua lai cho ai cung extends
    public double area() { return 0; } // demo
}
```

**Quy tắc bắt buộc:**

1. `permits` liệt kê tường minh (hoặc nếu tất cả nằm cùng file thì có thể bỏ).
2. Mỗi subclass phải khai báo một trong 3 modifier:
   - `final` — chặn tiếp tục kế thừa.
   - `sealed` — giới hạn tiếp tầng kế.
   - `non-sealed` — mở lại cho công chúng.

### 2.3. Vì sao sealed đáng giá?

**Tầng 3:** Sealed + pattern matching switch tạo ra **exhaustiveness checking** — compiler ép bạn xử lý **tất cả** các trường hợp:

```java
static double area(Shape shape) {
    // khong can default! compiler BAO LOI neu thieu mot case
    return switch (shape) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.width() * r.height();
        case Triangle t  -> 0;
    };
}

// sau nay ban them Square vao Shape:
// → khong sua switch nay → LOI BIEN DICH ngay lap tuc, khong phai bug nua dem
```

Đây chính là mô hình **algebraic data type (ADT)** của các ngôn ngữ hàm, giờ đã có trong Java. Dùng nó khi: domain có số loại **hạn chế và ổn định** (loại thanh toán, trạng thái đơn hàng, node của cây cú pháp).

❌ **Pitfall:** Dùng sealed cho domain mở (loại plugin, listener)... bạn sẽ phải sửa file sealed mỗi lần thêm plugin — mất tính mở rộng.

## 3. Pattern matching cho instanceof (Java 16+)

```java
// ✗ cu: kiem tra roi ep kieu 2 lan
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}

// ✅ moi: pattern matching — khai bao bien ngay trong instanceof
if (obj instanceof String s && s.length() > 0) {
    System.out.println(s.length()); // s da duoc "binding" kieu String
}

// ket hop voi sealed + record
static String moTa(Object obj) {
    if (obj instanceof Integer i) return "so nguyen: " + i;
    if (obj instanceof String s)  return "chuoi " + s.length() + " ky tu";
    if (obj instanceof Person p)  return "nguoi: " + p.name();
    return "chua biet";
}
```

## 4. Pattern matching cho switch (Java 21)

### 4.1. Switch cổ điển vs hiện đại

```java
// ✗ cu: nhieu break, fallthrough nguy hiem
static String dienGiaiC1(Object obj) {
    switch (obj) {
        case Integer i: return "so " + i;
        case String s:   return "chuoi " + s;
        case null:       return "null";
        default:         return "khong ro";
    }
}

// ✅ java 21+: switch expression, arrow, exhaustiveness
static String dienGiaiC2(Object obj) {
    return switch (obj) {
        case Integer i -> "so nguyen: " + i;
        case String s  -> "chuoi do dai " + s.length();
        case null      -> "la null";
        default        -> "loai khac: " + obj.getClass().getSimpleName();
    };
}
```

### 4.2. Pattern matching trong switch label

```java
// khai bao bien ngay trong nhan switch
static String phanLoai(Object obj) {
    return switch (obj) {
        case Integer i when i > 0 -> "so duong: " + i;
        case Integer i            -> "so am hoac 0: " + i;
        case String s when !s.isBlank() -> "chuoi hop le: " + s.length();
        case String s            -> "chuoi rong";
        case int[] arr           -> "mang " + arr.length + " phan tu";
        case null                -> "null";
        default                  -> "khong ro";
    };
}

// ket hop voi record
record DoiTuong(String ten, int tuoi) {}

static String moTa(Object obj) {
    return switch (obj) {
        case Circle c          -> "hinh tron r = " + c.radius();
        case Rectangle r      -> "hinh chu nhat " + r.width() + "x" + r.height();
        case Triangle t       -> "tam giac";
        case null, default    -> "khong xac dinh";
    };
}
```

### 4.3. Sealed + exhaustiveness = an toàn tuyệt đối

```java
public sealed interface Expr permits NumberExpr, VarExpr, OpExpr {}
public record NumberExpr(int val)  implements Expr {}
public record VarExpr(String name) implements Expr {}
public sealed interface OpExpr extends Expr permits AddExpr, MulExpr {}
public record AddExpr(Expr left, Expr right) implements OpExpr {}
public record MulExpr(Expr left, Expr right) implements OpExpr {}

static String eval(Expr e) {
    return switch (e) {           // compile SE KHONG CHO phep thieu case nao
        case NumberExpr n -> String.valueOf(n.val());
        case VarExpr v    -> v.name();
        case AddExpr a    -> eval(a.left()) + " + " + eval(a.right());
        case MulExpr m    -> eval(m.left()) + " * " + eval(m.right());
    };
}
```

## 5. Text Block (Java 15+)

```java
// ✗ cu: xau dai, nhieu \n va \"
String json = "{\n" +
              "  \"name\": \"An\",\n" +
              "  \"age\": 25\n" +
              "}";

// ✅ moi: text block — giong y chang ghi trong source code
String json2 = """
        {
          "name": "An",
          "age": 25
        }
        """;

// ky tu dac biet:
// \""" → dau ngoac kep literral
// \n  → xuong dong
// \\  → dau \
// trim: stripTrailingIndent() neu can
```

Lý tưởng cho: SQL query, JSON, HTML, Regex, code mẫu trong test.

## 6. Switch expression (Java 14+ cũng cần)

```java
// switch la expression, tra ve gia tri
static int soNgay(int thang, int nam) {
    return switch (thang) {
        case 1, 3, 5, 7, 8, 10, 12 -> 31;
        case 4, 6, 9, 11           -> 30;
        case 2                     -> (nam % 4 == 0 && nam % 100 != 0) || (nam % 400 == 0)
                                         ? 29 : 28;
        default                    -> -1;
    };
}
```

## 7. Bài tập

1. Viết record `GiaoDich(String ma, double soTien, LocalDate ngay, String loai)`. Thêm validation: `soTien > 0`. Viết static factory method `giaoDichPhatSinh(Scanner)`.
2. Viết sealed interface `HinhHoc` với `HinhTron`, `HinhChuNhat`, `HinhVuong`. Dùng pattern matching switch (Java 21) để tính diện tích, đảm bảo exhaustiveness.
3. Viết `String moTa(Object obj)` dùng `instanceof` pattern matching (Java 16+) — không dùng `if/else` lồng nhau.
4. Tạo DSL JSON đơn giản dùng text block + record: mỗi record có `toJson()` trả xâu JSON hợp lệ.
5. Viết AST (cây cú pháp trừu tượng) cho biểu thức số học: `Expr` sealed, các subclass record, viết hàm `evaluate` dùng switch expression exhaustiveness.
6. So sánh `equals()` của 2 record cùng giá trị — chúng bằng nhau không? Tại sao? Minh họa dùng `HashMap<PersonRecord, String>` và so sánh với POJO.

## 8. Tóm tắt

- **record** = immutable data carrier tự sinh getter/equals/hashCode/toString; dùng cho DTO, event, message; thêm validation qua compact constructor.
- **sealed** = giới hạn đúng số loại kế thừa; kết hợp với switch/pattern matching tạo exhaustiveness checking.
- **pattern matching instanceof** (Java 16+) = khai báo biến ngay trong `instanceof`, giảm 50% code kiểm tra kiểu.
- **pattern matching switch** (Java 21) = switch expression với nhãn dạng `case T t -> expr`, hỗ trợ `when` guard và record destructuring.
- **text block** = chuỗi nhiều dòng không cần `\n` và `\`"`; dùng cho SQL, JSON, HTML.
- Tất cả 4 tính năng này hướng tới mục tiêu: **immutability by default**, **compile-time exhaustiveness**, **less boilerplate**.
