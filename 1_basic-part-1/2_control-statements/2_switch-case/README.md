# Switch — từ cổ điển đến Pattern Matching (Java 21+)

> `switch` cho phép so sánh một giá trị với danh sách các case. Java hiện đại đã biến switch từ "dãy if-else có break" thành **switch expression** gọn gàng và thậm chí **match theo kiểu dữ liệu**.

## 1. Switch cổ điển

**Tầng 1 — Trực giác:** `if-else` là **chuỗi câu hỏi có/không** ("nếu A thì... nếu B thì..."). `switch` là **bảng tra cứu**: đưa giá trị vào, chọn đúng ô tương ứng.

```java
int thu = 3;
switch (thu) {
    case 1:
        System.out.println("Thu Hai");
        break;                       // bat buoc! khong co break → rơi xuống case sau
    case 2:
        System.out.println("Ba");
        break;
    case 6:
    case 7:                          // nhom case — khong co break o day la Y DING
        System.out.println("Cuoi tuan");
        break;
    default:                         // khong khop case nao
        System.out.println("Ngay trong tuan");
        break;
}
```

❌ **Pitfall — quên `break`:** code **rơi xuyên** (fallthrough) xuống case sau:

```java
int diem = 5;
switch (diem) {
    case 5:
        System.out.println("Trung binh");
        // thieu break → chay tiep xuong case 6!
    case 6:
        System.out.println("Kha");   // se in voi diem = 5!
        break;
}
```

Đây là nguồn bug kinh điển — chính vì vậy Java 14 đã ra **switch expression**.

## 2. Switch expression (Java 14+) — arrow `->`

```java
// switch tra ve gia tri, khong can break, khong so rơi xu
String tenThu = switch (thu) {
    case 1 -> "Thu Hai";
    case 2 -> "Ba";
    case 6, 7 -> "Cuoi tuan";        // nhieu case mot dong
    default -> "Ngay trong tuan";
};

// co the co khoi lenh + yield de tra ve gia tri
int soNgay = switch (thang) {
    case 1, 3, 5, 7, 8, 10, 12 -> 31;
    case 4, 6, 9, 11 -> 30;
    case 2 -> {
        int namNhuan = (nam % 4 == 0 && nam % 100 != 0) || nam % 400 == 0 ? 1 : 0;
        yield 28 + namNhuan;         // yield = tra ve gia tri trong khoi
    }
    default -> -1;
};
```

**Vì sao arrow `->` an toàn hơn?** Không có fallthrough, và compiler **bắt buộc** mọi case phải được xử lý (exhaustiveness) — thiếu case là lỗi biên dịch.

## 3. Pattern matching switch (Java 21+) — switch theo KIỂU

Trước Java 21, `switch` chỉ so sánh giá trị bằng. Java 21 cho phép switch theo **kiểu dữ liệu** — thay thế chuỗi `if instanceof` dài:

```java
// ✗ cu: chuoi if-instanceof loi nhoi
static String moTa(Object obj) {
    if (obj instanceof Integer i) {
        return "so nguyen " + i;
    } else if (obj instanceof String s) {
        return "chuoi " + s;
    } else if (obj == null) {
        return "null";
    }
    return "khong ro";
}

// ✅ java 21: switch pattern matching — gon va day du (exhaustive)
static String moTa(Object obj) {
    return switch (obj) {
        case Integer i when i > 0 -> "so nguyen duong: " + i;  // when = dieu kien bo sung
        case Integer i            -> "so nguyen: " + i;
        case String s when s.isEmpty() -> "chuoi rong";
        case String s             -> "chuoi: " + s;
        case null                 -> "la null";                // xu ly null trong switch!
        default                   -> "khong ro";
    };
}
```

**Điểm mới:**

- `case Integer i` — vừa kiểm tra kiểu vừa **binding biến** `i`.
- `when` — điều kiện bổ sung (guard) tinh chỉnh case.
- `case null` — cuối cùng switch xử lý được null mà không ném NPE.
- Compiler kiểm tra **exhaustive**: nếu bạn switch trên `Object` mà không có `default`, code không biên dịch được.

## 4. Pattern matching + Record destructuring

```java
record Diem(int x, int y) {}
record Circle(Diem tam, double r) {}

static double dienTich(Object shape) {
    return switch (shape) {
        case Circle(var t, double r) -> Math.PI * r * r;  // destructure record ngay trong case
        case Diem d                  -> 0;
        default                      -> throw new IllegalArgumentException();
    };
}
```

Kết hợp với **sealed interface** → compiler kiểm tra đủ mọi nhánh (xem chi tiết ở bài 2_basic-part-2/7_miscellaneous).

## 5. switch trên String và enum

```java
// String — Java 7+
switch (lenh) {
    case "start": batDau(); break;
    case "stop":  dungLai(); break;
    default:      System.out.println("Lenh khong hop le");
}

// enum — pho bien nhat
enum HinhThuc { TUAN_TRA, THANG, NAM }

double tienTietKiem(double tien, HinhThuc kyHan) {
    return switch (kyHan) {
        case TUAN_TRA -> tien * 0.001;
        case THANG -> tien * 0.05;
        case NAM -> tien * 0.06;
    }; // khong can default — enum da du case
}
```

## 6. ❌/✅ Pitfalls tổng hợp

```java
// ✗ quen break trong switch cu → fallthrough
// ✗ switch Object khong xu ly null → NullPointerException (Java 21: dung case null)
// ✗ dung switch khi dieu kien la pham vi (if x > 100) — if phu hop hon

// ✅ switch expression mac dinh khi can "map gia tri → ket qua"
// ✅ su dung enum + switch cho logic phan loai — compiler bao khi thieu case
// ✅ pattern matching thay cho chuoi if-instanceof dai dong
```

## 6. Bài tập

1. Viết switch expression chuyển tháng (1–12) thành mùa; thiếu case → compiler báo lỗi, quan sát.
2. Viết `static String dienGiai(Object o)` dùng pattern matching switch với `when`: số dương/âm, chuỗi rỗng/không rỗng, null, còn lại.
3. Cho `record GiaoDich(String loai, double tien)`; viết switch tính phí giao dịch theo loại (chuyển tiền: 0.1%, rút tiền: 5.000đ, chuyển khác...).
4. So sánh chuỗi if-else vs switch expression cho bài toán tính điểm học lực (A/B/C/D/F) — bản nào dễ đọc hơn, vì sao?

## 7. Tóm tắt

- Switch cổ điển: dùng `break` đúng, cẩn thận fallthrough.
- **Switch expression** (Java 14+): arrow `->`, gọn, trả giá trị, không rơi xuyên.
- **Pattern matching switch** (Java 21+): switch theo kiểu, `when` guard, `case null`, record destructuring, exhaustiveness kiểm tra lúc compile.
- Dùng switch khi ánh xạ giá trị/kiểu → kết quả; dùng if-else khi điều kiện là khoảng giá trị phức tạp.
