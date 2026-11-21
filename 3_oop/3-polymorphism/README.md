# Java Polymorphism

> Polymorphism là khả năng của một đối tượng có thể có nhiều hình dạng. Đa hình là một tính năng quan trọng của khái niệm Java OOP và nó cho phép chúng ta thực hiện nhiều thao tác bằng cách sử dụng tên duy nhất của bất kỳ phương thức nào (interface).

Ví von: polymorphism giống như một người mang nhiều vai — ở công ty là nhân viên, ở nhà là bố, ở sân bóng là thủ môn; cùng một con người nhưng tùy "bối cảnh tham chiếu" mà bạn gọi đúng vai của họ.

## Use of Polymorphism in Java

Mục này nói về ứng dụng thực tế nhất của đa hình: biến tham chiếu lớp cha cầm được object lớp con — kỹ thuật nền cho hầu hết framework, trong đó có Spring Boot.

Ứng dụng phổ biến nhất của đa hình trong OOP là khi tham chiếu lớp cha được sử dụng để tham chiếu đến đối tượng lớp con.

## Java Polymorphism Example

Mục này đi qua một ví dụ cụ thể để bạn "thấy" đa hình bằng mắt: một object Deer được nhìn qua nhiều loại kính khác nhau.

> 📄 **Code ví dụ:** [`examples/01-java-polymorphism-example.java`](examples/01-java-polymorphism-example.java)

Now, the Deer class is considered to be polymorphic since this has multiple inheritance. Following are true for the above examples −

- A Deer IS-A Animal
- A Deer IS-A Vegetarian
- A Deer IS-A Deer
- A Deer IS-A Object

Khi chúng ta áp dụng biến tham chiếu facts vào tham chiếu đối tượng Deer, các khai báo sau đây là hợp lệ −

> 📄 **Code ví dụ:** [`examples/02-java-polymorphism-example.java`](examples/02-java-polymorphism-example.java)

Tất cả các biến tham chiếu d, a, v, o đều tham chiếu đến cùng một đối tượng Deer trong heap. Hãy hình dung: một con nai đứng nguyên tại chỗ, nhưng bốn cái gương khác nhau cùng chiếu nó — gương thì nhiều, nai vẫn chỉ một.

## Java Polymorphism Implementation

> 📄 **Code ví dụ:** [`examples/03-java-polymorphism-implementation.java`](examples/03-java-polymorphism-implementation.java)

## Types of Java Polymorphism

Mục này chia đa hình thành 2 loại theo thời điểm "chốt" hành vi — compile time hay run time. Đọc để biết cách phân loại, vì cách triển khai và lỗi phát sinh của mỗi loại khác nhau.

- Compile Time Polymorphism (đa hình thời gian biên dịch)
- Run Time Polymorphism (đa hình thời gian chạy)

### Compile Time Polymorphism in Java

Đa hình thời gian biên dịch còn được gọi là `static Polymorphism` và được triển khai bằng cách `overloading method`. Ví von: giống như một nhà hàng có nhiều quầy cùng tên "Đặt bàn" nhưng khác số lượng khách — bạn chọn quầy nào ngay từ khi bước vào cửa (lúc compile).

Example: Compile Time Polymorphism

> 📄 **Code ví dụ:** [`examples/04-compile-time-polymorphism-in-java.java`](examples/04-compile-time-polymorphism-in-java.java)

> This example has multiple methods having the same name to achieve the concept of compile-time polymorphism in Java.

### Run Time Polymorphism in Java

Đa hình thời gian chạy còn được gọi là phân phối phương thức động và được triển khai bằng cách `method overriding`. Ví von: cùng một nút bấm "chơi nhạc", tùy vào đĩa nào đang nằm trong máy mà nhạc ra khác nhau — quyết định diễn ra lúc chạy, không phải lúc lắp máy.

> 📄 **Code ví dụ:** [`examples/05-run-time-polymorphism-in-java.java`](examples/05-run-time-polymorphism-in-java.java)

## Virtual Method and Run Time Polymorphism in Java

Mục này nói về cơ chế đứng sau đa hình lúc chạy: virtual method — lý do vì sao Java "chọn đúng phiên bản" method của lớp con dù bạn cầm tham chiếu của lớp cha.

- Trong phần này, tôi sẽ chỉ cho bạn cách hành vi của các phương thức ghi đè trong Java cho phép bạn tận dụng tính đa hình khi thiết kế các lớp của mình.
- We already have discussed method overriding, where a child class can override a method in its parent. An overridden method is essentially hidden in the parent class, and is not invoked unless the child class uses the super keyword within the overriding method.

Example: Implementation of Run Time Polymorphism with Virtual Methods

> 📄 **Code ví dụ:** [`examples/06-virtual-method-and-run-time-polymorphism-in-java.java`](examples/06-virtual-method-and-run-time-polymorphism-in-java.java)

Bây giờ giả sử chúng ta mở rộng lớp Employee như sau −

> 📄 **Code ví dụ:** [`examples/07-virtual-method-and-run-time-polymorphism-in-java.java`](examples/07-virtual-method-and-run-time-polymorphism-in-java.java)

Now, you study the following program carefully and try to determine its output −

> 📄 **Code ví dụ:** [`examples/08-virtual-method-and-run-time-polymorphism-in-java.java`](examples/08-virtual-method-and-run-time-polymorphism-in-java.java)

> **Tóm gọn 1 câu:** Polymorphism là "một cái tên, nhiều bộ mặt" — overloading chốt hình dạng lúc compile, overriding quyết định lúc chạy.
