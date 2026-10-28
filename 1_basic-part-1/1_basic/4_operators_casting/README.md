# Toán tử (Operators) và Ép kiểu (Casting)

> Toán tử là **động từ** của ngôn ngữ lập trình: cộng, so sánh, gán, logic. Ép kiểu là việc **chuyển đổi** dữ liệu giữa các kiểu — tự động (widening) hoặc thủ công (narrowing), và là nguồn bug kinh điển của người mới.

## Phần 1 — Toán tử

### 1.1. Toán tử số học

| Toán tử | Ý nghĩa | Ví dụ | Kết quả |
|---------|---------|-------|---------|
| `+` | Cộng | `10 + 3` | 13 |
| `-` | Trừ | `10 - 3` | 7 |
| `*` | Nhân | `10 * 3` | 30 |
| `/` | Chia | `10 / 3` | **3** (chia nguyên!) |
| `%` | Chia lấy dư | `10 % 3` | 1 |
| `++` | Tăng 1 | `i++`, `++i` | |
| `--` | Giảm 1 | `i--`, `--i` | |

```java
int a = 10, b = 3;

System.out.println(a / b);        // 3  — chia NGUYEN vi ca 2 la int
System.out.println(a % b);        // 1

// muon chia thuc: it nhat 1 toan hang la double
System.out.println(a / 3.0);        // 3.333...
System.out.println((double) a / b); // 3.333...
```

❌ **Pitfall kinh điển:** `(double)(a / b)` vẫn ra **3.0** — vì chia nguyên diễn ra **trước** khi ép kiểu. Phải ép kiểu **trước** phép chia: `(double) a / b`.

### 1.2. `++` và `--` — prefix vs postfix

```java
int i = 5;
System.out.println(i++);  // in 5 — LAY GIA TRI ROI tang (postfix)
System.out.println(i);    // 6

int j = 5;
System.out.println(++j);  // in 6 — TANG ROI lay gia tri (prefix)
```

**Vì sao nên cẩn thận?** Dùng `i++` bên trong biểu thức phức tạp (`arr[i++] = i`) là nguồn bug khó tìm. Quy tắc: chỉ dùng `++`/`--` **đứng một mình** trên một dòng.

### 1.3. Toán tử so sánh

| Toán tử | Ý nghĩa |
|---------|---------|
| `==` `!=` | Bằng / khác |
| `>` `<` `>=` `<=` | So sánh thứ tự |

```java
// ✗ loi kinh dien: so sanh chuoi bang ==
String s1 = "hello";
String s2 = new String("hello");
System.out.println(s1 == s2);        // false! so sanh THAM CHIEU
System.out.println(s1.equals(s2));   // true — so sanh NOI DUNG ✅

// voi primitive (int, long, double...) thi == la dung y nghia
// voi String, Integer, ... luon dung equals()
```

### 1.4. Toán tử logic — `&&`, `||`, `!` và short-circuit

```java
int age = 17;
boolean coTheDieuXe = age >= 18 && coGiayPhep; // && : ca hai dung moi dung

// SHORT-CIRCUIT: && dung ngay khi ve trai sai; || dung ngay khi ve trai dung
if (list != null && list.size() > 0) { }  // ✅ an toan — list null thi khong goi size()
if (list.size() > 0 && list != null) { }  // ✗ NPE khi list null — dung thu tu
```

**Vì sao short-circuit quan trọng?** Nó biến điều kiện thứ hai thành "chỉ chạy khi có ý nghĩa" — mô hình phòng thủ chuẩn của Java:

```java
// mau chuan: null check truoc, dung sau
if (user != null && user.isActive()) { ... }
```

### 1.5. Toán tử gán ghép

```java
int x = 10;
x += 5;   // x = x + 5 → 15
x -= 3;   // 12
x *= 2;   // 24
x /= 4;   // 6
x %= 4;   // 2

// luu y: toan tu gan ghep an trong ep kieu
byte b = 10;
b += 1;            // OK — ngam dinh ep ve byte
b = (byte) (b + 1); // vi b + 1 la int, gan vao byte phai ep kieu
```

### 1.6. Toán tử ba ngôi (ternary) và instanceof

```java
// dieu kien ? gia_tri_neu_dung : gia_tri_neu_sai
String ketQua = (diem >= 5) ? "DAT" : "TRUOT";

// instanceof — kiem tra kieu object
Object obj = "hello";
if (obj instanceof String s) {   // Java 16+: khai bao bien ngay tai cho
    System.out.println(s.length());
}
```

❌ **Pitfall:** lồng ternary nhiều tầng (`a ? b ? c : d : e`) — khó đọc như mật mã. Nếu lồng quá 1 tầng, dùng if-else hoặc switch.

## Phần 2 — Ép kiểu (Casting)

### 2.1. Hai chiều chuyển đổi

**Tầng 1 — Trực giác:**
- **Widening** (mở rộng): rót nước từ chai nhỏ sang chai lớn — **tự động, an toàn**.
- **Narrowing** (thu hẹp): rót chai lớn sang chai nhỏ — **phải chủ động, có thể tràn**.

