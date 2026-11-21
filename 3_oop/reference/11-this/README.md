# this 

## this Keyword in Java Class Methods

Mục này giải thích từ khóa `this` — "tự xưng danh" của object: cách object tự nhắc tên chính mình trong phương thức để phân biệt với các biến trùng tên.

`this` is a keyword in Java which is used as a reference to the object of the current class, with in an instance method or a constructor. Using `this` you can refer the members of a class such as constructors, variables and methods.

Ví von: trong một phòng họp có hai người cùng tên "Hùng", thì người đang trình bày phải nói "Tôi, Hùng, thuộc phòng A" để mọi người biết đang nói đến ai — `this` chính là chữ "tôi" trong code.

> Note − The keyword this is used only within instance methods or constructors

In general, the keyword this is used to:
- Phân biệt các biến thể hiện với các biến cục bộ nếu chúng có cùng tên, trong một hàm tạo hoặc một phương thức.

> 📄 **Code ví dụ:** [`examples/01-this-keyword-in-java-class-methods.java`](examples/01-this-keyword-in-java-class-methods.java)
- Gọi một kiểu constructor (constructor có tham số hoặc default) from other in a class. Nó được gọi là lệnh gọi constructor rõ ràng. Ví von: như nhân viên mới ghi "hồ sơ của tôi xin copy nguyên bản hồ sơ đã lập hôm qua" — constructor này nhờ constructor kia lập sẵn dữ liệu thay vì làm lại.
> 📄 **Code ví dụ:** [`examples/02-this-keyword-in-java-class-methods.java`](examples/02-this-keyword-in-java-class-methods.java)

Example: Using this Keyword in Java Class Methods

> 📄 **Code ví dụ:** [`examples/03-this-keyword-in-java-class-methods.java`](examples/03-this-keyword-in-java-class-methods.java)

> **Tóm gọn 1 câu:** `this` là chữ "tôi" của object — dùng để tự phân biệt khi trùng tên, hoặc để nhờ constructor khác "làm thay" một phần việc.
