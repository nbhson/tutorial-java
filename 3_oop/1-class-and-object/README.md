# Class & Object

## What are Java Classes?

Mục này trả lời câu hỏi đầu tiên của OOP: class là gì và nó đóng vai trò gì trong Java — đọc để hiểu "bản thiết kế" trước khi tự tay dựng nhà.

- Một `class` là một bản thiết kế mà từ đó các đối tượng riêng lẻ được tạo ra (hoặc chúng ta có thể nói một lớp là một kiểu dữ liệu của một kiểu đối tượng).
- Trong Java, mọi thứ đều liên quan đến các lớp và đối tượng. Mỗi lớp có các `phương thức` và `thuộc tính` riêng có thể được truy cập và thao tác thông qua các đối tượng.

*For example, if you want to create a class for students. In that case, "Student" will be a class, and student records (like student1, student2, etc) will be objects.*

Ví von: class giống bản vẽ kiến trúc của một ngôi nhà — bản vẽ thì không ở được, nhưng từ nó bạn có thể xây hàng trăm căn nhà giống hệt nhau (đó chính là các object).

### Properties of Java Classes

- Một lớp không chiếm bất kỳ byte bộ nhớ nào. 
- Một lớp cũng giống như một thực thể trong thế giới thực, nhưng nó không phải là một thực thể trong thế giới thực. Nó là một bản thiết kế trong đó chúng ta chỉ định các chức năng. 
- Một lớp chủ yếu chứa hai thứ: Phương thức và Thành viên dữ liệu. 
- Một lớp cũng có thể là một lớp lồng nhau. Các lớp tuân theo tất cả các quy tắc của OOP như kế thừa, đóng gói, trừu tượng hóa, v.v.

### Types of Class Variables

Mục này phân biệt 3 loại biến trong một class — hiểu đúng loại nào "sống" ở đâu sẽ giúp bạn tránh rất nhiều lỗi về phạm vi và bộ nhớ.

- `Local variable` − Các biến được định nghĩa bên trong phương thức, hàm tạo hoặc khối được gọi là biến cục bộ. Biến sẽ được khai báo và khởi tạo bên trong phương thức và biến sẽ bị hủy khi phương thức hoàn tất. 
- `Instance variable`− Biến thể hiện là các biến bên trong một lớp nhưng bên ngoài bất kỳ phương thức nào. Các biến này được khởi tạo khi lớp được khởi tạo. Biến thể hiện có thể được truy cập từ bên trong bất kỳ phương thức, hàm tạo hoặc khối nào của lớp cụ thể đó. 
- `Class variable` − Biến lớp là các biến được khai báo bên trong một lớp, bên ngoài bất kỳ phương thức nào, với từ khóa static.

Ví von để dễ nhớ: local variable là đồ dùng cá nhân trong phòng (đi khỏi phòng là bỏ lại), instance variable là đồ dùng chung của từng căn hộ (mỗi căn một bộ), còn class variable là biển hiệu trước chung cư — cả tòa nhà chỉ có một cái, ai cũng nhìn thấy cùng một nội dung.

### Creating (Declaring) a Java Class

Mục này chỉ cách "vẽ" bản thiết kế: cú pháp khai báo một class trong Java.

To create (declare) a class, you need to use `access modifiers` followed by `class` keyword and `class_name`

> 📄 **Code ví dụ:** [`examples/01-creating-declaring-a-java-class.java`](examples/01-creating-declaring-a-java-class.java)

Example of a Java Class

> 📄 **Code ví dụ:** [`examples/02-creating-declaring-a-java-class.java`](examples/02-creating-declaring-a-java-class.java)

## What are Java Objects?

Mục này nói về "chiếc nhà xây từ bản vẽ": object là gì, tại sao gọi là thể hiện (instance) của class, và cách tạo ra một object bằng từ khóa `new`.

- Đối tượng là một biến thuộc loại lớp, là thành phần cơ bản của hệ thống lập trình hướng đối tượng. Một lớp có các phương thức và thành viên dữ liệu (thuộc tính), các phương thức và thành viên dữ liệu này được truy cập thông qua một đối tượng. `Do đó, một đối tượng là một thể hiện của một lớp.` 
- Nếu chúng ta xem xét thế giới thực, chúng ta có thể tìm thấy nhiều đối tượng xung quanh chúng ta, ô tô, chó, con người, v.v. Tất cả các đối tượng này đều có trạng thái và hành vi. Nếu chúng ta xem xét một con chó, thì trạng thái của nó là : tên, giống và màu sắc, và hành vi là - sủa, vẫy đuôi và chạy. 

### Creating (Declaring) a Java Object

Như đã đề cập trước đó, một lớp cung cấp bản thiết kế cho các đối tượng. Về cơ bản, một đối tượng được tạo ra từ một lớp. Trong Java, từ khóa `new` được sử dụng để tạo các đối tượng mới.

There are three steps when creating an object from a class −

- Declaration − A variable declaration with a variable name with an object type.
- Instantiation − The 'new' keyword is used to create the object.
- Initialization − The 'new' keyword is followed by a call to a constructor. This call initializes the new object.

Ví von: tạo object giống như nhận nhà — Declaration là đăng ký tên chủ hộ, Instantiation là xây nhà (cấp gạch `new`), Initialization là dọn vào ở và sắp xếp đồ đạc (gọi constructor).

Syntax to Create a Java Object

> 📄 **Code ví dụ:** [`examples/03-creating-declaring-a-java-object.java`](examples/03-creating-declaring-a-java-object.java)

Example to Create a Java Object

> 📄 **Code ví dụ:** [`examples/04-creating-declaring-a-java-object.java`](examples/04-creating-declaring-a-java-object.java)

> **Tóm gọn 1 câu:** Class là bản vẽ, object là ngôi nhà xây từ bản vẽ đó — `new` chính là cú bật công xây nhà.
