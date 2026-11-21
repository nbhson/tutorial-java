# Java Inheritance

Trong lập trình Java, kế thừa là một khái niệm quan trọng của Java OOP.

> Kế thừa là một quá trình mà một lớp có được các thuộc tính (phương thức và thuộc tính) của một lớp khác. Với việc sử dụng kế thừa, thông tin được quản lý theo thứ tự phân cấp.

Class kế thừa các thuộc tính của class khác được gọi là lớp con (child class) và class có các thuộc tính được kế thừa được gọi là superclass (parent class).

Ví von: kế thừa giống như nhận thừa kế gia tài — con cái tự động sở hữu những gì cha mẹ đã gây dựng, rồi có thể phát triển thêm trên nền đó.

## Need of Java Inheritance

Mục này trả lời câu hỏi "tại sao cần kế thừa" — đọc để hiểu 4 lợi ích thực tế trước khi học cú pháp.

- **Code Reusability**: The basic need of an inheritance is to reuse the features. If you have defined some functionality once, by using the inheritance you can easily use them in other classes and packages.
- **Extensibility**: The inheritance helps to extend the functionalities of a class. If you have a base class with some functionalities, you can extend them by using the inheritance in the child class.
- **Cấy ghép ghi đè phương thức**: Kế thừa là cần thiết để đạt được một trong những khái niệm của Đa hình là ghi đè phương thức.
- **Đạt được tính trừu tượng**: Một khái niệm khác của OOP là tính trừu tượng cũng cần có sự kế thừa.

## Implementation of Java Inheritance

Mục này nói về cú pháp cụ thể: từ khóa `extends` — thứ biến "lý thuyết thừa kế" thành code chạy được.

Để triển khai (sử dụng) kế thừa trong Java, từ khóa `extends` được sử dụng. Nó kế thừa các thuộc tính (thuộc tính hoặc/và phương thức) của lớp cơ sở cho lớp con. 

> 📄 **Code ví dụ:** [`examples/01-implementation-of-java-inheritance.java`](examples/01-implementation-of-java-inheritance.java)

Java Inheritance Example

> 📄 **Code ví dụ:** [`examples/02-implementation-of-java-inheritance.java`](examples/02-implementation-of-java-inheritance.java)

In the given program, when an object to `My_Calculation` class is created, a copy of the contents of the superclass is made within it. That is why, using the object of the subclass you can access the members of a superclass.

![alt text](image.png)


The Superclass reference variable can hold the subclass object, but using that variable you can access only the members of the superclass, so to access the members of both classes it is recommended to always create reference variable to the subclass.

Ví von: cầm chìa khóa phòng khách (reference của superclass) thì chỉ mở được phòng khách, dù ngôi nhà phía sau còn cả phòng ngủ — muốn đi hết nhà thì phải có chìa khóa cả nhà (reference của subclass).

> 📄 **Code ví dụ:** [`examples/03-implementation-of-java-inheritance.java`](examples/03-implementation-of-java-inheritance.java)

> **Note** − A subclass inherits all the members (fields, methods, and nested classes) from its superclass. `Constructors are not members`, so they are not inherited by subclasses, but the constructor of the superclass can be invoked from the subclass.

## Java Inheritance: The super Keyword

Mục này nói về từ khóa `super` — "điện thoại hotline" gọi thẳng về lớp cha khi bạn cần phân biệt trùng tên hoặc mượn constructor của cha.

The `super` keyword is **similar to this keyword**. Following are the scenarios where the super keyword is used.

- Nó được sử dụng để phân biệt các thành viên của lớp cha với các thành viên của lớp con nếu chúng có cùng tên. (Sample Code 1)
- Nó được sử dụng để gọi `constructor` của lớp cha từ lớp con. (Sample Code 2)

### Differentiating the Members

If a class is inheriting the properties of another class. And if the members of the superclass have the names same as the sub class, to differentiate these variables we use super keyword as shown below. Hãy tưởng tượng hai người cùng tên "Minh" trong một nhà: muốn nói chuyện với bố Minh thì phải gọi rõ "Minh của bố" — `super` chính là cách gọi đó trong code.

> 📄 **Code ví dụ:** [`examples/04-differentiating-the-members.java`](examples/04-differentiating-the-members.java)

Sample Code 1

> 📄 **Code ví dụ:** [`examples/05-differentiating-the-members.java`](examples/05-differentiating-the-members.java)

### Invoking Superclass Constructor

If a class is inheriting the properties of another class, the subclass automatically có được the default constructor of the superclass. But if you want to call a parameterized constructor of the superclass, you need to use the super keyword as shown below. Ví von: muốn dùng đúng "gói nguyên liệu" mà cha đã chuẩn bị sẵn thì phải gọi riêng ra xin, chứ không tự sinh ra.

Sample Code 2

> 📄 **Code ví dụ:** [`examples/06-invoking-superclass-constructor.java`](examples/06-invoking-superclass-constructor.java)

## IS-A Relationship

Mục này nói về cách đọc mối quan hệ kế thừa bằng tiếng người: "Dog IS-A Animal" — kiểm tra xem hai class có đúng quan hệ "là một loại của" hay không.

IS-A is a way of saying: This object is a type of that object. Let us see how the extends keyword is used to achieve inheritance.

> 📄 **Code ví dụ:** [`examples/07-is-a-relationship.java`](examples/07-is-a-relationship.java)

Bây giờ, dựa trên ví dụ trên, theo thuật ngữ hướng đối tượng, những điều sau đây là đúng:

