# Overriding

## Lợi ích của Overriding trong Java

Lợi ích của việc ghi đè là: `khả năng xác định hành vi cụ thể cho loại lớp con`, nghĩa là lớp con có thể triển khai phương thức lớp cha dựa trên yêu cầu của lớp đó. Theo thuật ngữ hướng đối tượng, ghi đè có nghĩa là ghi đè chức năng của phương thức hiện có.

## Java Method Overriding

- Ghi đè phương thức cho phép chúng ta đạt được đa hình thời gian chạy và được sử dụng để viết các định nghĩa cụ thể của phương thức lớp con đã được định nghĩa trong lớp cha.
- Phương thức là lớp cha và phương thức được ghi đè trong lớp con phải có cùng chữ ký khai báo như danh sách tham số, kiểu và kiểu trả về.

## Cách sử dụng method overriding trong Java

Dưới đây là hai công dụng quan trọng của method overriding trong Java:

> - Dùng để đạt được **đa hình thời gian chạy (run-time polymorphism)**.
> - Dùng để viết định nghĩa cụ thể cho phương thức của lớp con (phương thức này gọi là **overridden method**).

## Example of Method Overriding in Java

```java
class Animal {
   public void move() {
      System.out.println("Animals can move");
   }
}

class Dog extends Animal {
   public void move() {
      // super.move()
      System.out.println("Dogs can walk and run");
   }
   public void bark() {
      System.out.println("Dogs can bark");
   }
}

public class TestDog {

   public static void main(String args[]) {
      Animal a = new Animal();   // Animal reference and object
      Animal b = new Dog();   // Animal reference but Dog object

      a.move();   // runs the method in Animal class
      b.move();   // runs the method in Dog class
      b.bark();

      /*
      TestDog.java:26: error: cannot find symbol
      b.bark();
      */
   }
}
```

## Rules for Method Overriding

- Danh sách `param phải giống hệt` với danh sách param của phương thức được ghi đè.
- Kiểu trả về phải giống hoặc là con của kiểu trả về được khai báo trong phương thức ghi đè ban đầu trong lớp cha.
- `Access modifier` không thể hạn chế hơn mức truy cập của phương thức ghi đè. Ví dụ: Nếu phương thức của class cha được khai báo là public thì phương thức ghi đè trong lớp con không thể là private hoặc protected.
- Một phương thức được khai báo là `final` không thể bị ghi đè.
- Phương thức khai báo `static` không thể bị ghi đè, nhưng có thể được **khai báo lại (re-declare)** trong lớp con.
- Constructor không thể bị ghi đè.

## Java Method and Constructor Overriding

Trong Java, mỗi lớp có một tên khác nhau và tên của hàm tạo giống với tên lớp. Do đó, chúng ta không thể ghi đè một hàm tạo vì chúng không thể có cùng tên.

## Java Method Overriding: dùng từ khóa super

Khi muốn gọi **phiên bản của superclass** cho một phương thức đã bị ghi đè, bạn dùng từ khóa `super`:

```java
class Animal {
   public void move() {
      System.out.println("Animals can move");
   }
}

class Dog extends Animal {
   public void move() {
      super.move();   // invokes the super class method
      System.out.println("Dogs can walk and run");
   }
}

public class TestDog {

   public static void main(String args[]) {
      Animal b = new Dog();   // Animal reference but Dog object
      b.move();   // runs the method in Dog class
   }
}
```

## Kết quả

```bash
Animals can move
Dogs can walk and run
```