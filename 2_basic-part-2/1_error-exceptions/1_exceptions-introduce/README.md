# Exceptions (Ngoại lệ)

## Exception trong Java là gì?

`Exception` (ngoại lệ) là một sự cố phát sinh **trong quá trình chạy chương trình**. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn và chương trình có thể kết thúc bất thường — điều này không được khuyến khích, vì vậy bạn cần **xử lý (handle)** chúng.

## Vì sao exception xảy ra?

Exception có thể xảy ra vì nhiều lý do khác nhau. Một số tình huống điển hình:

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

> Có exception do lỗi người dùng, có exception do lỗi lập trình viên, và có exception do tài nguyên vật lý gặp sự cố.

## Các nhóm exception trong Java

Java chia exception thành 3 nhóm chính:

- **Checked exceptions**
- **Unchecked exceptions**
- **Errors**

### Checked Exceptions

`Checked exception` là exception được **compiler kiểm tra ngay lúc biên dịch** (nên còn gọi là compile-time exception). **Bạn không thể bỏ qua chúng — bắt buộc phải bắt (try/catch) hoặc khai báo ném ra (throws).**

```java
import java.io.File;
import java.io.FileReader;

public class FilenotFound_Demo {

   public static void main(String args[]) {		
      File file = new File("E://file.txt");
      FileReader fr = new FileReader(file); 
   }
}

// C:\>javac FilenotFound_Demo.java
// FilenotFound_Demo.java:8: error: unreported exception FileNotFoundException; must be caught or declared to be thrown
//       FileReader fr = new FileReader(file);
```

Compiler báo lỗi vì `FileReader` có thể ném `FileNotFoundException` mà bạn chưa xử lý.

### Unchecked Exceptions

- `Unchecked exception` là exception xảy ra tại **thời điểm chạy (runtime)**.
- Thường do lỗi lập trình: sai logic, dùng API không đúng cách.
- **Compiler bỏ qua, không ép bạn xử lý.**

```java
public class Unchecked_Demo {
   
   public static void main(String args[]) {
      int num[] = {1, 2, 3, 4};
      System.out.println(num[5]);   // ArrayIndexOutOfBoundsException lúc chạy
   }
}
```

### Errors

Đây **không phải exception** mà là những vấn đề phát sinh ngoài tầm kiểm soát của người dùng hoặc lập trình viên (ví dụ: `StackOverflowError`, `OutOfMemoryError`). Lỗi thường bị bỏ qua trong mã của bạn vì bạn hiếm khi có thể làm gì khi chúng xảy ra. Chúng cũng chỉ bộc lộ lúc chạy chương trình, không bị compiler kiểm tra.

> Trong Java, quá trình **biên dịch (compilation)** chạy trước quá trình **thực thi (execution)**: mã nguồn được dịch thành bytecode, sau đó JVM thực thi bytecode đó.

![alt text](image.png)

## Xử lý exception trong Java

### Try/catch

Bạn bắt exception bằng cặp từ khóa `try` và `catch`. Khối try/catch đặt **bao quanh đoạn code có thể gây exception** — đoạn code này được gọi là *protected code*:

```java
try {
   // Protected code
} catch (ExceptionName e1) {
   // Catch block
}

// Multiple Catch Blocks
try {
   // Protected code
} catch (ExceptionType1 e1) {
   // Catch block
} catch (ExceptionType2 e2) {
   // Catch block
} catch (ExceptionType3 e3) {
   // Catch block
} finally {
   // The finally block always executes.
}

// example
int a[] = new int[2];
try {
   System.out.println("Access element three :" + a[3]);
} catch (ArrayIndexOutOfBoundsException e) {
   System.out.println("Exception thrown  :" + e);
} finally {
   a[0] = 6;
   System.out.println("First element value: " + a[0]);
   System.out.println("The finally statement is executed");
}
```

Chi tiết từng dạng bạn xem ở các bài riêng: `2_try-catch-block`, `4_multiple-catch-blocks`, `6_finally-block`.

### Try-with-resources

Thông thường, khi dùng tài nguyên như stream, connection..., bạn phải đóng chúng một cách tường minh trong khối `finally`. Trong chương trình sau, chúng ta đọc dữ liệu từ file bằng `FileReader` rồi đóng nó trong `finally`:

```java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadData_Demo {

   public static void main(String args[]) {
      FileReader fr = null;		
      try {
         File file = new File("file.txt");
         fr = new FileReader(file); char [] a = new char[50];
         fr.read(a);   // reads the content to the array
         for(char c : a)
         System.out.print(c);   // prints the characters one by one
      } catch (IOException e) {
         e.printStackTrace();
      }finally {
         try {
            fr.close();
         } catch (IOException ex) {		
            ex.printStackTrace();
         }
      }
   }
}
```

Từ Java 7, cú pháp **try-with-resources** cho phép tự động đóng tài nguyên khi ra khỏi khối `try`:

```java
try(FileReader fr = new FileReader("file path")) {
   // use the resource
   } catch (IOException e) {
      // body of catch 
   }
```

```java
import java.io.FileReader;
import java.io.IOException;

public class Try_withDemo {

   public static void main(String args[]) {
      try(FileReader fr = new FileReader("E://file.txt")) {
         char [] a = new char[50];
         fr.read(a);   // reads the contentto the array
         for(char c : a)
         System.out.print(c);   // prints the characters one by one
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
```

### Throws / Throw

- `throw` — ném một exception cụ thể ra khỏi phương thức.
- `throws` — khai báo rằng phương thức này có thể ném loại exception đó, đẩy việc xử lý cho nơi gọi.

```java
public void divideNumbers(int a, int b) throws ArithmeticException {
    if (b == 0) {
        throw new ArithmeticException("Không thể chia cho 0");
    }
    int result = a / b;
}
```

## Các phương thức quan trọng của lớp Exception

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 1 | `public String getMessage()` | Trả về thông điệp chi tiết của exception — được khởi tạo trong constructor của `Throwable` |
| 2 | `public Throwable getCause()` | Trả về nguyên nhân gốc của exception (dạng một đối tượng `Throwable`) |
| 3 | `public String toString()` | Trả về tên lớp nối với kết quả của `getMessage()` |
| 4 | `public void printStackTrace()` | In kết quả của `toString()` cùng stack trace ra `System.err` |
| 5 | `public StackTraceElement[] getStackTrace()` | Trả về mảng các phần tử trong stack trace (phần tử 0 là đỉnh ngăn xếp gọi hàm) |
| 6 | `public Throwable fillInStackTrace()` | Ghi đè stack trace của đối tượng này bằng stack trace hiện tại |

## Tóm tắt

| Nhóm | Thời điểm phát hiện | Bắt buộc xử lý? | Ví dụ |
|------|--------------------|-----------------|-------|
| Checked | Lúc biên dịch | ✅ Có (`try/catch` hoặc `throws`) | `IOException`, `SQLException` |
| Unchecked | Lúc chạy | ❌ Không ép | `NullPointerException`, `ArithmeticException` |
| Error | Lúc chạy | Không xử lý được | `StackOverflowError`, `OutOfMemoryError` |