- Animal is the superclass of Mammal class.
- Animal is the superclass of Reptile class.
- Mammal and Reptile are subclasses of Animal class.
- Dog is the subclass of both Mammal and Animal classes.


Bây giờ, nếu chúng ta xem xét mối quan hệ IS-A, we can say −
 
- Mammal IS-A Animal
- Reptile IS-A Animal
- Dog IS-A Mammal
- Hence: Dog IS-A Animal as well

> Với việc sử dụng từ khóa extends, các lớp con sẽ có thể kế thừa tất cả các thuộc tính của lớp cha ngoại trừ các thuộc tính riêng tư của lớp cha.

## Java Inheritance: The instanceof Keyword

Mục này nói về công cụ "kiểm tra hộ chiếu": `instanceof` cho phép code hỏi một object có thực sự thuộc một loại nào đó hay không trước khi tin tưởng dùng nó.

Chúng ta hãy sử dụng toán tử instanceof để kiểm tra xem Mammal có thực sự là một loài động vật hay không và dog có thực sự là một loài động vật hay không.

> 📄 **Code ví dụ:** [`examples/08-java-inheritance-the-instanceof-keyword.java`](examples/08-java-inheritance-the-instanceof-keyword.java)

### Types of Java Inheritance

![alt text](image-1.png)

### Java Single Inheritance
 
Mục này mô tả 3 dạng kế thừa Java cho phép — đọc từng dạng để nhận ra "hình dáng" cây kế thừa khi gặp trong code thật.

Sự kế thừa đơn (hoặc, một cấp) kế thừa dữ liệu từ chỉ một lớp cơ sở cho chỉ một lớp dẫn xuất. Giống như một gia đình một con: cha truyền cho con, hết.

> 📄 **Code ví dụ:** [`examples/09-java-single-inheritance.java`](examples/09-java-single-inheritance.java)

### Java Multilevel Inheritance

Sự kế thừa trong đó một lớp cơ sở được kế thừa cho một lớp dẫn xuất và lớp dẫn xuất đó được kế thừa thêm cho một lớp dẫn xuất khác được gọi là kế thừa đa cấp. Kế thừa đa cấp liên quan đến nhiều lớp cơ sở. Ví von: ông truyền cho cha, cha truyền cho cháu — gia tài đi xuống từng đời.

> 📄 **Code ví dụ:** [`examples/10-java-multilevel-inheritance.java`](examples/10-java-multilevel-inheritance.java)

### Java Hierarchical Inheritance

Sự kế thừa trong đó chỉ có một lớp cơ sở và nhiều lớp dẫn xuất được gọi là sự kế thừa phân cấp. Giống một người cha có nhiều người con: cùng nhận thừa kế từ một nguồn.

> 📄 **Code ví dụ:** [`examples/11-java-hierarchical-inheritance.java`](examples/11-java-hierarchical-inheritance.java)

## Note

Mục này giải thích vì sao Java "khép kín" hơn các ngôn ngữ khác: chỉ cho kế thừa đơn nhưng vẫn cho kế thừa nhiều tầng — đọc để hiểu lý do thiết kế đằng sau quy tắc này.

Trong Java, mặc dù chỉ cho phép kế thừa đơn (mỗi lớp chỉ có thể kế thừa từ một lớp cha duy nhất), nhưng việc cho phép kế thừa lồng (multilevel inheritance) vẫn có ý nghĩa và mang lại nhiều lợi ích. Dưới đây là một số lý do và giải thích cho việc này:

1. Cấu trúc Phân Cấp Rõ Ràng

Kế thừa lồng cho phép xây dựng một cấu trúc phân cấp rõ ràng và có tổ chức. Mỗi lớp có thể mở rộng và bổ sung các thuộc tính và phương thức của lớp cha mà không làm phức tạp hóa mối quan hệ giữa các lớp.

2. Tránh Kế Thừa Đa (Multiple Inheritance)

Java không hỗ trợ kế thừa đa (nơi một lớp có thể kế thừa từ nhiều lớp cha) để tránh các vấn đề phức tạp như "Diamond Problem" (vấn đề hình thoi).
Vấn đề này xảy ra khi một lớp kế thừa từ hai lớp cha có cùng một phương thức, gây khó khăn trong việc xác định phương thức nào sẽ được gọi. Ví von: một đứa trẻ bị hai ông bà nội ngoại cùng dạy hai cách ăn cơm khác nhau — không biết nghe ai; đó chính là "Diamond Problem".

3. Sự Đơn Giản và Dễ Hiểu

Kế thừa lồng giúp giữ cho mối quan hệ giữa các lớp đơn giản và dễ hiểu. Nếu một lớp có thể kế thừa từ nhiều lớp cha, điều này có thể dẫn đến sự phức tạp trong việc theo dõi và hiểu cách các lớp tương tác với nhau.

> Việc cho phép kế thừa lồng trong Java giúp duy trì tính đơn giản, rõ ràng và dễ bảo trì trong thiết kế hệ thống. Mặc dù có thể có những trường hợp mà kế thừa đa có vẻ gọn hơn, nhưng các vấn đề phức tạp mà nó mang lại thường không đáng để đánh đổi. Thay vào đó, Java khuyến khích việc sử dụng giao diện để đạt được tính linh hoạt và đa hình mà không cần phải đối mặt với những vấn đề phức tạp của kế thừa đa.

> **Tóm gọn 1 câu:** Kế thừa là "nhận gia tài từ lớp cha" — dùng `extends` để nhận, `super` để gọi về cha, và Java chỉ cho một đường truyền thừa kế để tránh "Diamond Problem".
