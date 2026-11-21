# Các Exception có sẵn trong Java (Built-in Exceptions)

## Exception trong Java là gì?

Mục nền ôn nhanh: exception là gì và nó đến từ đâu, trước khi lướt qua "danh bạ" các exception Java đã dựng sẵn.

`Exception` (ngoại lệ) là một sự cố phát sinh trong quá trình chạy chương trình. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn, chương trình có thể kết thúc bất thường — vì vậy bạn cần xử lý chúng.

Lý do exception xảy ra có thể là: người dùng nhập dữ liệu không hợp lệ, file cần mở không tồn tại, kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

> Có exception do lỗi người dùng, có exception do lỗi lập trình viên, và có exception do tài nguyên vật lý gặp sự cố.

## Các exception có sẵn phổ biến trong Java

Mục này là "cuốn danh bạ sự cố" của Java: bạn không cần học thuộc từng dòng, chỉ cần nhớ vài cái tên xuyên suốt (`NullPointerException`, `ArithmeticException`, `IOException`...) và biết quay lại tra khi gặp.

Java đã cung cấp sẵn một loạt exception trong gói `java.lang`. Dưới đây là những exception thường gặp nhất mà bạn cần nhớ:

### Unchecked exceptions (runtime)

| # | Exception | Ý nghĩa |
|---|-----------|---------|
| 1 | `ArithmeticException` | Lỗi số học, ví dụ chia cho 0 |
| 2 | `ArrayIndexOutOfBoundsException` | Chỉ số mảng vượt quá giới hạn |
| 3 | `ArrayStoreException` | Gán phần tử mảng với kiểu không tương thích |
| 4 | `ClassCastException` | Ép kiểu không hợp lệ |
| 5 | `IllegalArgumentException` | Đối số không hợp lệ được dùng để gọi một phương thức |
| 6 | `IllegalMonitorStateException` | Thao tác monitor không hợp lệ, ví dụ `wait()` trên thread chưa khóa |
| 7 | `IllegalStateException` | Môi trường hoặc ứng dụng đang ở **trạng thái không hợp lệ** |
| 8 | `IllegalThreadStateException` | Thao tác yêu cầu không tương thích với trạng thái hiện tại của thread |
| 9 | `IndexOutOfBoundsException` | Chỉ số (index) nào đó vượt quá giới hạn |
| 10 | `NegativeArraySizeException` | Tạo mảng với kích thước âm |
| 11 | `NullPointerException` | Dùng tham chiếu `null` một cách không hợp lệ |
| 12 | `NumberFormatException` | Chuyển chuỗi sang dạng số không hợp lệ |
| 13 | `SecurityException` | Cố gắng vi phạm bảo mật |
| 14 | `StringIndexOutOfBoundsException` | Truy cập chỉ số ngoài phạm vi của chuỗi |
| 15 | `UnsupportedOperationException` | Gặp một thao tác không được hỗ trợ |

> **Cách đọc bảng này:** đây là bảng tra cứu, không phải bảng học thuộc — khi runtime báo tên exception nào, quay lại tìm đúng dòng đó để biết nó nghĩa là gì. Cột **Ý nghĩa** đọc như "câu bệnh án": mô tả tình huống nào sinh ra exception này.

### Checked exceptions

| # | Exception | Ý nghĩa |
|---|-----------|---------|
| 16 | `ClassNotFoundException` | Không tìm thấy lớp |
| 17 | `CloneNotSupportedException` | Cố clone một đối tượng không triển khai interface `Cloneable` |
| 18 | `IllegalAccessException` | Bị từ chối quyền truy cập vào một lớp |
| 19 | `InstantiationException` | Cố tạo đối tượng từ lớp trừu tượng hoặc interface |
| 20 | `InterruptedException` | Một thread bị thread khác ngắt (interrupt) |
| 21 | `NoSuchFieldException` | Field yêu cầu không tồn tại |
| 22 | `NoSuchMethodException` | Phương thức yêu cầu không tồn tại |

> **Cách đọc bảng này:** cùng cách đọc như bảng trên, nhưng nhóm này là **checked** — compiler sẽ ép bạn xử lý (try/catch hoặc throws) nếu code bạn gọi được chúng; gặp khi làm việc với reflection, clone, thread.

## Ví dụ

> 📄 **Code ví dụ:** [`examples/01-vi-du.java`](examples/01-vi-du.java)

## Ghi nhớ

Mục này chốt lại hai điều nên mang đi sau khi đọc danh bạ trên.

- Checked exceptions (`IOException`, `SQLException`...) compiler **ép bạn** phải xử lý; unchecked exceptions trên bảng 1 xảy ra lúc chạy và thường do lỗi lập trình.
- Khi bắt exception, bạn nên bắt loại **cụ thể nhất** trước (xem bài `2_try-catch-block`).

> **Tóm gọn 1 câu:** Các exception có sẵn là "danh bạ sự cố" đóng gói trong `java.lang` — đừng học thuộc, chỉ cần biết tra, và nhớ nhóm nào compiler ép xử lý.
