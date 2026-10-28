# Access Modifiers (Phạm vi truy cập)

> Access modifiers trong Java dùng để chỉ định **phạm vi truy cập** của biến, thành viên dữ liệu, phương thức, lớp hoặc constructor. Chúng giúp hạn chế và bảo vệ việc truy cập dữ liệu.

Java có 4 loại access modifier, liệt kê theo phạm vi từ hẹp đến rộng:

- `default` (không cần từ khóa)
- `private`
- `protected`
- `public`

![alt text](image.png)

## Default Access Modifier

`Default access modifier` nghĩa là bạn **không khai báo tường minh** modifier nào cho lớp, field, phương thức...

> Biến hoặc phương thức không có modifier chỉ có thể được truy cập bởi **các lớp trong cùng package**. Lưu ý: field trong interface mặc định là `public static final` và phương thức trong interface mặc định là `public`.

Ví dụ:

```java
String version = "1.5.1";

boolean processOrder() {
   return true;
}
```

## Private Access Modifier

> Các phương thức, biến, constructor khai báo `private` **chỉ có thể được truy cập bên trong chính lớp khai báo chúng**.

- `private` là mức truy cập **hạn chế nhất**. Class và interface không thể là `private`.
- Biến `private` vẫn có thể truy cập từ ngoài lớp **thông qua các phương thức getter/setter public**.
- Dùng `private` là cách chính để một đối tượng **đóng gói (encapsulate)** và ẩn dữ liệu khỏi thế giới bên ngoài.

Ví dụ 1:

```java
public class Logger {
    // Here, the format variable of the Logger class is private, so there's no way for other classes to retrieve or set its value directly.
    private String format;


    // So, to make this variable available to the outside world, we defined two public methods: getFormat(), which returns the value of format, and setFormat(String), which sets its value.
    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
```

Ví dụ 2 — khi getter/setter cũng là `private`, lớp ngoài không gọi được nữa:

```java
class Logger {
  private String format;

  private String getFormat() {
    return this.format;
  }

  private void setFormat(String format) {
    this.format = format;
  }
}


public class Main {
  public static void main(String[] args) {
    // Creating an object 
    Logger log = new Logger();
    // Setting the value
    log.setFormat("Text");
    // Getting the value
    System.out.println(log.getFormat());


    // Main.java:18: error: setFormat(String) has private access in Logger
    // log.setFormat("Text");
  }
}
```

## Protected Access Modifier

> Các biến, phương thức, constructor khai báo `protected` trong superclass **chỉ được truy cập bởi các lớp con (kể cả ở package khác)** hoặc **bất kỳ lớp nào trong cùng package** với lớp chứa thành phần đó.

- `protected` không áp dụng được cho class và interface. Với interface, các phương thức và field **không thể** khai báo `protected`.
- `protected` cho lớp con cơ hội dùng lại các phương thức/biến hỗ trợ, đồng thời ngăn các lớp không liên quan dùng chúng.

```java
class AudioPlayer {
   protected boolean openSpeaker(Speaker sp) {
      // implementation details
   }
}

class StreamingAudioPlayer extends AudioPlayer {
   boolean openSpeaker(Speaker sp) {
      // implementation details
   }
}
```

## Public Access Modifier

Lớp, phương thức, constructor, interface... khai báo `public` có thể được truy cập từ **bất kỳ lớp nào**. Vì vậy các field, phương thức, khối lệnh bên trong một lớp public đều có thể được truy cập từ bất kỳ đâu trong chương trình Java.

Cú pháp:

```java
public static void main(String[] arguments) {
   // ...
}
```

Ví dụ:

```java
// Class One
class One {
  public void printOne() {
    System.out.println("printOne method of One class.");
  }
}

public class Main {
  public static void main(String[] args) {
    // Creating an object of class One
    One obj = new One();

    // Calling printOne() method of class One
    obj.printOne();
  }
}
```

## Access Modifiers và kế thừa

Với các phương thức được **kế thừa**, Java áp dụng các quy tắc sau:

> - Phương thức khai báo `public` trong superclass **phải** vẫn là `public` trong mọi lớp con.
> - Phương thức khai báo `protected` trong superclass phải là `protected` hoặc `public` trong lớp con; **không thể** là `private`.
> - Phương thức `private` không được kế thừa, nên không có quy tắc nào cho chúng.

![alt text](image-1.png)

## Tổng kết

| Modifier | Cùng class | Cùng package | Lớp con (khác package) | Mọi nơi |
|----------|:---:|:---:|:---:|:---:|
| `private` | ✅ | ❌ | ❌ | ❌ |
| `default` | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |
