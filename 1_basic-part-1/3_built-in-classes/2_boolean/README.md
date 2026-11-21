# Lớp Boolean

Vậy khi cần số boolean "khoác áo đối tượng" — để lưu vào collection hay generic — thì dùng gì? Lớp `Boolean` là câu trả lời. Mục này nói về cách tạo và các method của nó.

- Lớp `Boolean` trong Java bọc giá trị kiểu nguyên thủy `boolean` trong một đối tượng. Một đối tượng `Boolean` chứa duy nhất một field kiểu `boolean`.

Ví von: nếu `boolean` là **công tắc điện** (chỉ bật/tắt), thì `Boolean` là **công tắc được đóng vỏ hộp đẹp** — vẫn chỉ hai trạng thái, nhưng có thể để vào kệ (collection), dán nhãn (generic), chuyển qua lại giữa các tầng API.

## Các constructor của lớp Boolean

| # | Constructor | Mô tả |
|---|-------------|-------|
| 1 | `Boolean(boolean value)` | Tạo đối tượng `Boolean` biểu diễn giá trị `value` |
| 2 | `Boolean(String s)` | Tạo đối tượng `Boolean` mang giá trị `true` nếu chuỗi không phải `null` và bằng `"true"` (không phân biệt hoa thường); ngược lại là `false` |

Dòng 2 đáng chú ý nhất: mọi chuỗi khác `"true"` — kể cả `"TRUE  "`, `"yes"`, hay chuỗi rác — đều cho `false` âm thầm, không báo lỗi. Giống như **máy đọc chỉ nhận đúng mật khẩu "true"**, gõ sai phát nào cũng bị từ chối mà không giải thích.

## Các phương thức của lớp Boolean

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 1 | `boolean booleanValue()` | Trả về giá trị của đối tượng `Boolean` dưới dạng kiểu nguyên thủy `boolean` |
| 2 | `int compareTo(Boolean b)` | So sánh thể hiện `Boolean` này với thể hiện khác |
| 3 | `boolean equals(Object obj)` | Trả về `true` khi và chỉ khi tham số khác `null` và là đối tượng `Boolean` mang cùng giá trị |
| 4 | `static boolean getBoolean(String name)` | Trả về `true` khi và chỉ khi system property có tên tương ứng tồn tại và bằng chuỗi `"true"` |
| 5 | `int hashCode()` | Trả về mã băm của đối tượng `Boolean` này |
| 6 | `int hashCode(boolean value)` | Trả về mã băm cho một giá trị `boolean` cụ thể — tương thích với `Boolean.hashCode()` |
| 7 | `static boolean logicalAnd(boolean a, boolean b)` | Trả về kết quả của phép AND logic trên hai toán hạng |
| 8 | `static boolean logicalOr(boolean a, boolean b)` | Trả về kết quả của phép OR logic trên hai toán hạng |
| 9 | `static boolean logicalXor(boolean a, boolean b)` | Trả về kết quả của phép XOR logic trên hai toán hạng |
| 10 | `static boolean parseBoolean(String s)` | Phân tích chuỗi thành `boolean` |
| 11 | `String toString()` | Trả về chuỗi biểu diễn giá trị của đối tượng `Boolean` này |
| 12 | `static String toString(boolean b)` | Trả về chuỗi biểu diễn giá trị `boolean` chỉ định |
| 13 | `static Boolean valueOf(boolean b)` | Trả về thể hiện `Boolean` biểu diễn giá trị `boolean` chỉ định |
| 14 | `static Boolean valueOf(String s)` | Trả về `Boolean` có giá trị biểu diễn bởi chuỗi chỉ định |

> **Cách đọc bảng này:** chia theo nhóm — dòng 1-2 là **mở hộp** (lấy boolean gốc ra), dòng 3-5 so sánh và băm, dòng 7-9 là ba phép logic `AND/OR/XOR` dạng tiện ích, dòng 10-14 là các **đóng hộp** từ chuỗi hoặc giá trị. Cần thao tác gì, tra đúng nhóm đó.

## Ví dụ

> 📄 **Code ví dụ:** [`examples/01-vi-du.java`](examples/01-vi-du.java)

Kết quả: vì `true` so sánh với `false` cho giá trị dương, chương trình in ra `Object value is true`.

> **Tóm gọn 1 câu:** `Boolean` là "công tắc đóng hộp" — bản chất vẫn true/false, nhưng mang được vào collection, generic và kèm theo bộ tiện ích parse/compare/logic.