```
byte → short → int → long → float → double
        (chieu widening — tu dong)
```

**Tầng 2 — Cú pháp và ví dụ:**

```java
// WIDENING — tu dong
int i = 100;
long l = i;        // ✅ tu dong int → long
double d = i;      // ✅ tu dong int → double (100.0)

// NARROWING — phai ep kieu ro rang
double pi = 3.99;
int n = (int) pi;  // 3 — MAT PHAN THAP PHAN (khong lam tron!)
long big = 5_000_000_000L;
int n2 = (int) big; // tran — ket qua sai vo dinh
```

> Ví dụ thực hành trong repo: `4_operator/src/casting/Main.java` và `5_casting/src/casting/Main.java` — demo widening (`int → double`) và narrowing (`(int) 9.78`) bằng chính code chạy được.

### 2.2. Ba cạm bẫy kinh điển

```java
// cam bay 1: chia nguyen truoc, ep kieu sau
int a = 10, b = 3;
System.out.println((double) (a / b)); // 3.0  — SAI y
System.out.println((double) a / b);   // 3.33 — DUNG

// cam bay 2: tran so khi narrow
int lon = 130;
byte be = (byte) lon;   // be = -126 — wrap-around, khong bao loi!

// cam bay 3: ep kieu object khac chuyen chuoi
String so = "123";
int x = (int) so;                    // ✗ khong bien dich duoc!
int y = Integer.parseInt(so);        // ✅ dung parse
double z = Double.parseDouble("3.14");
```

### 2.3. Ép kiểu với Object — và instanceof pattern

```java
Object obj = "Hello Java";

// ✗ cu: ep kieu truc tiep — neu sai kieu nem ClassCastException
String s = (String) obj;

// ✅ moi: kiem tra truoc (hoac dung pattern matching)
if (obj instanceof String str) {
    System.out.println(str.length()); // str da la String, khong can ep
}
```

### 2.4. Autoboxing — primitive và wrapper

```java
// primitive: int, long, double... | wrapper: Integer, Long, Double...
Integer a = 127;
Integer b = 127;
System.out.println(a == b);   // true  — cache cua Integer tu -128..127

Integer c = 128;
Integer d = 128;
System.out.println(c == d);      // false! — ngoai cache, la 2 object khac nhau
System.out.println(c.equals(d)); // true ✅

int e = c; // unboxing tu dong
```

❌ **Pitfall:** So sánh `Integer` bằng `==` — đúng với giá trị nhỏ, **sai** với giá trị lớn. Luôn dùng `equals()` với wrapper class.

### 2.5. Kiểu `char` — nằm giữa số và chữ

```java
char kitu = 'A';
int ma = kitu;               // 65 — widening tu dong
char k2 = (char) (kitu + 1); // 'B'

// chu → so va nguoc lai
int chuSo = Character.getNumericValue('7'); // 7
char c = (char) ('0' + 5);                  // '5'
```

## Phần 3 — Độ ưu tiên toán tử (tóm tắt)

Từ cao xuống thấp:

1. `()` ngoặc — luôn dùng ngoặc khi nghi ngờ
2. `++ -- !` đơn ngôi
3. `* / %`
4. `+ -`
5. So sánh `< > <= >=`
6. So sánh bằng `== !=`
7. `&&`
8. `||`
9. `?:` ternary
10. `= += -= ...` gán

❌ **Pitfall:** `a + b * c` không bằng `(a + b) * c`. Đừng học thuộc bảng ưu tiên — **dùng ngoặc** để diễn đạt rõ ý, code tự giải thích được.

## Bài tập

1. Cho `int a = 7, b = 2;` — đoán kết quả của `a / b`, `a % b`, `(double) a / b`, `(double) (a / b)`, rồi chạy kiểm chứng.
2. Viết chương trình nhập số giây, đổi ra giờ:phút:giây (dùng `/` và `%`).
3. Viết logic kiểm tra "số nguyên tố" dùng `&&`/`||` — chú ý short-circuit giúp tránh chia cho 0.
4. Vì sao `(int) 3.99` = 3 mà không phải 4? Viết hàm làm tròn đúng bằng `Math.round`.
5. Viết method `static double trungBinh(int[] diem)` — chú ý cách tránh chia nguyên.
6. Với `Integer x = 1000, y = 1000;` dự đoán `x == y` và `x.equals(y)`, giải thích cơ chế cache.

## Tóm tắt

- `/` với 2 int là **chia nguyên** — muốn chia thực, ép kiểu trước phép chia.
- `==` so sánh tham chiếu với object; nội dung dùng `equals()` (String, Integer...).
- Short-circuit (`&&`, `||`) là công cụ phòng thủ: null-check trước khi dùng.
- Widening tự động; narrowing phải ép kiểu tường minh và có thể mất dữ liệu/tràn số.
- Wrapper class so sánh bằng `equals()`; chuỗi → số dùng `parse`.
- Dùng ngoặc thay vì ghi nhớ bảng độ ưu tiên.
