# Try và Catch

## Exception trong Java là gì?

`Exception` (ngoại lệ) là một sự cố phát sinh trong quá trình chạy chương trình. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn và chương trình có thể kết thúc bất thường — vì vậy bạn cần xử lý chúng.

Lý do exception xảy ra có thể là:

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

## Các nhóm exception trong Java

- **Checked exceptions** — compiler kiểm tra, bắt buộc phải xử lý.
- **Unchecked exceptions** — xảy ra lúc chạy (runtime), compiler không ép xử lý.
- **Errors** — lỗi hệ thống, thường không xử lý được.

Bạn xem chi tiết từng nhóm ở bài `1_exceptions-introduce` và `8_built-in-exceptions`.

## Java try và catch

> Bạn bắt một exception bằng cặp từ khóa `try` và `catch`. Khối try/catch được đặt **bao quanh đoạn code có thể gây exception**.

### Khối try

- Khi exception xảy ra trong khối try, nó sẽ được xử lý bởi khối `catch` tương ứng đi kèm.
- Mỗi khối `try` phải được theo sau **ngay** bởi một khối `catch` hoặc khối `finally`.

### Khối catch

Nếu exception xảy ra trong protected code, khối `catch` đi sau khối `try` sẽ được kiểm tra để tìm loại exception khớp.

### Cú pháp try/catch

```java
try {
   // Protected code
} catch (ExceptionName e1) {
   // Catch block
}
```

### Ví dụ

```java
package com.tutorialspoint;

public class ExcepTest {

   public static void main(String args[]) {
      try {
         int a[] = new int[2];
         System.out.println("Access element three :" + a[3]);
      } catch (ArrayIndexOutOfBoundsException e) {
         System.out.println("Exception thrown  :" + e);
      }
      System.out.println("Out of the block");
   }
}
```

Ở đây `a[3]` gây `ArrayIndexOutOfBoundsException` (mảng chỉ có chỉ số 0 và 1), code trong `catch` chạy thay thế, rồi chương trình **tiếp tục chạy bình thường** — in ra "Out of the block".

## Nhiều khối catch (Multiple Catch Blocks)

Một khối `try` có thể đi kèm nhiều khối `catch`, mỗi khối bắt một loại exception:

```java
try {
   // Protected code
} catch (ExceptionType1 e1) {
   // Catch block
} catch (ExceptionType2 e2) {
   // Catch block
} catch (ExceptionType3 e3) {
   // Catch block
}
```

```java
package com.tutorialspoint;

public class ExcepTest {

   public static void main(String args[]) {
      try {
         int a[] = new int[2];
         int b = 0;
         int c = 1 / b; // move this line 
         System.out.println("Access element three :" + a[3]); // move this line 
      }
      catch (ArrayIndexOutOfBoundsException e) {
         System.out.println("ArrayIndexOutOfBoundsException thrown  :" + e);
      }catch (Exception e) {
          System.out.println("Exception thrown  :" + e);
      }
      System.out.println("Out of the block");
   }
}
```

> Lưu ý: exception xảy ra ở đâu thì các dòng sau đó trong `try` sẽ **không được thực thi** — ví dụ khi `1/b` ném `ArithmeticException`, dòng `a[3]` không chạy nữa.

## Bắt nhiều loại exception trong một catch

Từ Java 7, bạn có thể gộp nhiều loại exception vào **một** khối `catch` bằng toán tử `|`:

```java
catch (IOException|FileNotFoundException ex) {
   logger.log(ex);
   throw ex;
```

> Nếu muốn dùng dạng multiple này, bạn phải chỉ rõ các loại exception cụ thể.

```java
 package com.tutorialspoint;

public class ExcepTest {

   public static void main(String args[]) {
      try {
         int a[] = new int[2];
         int b = 0;
         int c = 1/b;
         System.out.println("Access element three :" + a[3]);
      }
      catch (ArrayIndexOutOfBoundsException | ArithmeticException e) {
         System.out.println("Exception thrown  :" + e);
      }
      System.out.println("Out of the block");
   }
}
```

## Lưu ý

- Khối `catch` con (cụ thể hơn) phải đứng **trước** khối `catch` cha (chung hơn) — đặt `catch (Exception e)` đầu tiên sẽ khiến compiler báo lỗi "exception has already been caught".
- Xem thêm: `3_try-with-resource` (tự đóng tài nguyên), `6_finally-block` (khối luôn chạy), `7_throw-error` (ném exception chủ động).
