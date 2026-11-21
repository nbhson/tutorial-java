# Class Methods

## Java Class Methods

Mục này định nghĩa class method — "bộ tay chân" của một class: thứ mà object dùng để làm việc.

Class Methods là phương thức được khai báo trong một lớp. Chúng thực hiện các hoạt động cụ thể và có thể truy cập, sửa đổi các thuộc tính lớp.

Ví von: nếu class là một chiếc máy pha cà phê, thì class methods là các nút bấm — mỗi nút làm một việc cụ thể, và máy chủ (class) chứa cả các nguyên liệu (thuộc tính) mà nút bấm thao tác lên.

## Creating (Declaring) Java Class Methods

Mục này nói về cú pháp khai báo method: từng thành phần trong một dòng khai báo có ý nghĩa gì — đọc để khi nhìn chữ ký method bạn "đọc được" nó như đọc một câu.

- Class methods declaration is similar to the user-defined methods declaration except that class methods are declared within a class.
- Các phương thức lớp được khai báo bằng cách chỉ định access modifier, followed by the return type, method_name, and parameters list.

Syntax:

- `modifier` − It defines the access type of the method and it is optional to use.
- `returnType` − The returns data type of the class method.
- `nameOfMethod` − This is the method name. The method signature consists of the method name and the parameter list.
- `Parameter` List − The list of parameters, it is the type, order, and number of parameters of a method. These are optional, method may contain zero parameters.
- `method` body − The method body defines what the method does with the statements.

Ví von dễ nhớ: một dòng khai báo method giống một lời mời công việc — `modifier` là "ai được giao việc", `returnType` là "giao xong nhận lại gì", `nameOfMethod` là "tên công việc", `Parameter List` là "đồ nghề bàn giao ban đầu", còn `method body` là bản mô tả cách làm việc.

> 📄 **Code ví dụ:** [`examples/01-creating-declaring-java-class-methods.java`](examples/01-creating-declaring-java-class-methods.java)

## Accessing Java Class Methods

Mục này chỉ cách "bấm nút": sau khi khai báo method, làm sao gọi nó từ bên ngoài — qua object và dấu chấm.

- To access a class method (public class method), you need to create an object first, then by using the object you can access the class method (with the help of dot (.) operator).

Syntax

> 📄 **Code ví dụ:** [`examples/02-accessing-java-class-methods.java`](examples/02-accessing-java-class-methods.java)

Example

> 📄 **Code ví dụ:** [`examples/03-accessing-java-class-methods.java`](examples/03-accessing-java-class-methods.java)

> **Tóm gọn 1 câu:** Class method là "nút bấm trên máy" — khai báo đủ modifier, kiểu trả về, tên, tham số; rồi gọi qua object với dấu chấm.
