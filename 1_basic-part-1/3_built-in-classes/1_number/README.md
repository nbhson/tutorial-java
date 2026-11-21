# Lớp Numbers

Vậy khi nào kiểu nguyên thủy "không đủ tầm"? Mục này nói về các **lớp wrapper** — phiên bản "đối tượng hóa" của số, và lớp cha chung `Number` của chúng.

- Thông thường, khi làm việc với số, ta dùng **kiểu dữ liệu nguyên thủy** như `byte`, `int`, `long`, `double`...

> 📄 **Code ví dụ:** [`examples/01-lop-numbers.java`](examples/01-lop-numbers.java)

- Đôi khi, có những tình huống cần dùng **đối tượng** thay vì kiểu nguyên thủy (ví dụ: lưu vào collection, dùng generic). Để làm được điều đó, Java cung cấp các **lớp wrapper**.
- Tất cả các lớp wrapper (Integer, Long, Byte, Double, Float, Short) đều là con của lớp trừu tượng `Number`.

Ví von: primitive là **tiền lẻ trong túi** — dùng nhanh, gọn; wrapper là **tờ tiền bỏ vào ví da** — cồng kềnh hơn chút nhưng bỏ được vào các "hộc" yêu cầu ví (collection, generic). Cần bỏ vào ví thì phải có tờ, không thể nhét lỏng tiền xu.

## Lớp Number

> `Number` là một lớp trừu tượng trong gói `java.lang`. Nó là **lớp cha** của các lớp biểu diễn giá trị số có thể chuyển về kiểu nguyên thủy như `byte`, `short`, `int`, `long`, `float`, `double`.

`Number` đóng vai **tổ phụ họ**: mọi lớp wrapper số đều là hậu duệ, nên đảm bảo chung một bộ kỹ năng (các method bên dưới).

![alt text](image.png)

- Đối tượng wrapper **"bọc" (wrap)** giá trị kiểu nguyên thủy tương ứng. Việc chuyển kiểu nguyên thủy thành đối tượng gọi là **boxing**, compiler tự lo việc này.
- Do đó khi dùng wrapper, bạn chỉ cần truyền giá trị nguyên thủy vào constructor của lớp wrapper.
- Ngược lại, đối tượng wrapper được chuyển về kiểu nguyên thủy gọi là **unboxing**. Lớp `Number` thuộc gói `java.lang`.

> 📄 **Code ví dụ:** [`examples/02-lop-number.java`](examples/02-lop-number.java)

## Các phương thức của lớp Number

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 1 | `byteValue()` | Trả về giá trị của số dưới dạng `byte` |
| 2 | `doubleValue()` | Trả về giá trị của số dưới dạng `double` |
| 3 | `floatValue()` | Trả về giá trị của số dưới dạng `float` |
| 4 | `intValue()` | Trả về giá trị của số dưới dạng `int` |
| 5 | `longValue()` | Trả về giá trị của số dưới dạng `long` |
| 6 | `compareTo()` | So sánh đối tượng Number này với tham số |
| 7 | `equals()` | Xác định đối tượng số này có bằng tham số không |
| 8 | `valueOf()` | Trả về đối tượng Integer chứa giá trị của kiểu nguyên thủy chỉ định |
| 9 | `toString()` | Trả về chuỗi biểu diễn giá trị của số |
| 10 | `parseInt()` | Chuyển một chuỗi (String) thành kiểu nguyên thủy tương ứng |
| 11 | `min()` | Trả về giá trị nhỏ hơn trong hai tham số |
| 12 | `max()` | Trả về giá trị lớn hơn trong hai tham số |

> **Cách đọc bảng này:** chia bảng thành 3 nhóm theo cột Mô tả — nhóm `xxxValue()` (dòng 1-5) để **mở hộp** lấy giá trị nguyên thủy tương ứng, nhóm so sánh (compareTo, equals) để **kiểm tra**, nhóm còn lại là các tiện ích chuyển đổi và tính toán. Cần gì tra nhóm đó, không cần học thuộc.

## Các lớp Wrapper trong Java

| # | Lớp | Mô tả |
|---|-----|-------|
| 1 | `Boolean` | Bọc giá trị kiểu nguyên thủy `boolean` trong một đối tượng |
| 2 | `Byte` | Bọc giá trị kiểu `byte` trong một đối tượng |
| 3 | `Character` | Bọc giá trị kiểu `char` trong một đối tượng |
| 4 | `Double` | Bọc giá trị kiểu `double` trong một đối tượng |
| 5 | `Float` | Bọc giá trị kiểu `float` trong một đối tượng |
| 6 | `Integer` | Bọc giá trị kiểu `int` trong một đối tượng |
| 7 | `Long` | Bọc giá trị kiểu `long` trong một đối tượng |
| 8 | `Short` | Bọc giá trị kiểu `short` trong một đối tượng |

> Lưu ý: `Character` và `Boolean` không kế thừa từ `Number` (vì không phải kiểu số); các wrapper còn lại đều là con của `Number`.

> **Cách đọc bảng này:** mỗi dòng là một "tờ tiền" có sẵn trong ví da Java — tên lớp in hoa chính là bản wrapper của kiểu primitive tương ứng viết thường (`Integer` ↔ `int`, `Double` ↔ `double`). Riêng `Character` và `Boolean` là "ngoại lệ họ hàng".

> **Tóm gọn 1 câu:** Wrapper class là "tờ tiền bọc tiền lẻ" — bỏ được vào collection và generic, hộp bọc và mở hộp (boxing/unboxing) compiler tự lo cho bạn.
