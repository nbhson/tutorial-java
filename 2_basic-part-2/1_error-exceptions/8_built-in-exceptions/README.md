# Các Exception có sẵn trong Java (Built-in Exceptions)

## Exception trong Java là gì?

`Exception` (ngoại lệ) là một sự cố phát sinh trong quá trình chạy chương trình. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn, chương trình có thể kết thúc bất thường — vì vậy bạn cần xử lý chúng.

Lý do exception xảy ra có thể là: người dùng nhập dữ liệu không hợp lệ, file cần mở không tồn tại, kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

> Có exception do lỗi người dùng, có exception do lỗi lập trình viên, và có exception do tài nguyên vật lý gặp sự cố.

## Các exception có sẵn phổ biến trong Java

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

## Ví dụ

```java
public class BuiltInExceptionDemo {
    public static void main(String[] args) {
        try {
            int a = 10 / 0;               // ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("Lỗi số học: " + e.getMessage());
        }

        try {
            int[] arr = new int[3];
            arr[5] = 1;                    // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Vượt chỉ số mảng: " + e.getMessage());
        }

        try {
            int n = Integer.parseInt("abc"); // NumberFormatException
        } catch (NumberFormatException e) {
            System.out.println("Chuỗi không phải số hợp lệ");
        }

        String s = null;
        // s.length();                    // NullPointerException nếu bỏ comment
    }
}
```

## Ghi nhớ

- Checked exceptions (`IOException`, `SQLException`...) compiler **ép bạn** phải xử lý; unchecked exceptions trên bảng 1 xảy ra lúc chạy và thường do lỗi lập trình.
- Khi bắt exception, bạn nên bắt loại **cụ thể nhất** trước (xem bài `2_try-catch-block`).
