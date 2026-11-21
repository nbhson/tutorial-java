# Toán tử (Operators) và Ép kiểu (Casting)

> Toán tử là **động từ** của ngôn ngữ lập trình: cộng, so sánh, gán, logic. Ép kiểu là việc **chuyển đổi** dữ liệu giữa các kiểu — tự động (widening) hoặc thủ công (narrowing), và là nguồn bug kinh điển của người mới.

## Phần 1 — Toán tử

Mục này trang bị "động từ" cho code của bạn: các phép tính, so sánh, logic. Đọc để viết biểu thức đúng và né được mấy cái bẫy kinh điển như chia nguyên hay fallthrough của `++`.

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

> **Cách đọc bảng này:** mỗi dòng là một "động từ" phép tính; dừng lâu nhất ở dòng `/` — với hai số nguyên, Java **cắt bỏ phần thập phân** thay vì làm tròn, như chia 10 viên kẹo cho 3 đứa: mỗi đứa 3 viên, phần dư nằm ở `%`.

> 📄 **Code ví dụ:** [`examples/01-1-1-toan-tu-so-hoc.java`](examples/01-1-1-toan-tu-so-hoc.java)

❌ **Pitfall kinh điển:** `(double)(a / b)` vẫn ra **3.0** — vì chia nguyên diễn ra **trước** khi ép kiểu. Phải ép kiểu **trước** phép chia: `(double) a / b`.

> **Tóm gọn 1 câu:** Toán tử số học quen mặt nhưng `/` với số nguyên là "chia kẹo cho đứa trẻ" — phần thập phân bị bỏ, không làm tròn.

### 1.2. `++` và `--` — prefix vs postfix

> 📄 **Code ví dụ:** [`examples/02-1-2-va-prefix-vs-postfix.java`](examples/02-1-2-va-prefix-vs-postfix.java)

**Vì sao nên cẩn thận?** Dùng `i++` bên trong biểu thức phức tạp (`arr[i++] = i`) là nguồn bug khó tìm. Quy tắc: chỉ dùng `++`/`--` **đứng một mình** trên một dòng.

### 1.3. Toán tử so sánh

| Toán tử | Ý nghĩa |
|---------|---------|
| `==` `!=` | Bằng / khác |
| `>` `<` `>=` `<=` | So sánh thứ tự |

> 📄 **Code ví dụ:** [`examples/03-1-3-toan-tu-so-sanh.java`](examples/03-1-3-toan-tu-so-sanh.java)

### 1.4. Toán tử logic — `&&`, `||`, `!` và short-circuit

> 📄 **Code ví dụ:** [`examples/04-1-4-toan-tu-logic-va-short-circuit.java`](examples/04-1-4-toan-tu-logic-va-short-circuit.java)

**Vì sao short-circuit quan trọng?** Nó biến điều kiện thứ hai thành "chỉ chạy khi có ý nghĩa" — mô hình phòng thủ chuẩn của Java:

> 📄 **Code ví dụ:** [`examples/05-1-4-toan-tu-logic-va-short-circuit.java`](examples/05-1-4-toan-tu-logic-va-short-circuit.java)

### 1.5. Toán tử gán ghép

> 📄 **Code ví dụ:** [`examples/06-1-5-toan-tu-gan-ghep.java`](examples/06-1-5-toan-tu-gan-ghep.java)

### 1.6. Toán tử ba ngôi (ternary) và instanceof

> 📄 **Code ví dụ:** [`examples/07-1-6-toan-tu-ba-ngoi-ternary-va.java`](examples/07-1-6-toan-tu-ba-ngoi-ternary-va.java)

❌ **Pitfall:** lồng ternary nhiều tầng (`a ? b ? c : d : e`) — khó đọc như mật mã. Nếu lồng quá 1 tầng, dùng if-else hoặc switch.

> **Tóm gọn 1 câu:** Phần 1 cho bạn bộ "động từ" đầy đủ — nhớ 2 điều vàng: chia nguyên cắt thập phân, và short-circuit giúp bạn chặn lỗi trước khi xảy ra.

## Phần 2 — Ép kiểu (Casting)

Mục này nói về việc "đổi thùng" cho dữ liệu: chuyển giá trị giữa các kiểu to nhỏ khác nhau. Đọc để biết chiều nào tự động, chiều nào phải tự chịu trách nhiệm — và mất dữ liệu ở đâu.

### 2.1. Hai chiều chuyển đổi

**Tầng 1 — Trực giác:**
- **Widening** (mở rộng): rót nước từ chai nhỏ sang chai lớn — **tự động, an toàn**.
- **Narrowing** (thu hẹp): rót chai lớn sang chai nhỏ — **phải chủ động, có thể tràn**.

```
byte → short → int → long → float → double
        (chieu widening — tu dong)
```

**Tầng 2 — Cú pháp và ví dụ:**

> 📄 **Code ví dụ:** [`examples/08-2-1-hai-chieu-chuyen-oi.java`](examples/08-2-1-hai-chieu-chuyen-oi.java)

> Ví dụ thực hành trong repo: `4_operator/src/casting/Main.java` và `5_casting/src/casting/Main.java` — demo widening (`int → double`) và narrowing (`(int) 9.78`) bằng chính code chạy được.

### 2.2. Ba cạm bẫy kinh điển

> 📄 **Code ví dụ:** [`examples/09-2-2-ba-cam-bay-kinh-ien.java`](examples/09-2-2-ba-cam-bay-kinh-ien.java)

### 2.3. Ép kiểu với Object — và instanceof pattern

> 📄 **Code ví dụ:** [`examples/10-2-3-ep-kieu-voi-object-va-instanceof.java`](examples/10-2-3-ep-kieu-voi-object-va-instanceof.java)

### 2.4. Autoboxing — primitive và wrapper

> 📄 **Code ví dụ:** [`examples/11-2-4-autoboxing-primitive-va-wrapper.java`](examples/11-2-4-autoboxing-primitive-va-wrapper.java)

❌ **Pitfall:** So sánh `Integer` bằng `==` — đúng với giá trị nhỏ, **sai** với giá trị lớn. Luôn dùng `equals()` với wrapper class.

> **Tóm gọn 1 câu:** Ép kiểu là "rót nước giữa các thùng" — rót sang thùng to thì tự động, rót sang thùng nhỏ thì tự tay và đỡ tràn.

### 2.5. Kiểu `char` — nằm giữa số và chữ

> 📄 **Code ví dụ:** [`examples/12-2-5-kieu-char-nam-giua-so-va.java`](examples/12-2-5-kieu-char-nam-giua-so-va.java)

## Phần 3 — Độ ưu tiên toán tử (tóm tắt)

Mục này xếp thứ tự "ai lên tiếng trước" khi nhiều toán tử đứng chung một biểu thức. Đọc một lượt để có trực giác — nhưng lời khuyên thật sự nằm ở pitfall cuối mục.

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

> **Tóm gọn 1 câu:** Độ ưu tiên toán tử như luật giao thông — biết là tốt, nhưng gắn "biển báo ngoặc" thì ai đọc code cũng hiểu ngay.

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

> **Tóm gọn 1 câu:** Toán tử là động từ, ép kiểu là đổi thùng — hai kỹ năng nhỏ nhưng quyết định code của bạn chạy đúng hay "im lặng trả kết quả sai".
