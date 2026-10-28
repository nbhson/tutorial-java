# Overloading (Nạp chồng phương thức)

## Method Overloading trong Java

Khi một lớp có **hai hoặc nhiều phương thức cùng tên nhưng tham số khác nhau** (khác số lượng hoặc kiểu dữ liệu), tại thời điểm gọi, dựa trên tham số được truyền vào, phương thức tương ứng sẽ được gọi. Cơ chế này gọi là **method overloading** (nạp chồng phương thức).

## Lợi ích của method overloading

- Nạp chồng cải thiện **khả năng đọc mã** và giảm sự trùng lặp mã.
- Nạp chồng cũng giúp đạt được **đa hình thời gian biên dịch (compile-time polymorphism)**.

## Ví dụ

Ở đây ta tạo một lớp `Tester` có hai phương thức cùng tên (`add`) và cùng kiểu trả về, điểm khác biệt duy nhất là tham số nhận vào (một phương thức nhận hai biến `int`, phương thức kia nhận ba biến `int`):

```java
class Calculator{
   public static int add(int a, int b){
      return a + b;
   }
   public static int add(int a, int b, int c){
      return a + b + c;
   }
}
```

Khi bạn gọi `add()`, dựa trên tham số truyền vào, phần thân phương thức tương ứng sẽ được thực thi:

```java
int result = Calculator.add(1,2);  // returns 3;
result = Calculator.add(1,2,3);    // returns 6;
```

## Các cách nạp chồng hợp lệ

Trong cùng một lớp, với các phương thức cùng tên, bạn có thể nạp chồng bằng cách:

> - **Khác số lượng tham số**
> - **Khác kiểu dữ liệu tham số**

## Các cách nạp chồng KHÔNG hợp lệ

Những cách sau **không** tạo ra overloading — compiler sẽ báo lỗi trùng phương thức:

> - **Khác kiểu trả về** (tham số vẫn giống nhau)

```java
public static int add(int a, int b) {
  return a + b;
}

// error: duplicate method
public static double add(int a, int b) {
  return a + b;
}
```

> - **Khác static/non-static** (tham số vẫn giống nhau)

```java
class Example {
    static void method(int a) {
        // Phương thức static
        System.out.println("Static method: " + a);
    }

    // Invalid overload: cùng tên và cùng tham số
    void method(int a) {
        // Phương thức non-static
        System.out.println("Non-static method: " + a);
    }
}
```

## Ví dụ đầy đủ

### Overloading: khác số lượng tham số

Bạn có thể nạp chồng dựa trên số lượng tham số khác nhau.

#### Số lượng tham số khác nhau (phương thức static)

```java
package com.tutorialspoint;

class Calculator{
   public static int add(int a, int b){
      return a + b;
   }
   public static int add(int a, int b, int c){
      return a + b + c;
   }
}

public class Tester {
   public static void main(String args[]){
      System.out.println(Calculator.add(20, 40));
      System.out.println(Calculator.add(40, 50, 60));
   }
}
```

#### Số lượng tham số khác nhau (phương thức non-static)

```java
package com.tutorialspoint;

class Calculator{
   public int add(int a, int b){
      return a + b;
   }
   public int add(int a, int b, int c){
      return a + b + c;
   }
}

public class Tester {
   public static void main(String args[]){
      Calculator calculator = new Calculator();   
      System.out.println(calculator.add(20, 40));
      System.out.println(calculator.add(40, 50, 60));
   }
}
```

### Overloading: khác kiểu tham số

```java
package com.tutorialspoint;

class Calculator{
   public int add(int a, int b){
      return a + b;
   }
   public double add(double a, double b){
      return a + b;
   }
}

public class Tester {
   public static void main(String args[]){
      Calculator calculator = new Calculator();   
      System.out.println(calculator.add(20, 40));
      System.out.println(calculator.add(20.0, 40.0));
   }
}
```
