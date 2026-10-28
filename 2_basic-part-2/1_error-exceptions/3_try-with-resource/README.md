# Try-with-resources

## Exception trong Java là gì?

`Exception` (ngoại lệ) là một sự cố phát sinh trong quá trình chạy chương trình. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn, chương trình có thể kết thúc bất thường — vì vậy bạn cần xử lý chúng.

Lý do exception xảy ra có thể là: người dùng nhập dữ liệu không hợp lệ, file cần mở không tồn tại, kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ. Chi tiết các nhóm exception (checked/unchecked/error) bạn xem ở bài `1_exceptions-introduce`.

## Try-with-resources

> Câu lệnh try-with-resources trong Java là câu lệnh try được sử dụng để khai báo một hoặc nhiều **tài nguyên** như streams, sockets, database connections... Các tài nguyên này **phải được đóng** khi chương trình hoàn tất. Câu lệnh try-with-resources sẽ đóng các tài nguyên ở cuối khối try **một cách tự động**.

Tính năng try-with-resources được giới thiệu từ **Java 7**. Nó có thể thay thế cho mô hình try-catch-finally khi làm việc với các đối tượng tài nguyên.

## Cú pháp

```java
try(resources declarations) {
   // use of the resources
}
catch(Exception e) {
   // exception handling
}
```

## Ví dụ

```java
try(FileReader fr = new FileReader("E://file.txt")) {
   char [] a = new char[50];
   fr.read(a);   // reads the contentto the array
   for(char c : a)
   System.out.print(c);   // prints the characters one by one
} catch (IOException e) {
   e.printStackTrace();
}
```

Khi ra khỏi khối `try` (bình thường hoặc do exception), `fr.close()` được gọi **tự động** — bạn không cần viết `finally` để đóng tài nguyên.

## Try-with-resources với nhiều tài nguyên

```java
// This example is to use Try with Resources 
// with multiple  Resources
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    // try block with multiple resources
    try (
      FileReader fileReader = new FileReader("file1.txt"); 
      BufferedReader bufferedReader = new BufferedReader(fileReader); 
      FileWriter fileWriter = new FileWriter("file2.txt"); 
      PrintWriter printWriter = new PrintWriter(fileWriter)
    ) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        // Read content line by line and write it 
        // to the output (file2.txt) file
        printWriter.println(line);
      }
      System.out.println("Content copied.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```

Sau khi kết thúc khối try, các tài nguyên sẽ tự động được đóng mà không cần gọi `close()` rõ ràng. Nếu có lỗi xảy ra trong quá trình đọc file, nó sẽ được bắt và xử lý trong khối catch.

### Không dùng try-with-resources (cách cũ, dài dòng hơn)

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
      } finally {
         try {
            fr.close();
         } catch (IOException ex) {		
            ex.printStackTrace();
         }
      }
   }
}
```

## Điều kiện để dùng

- Tài nguyên khai báo trong khối `try(...)` phải triển khai interface `AutoCloseable` (có phương thức `close()`).
- Các tài nguyên được đóng theo **thứ tự ngược lại** với thứ tự khai báo.
- Bạn vẫn được khai báo biến bên ngoài và tham chiếu vào try-with-resources nếu cần (Java 9+ cho phép dùng biến `final`/effectively final đã khai báo sẵn).
