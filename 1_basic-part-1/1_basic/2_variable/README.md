# VARIABLE

Vậy làm sao lưu thông tin trong chương trình? Dùng **biến (variable)** — mỗi biến là một "hộp có nhãn" trong bộ nhớ: dán nhãn kiểu dữ liệu để biết hộp to cỡ nào, đựng được gì. Mục này nói về các loại hộp đó và chúng "sống" ở đâu, chết khi nào.

- Gán kiểu dữ liệu để xác định biến mang kiểu dữ liệu nào, kiểu dữ liệu là tập hợp những nhóm dữ liệu có chung đặc tính, cách lưu trữ và thao tác xử lí.
- Nhờ có kiểu dữ liệu compiler nhận biết được kích thước của một biến và khả năng lưu trữ của nó. Mục đích để lưu trữ và tái xử dụng.

Ví von: chọn kiểu dữ liệu giống như chọn thùng hàng — thùng giày (int) không thể đựng tủ lạnh (object), và compiler là người kiểm kho: nhìn nhãn là biết thùng này to bao nhiêu, đựng được cái gì.

![alt text](image.png)

## Local variable

Mục này nói về biến "ăn nhờ ở tạm": chỉ sống trong một method, constructor hay block, ra khỏi đó là biến mất. Đọc để biết vòng đời và giới hạn của nó.

- Local variables are declared in `methods`, `constructors`, or `blocks`.
- Local variables are **created** when the method, constructor or block is entered and the variable will be **destroyed** once it exits the method, constructor, or block.

Nói theo cách đời thường: biến local như **ghế ngồi trong quán cà phê** — bạn bước vào thì có ghế dùng, bước ra thì ghế trả lại cho khách sau, không ai mang ghế về nhà được.

> `Access modifiers` cannot be used for local variables.

Vì ghế chỉ có giá trị trong quán nên không cần "khóa riêng" — biến local không dùng được access modifier (`public`, `private`...).

Example 1: Variable's local scope with initialization
> 📄 **Code ví dụ:** [`examples/01-local-variable.java`](examples/01-local-variable.java)

Example 2: Variable's local scope without initialization
> 📄 **Code ví dụ:** [`examples/02-local-variable.java`](examples/02-local-variable.java)

> **Tóm gọn 1 câu:** Biến local là "khách ở trọ" — sinh ra khi method chạy, tan biến khi method kết thúc, không đòi được quyền riêng tư.

## Java Instance Variables

Mục này nói về biến thuộc về **từng đối tượng**: mỗi object có bản sao riêng của biến này, sống chết theo object. Đọc để phân biệt nó với biến local và biến static ở mục sau.

- Instance variables are declared in a class, but **outside** a method, constructor or any block.
- Instance variables are `created` when an object is created with the use of the keyword 'new' and `destroyed` when the object is destroyed. (question: when the object will be destroyed)
- **Instance variables have default values**. For numbers, the default value is 0, for Booleans it is false, and for object references it is null. Values can be assigned during the declaration or within the constructor.
- Instance variables can be accessed directly by calling the variable name inside the class. However, within `static` methods (when instance variables are given accessibility), they should be called using the fully qualified name. ObjectReference.VariableName.

Ví von: instance variable như **đồ đạc trong từng căn hộ chung cư** — tạo ra khi căn hộ được "new" (bàn giao), biến mất khi căn hộ bị phá. Mỗi căn hộ có bộ đồ riêng, nhà nào nấy xài, kể cả hai căn cùng mẫu thiết kế (cùng class).

> `Instance` variables can be declared in class level before or after use.

> 📄 **Code ví dụ:** [`examples/03-java-instance-variables.java`](examples/03-java-instance-variables.java)

> **Tóm gọn 1 câu:** Instance variable là "đồ đạc của từng căn hộ" — mỗi object một bộ riêng, có sẵn giá trị mặc định nếu bạn chưa bày biện gì.

## Java Class/Static Variables

Mục này nói về biến "dùng chung cả tòa nhà": chỉ có một bản duy nhất, mọi object cùng nhìn thấy, sống trọn vòng đời chương trình. Đọc để biết khi nào nên dùng và cách gọi đúng.

- Class variables also known as static variables are declared with the `static keyword` in a class, but `outside` a method, constructor or a block.
- Static variables are `created` when the program starts and `destroyed` when the program stops.
- most static variables are declared `public` since they must be available for users of the class.
- **Default** values are same as instance variables. For numbers, the default value is 0; for Booleans, it is false; and for object references, it is null. Values can be assigned during the declaration or within the constructor. Additionally, values can be assigned in special static initializer blocks.

Nếu instance variable là đồ đạc từng căn hộ, thì static variable là **tiện ích chung của cả chung cư**: thang máy, bảng điện tử sảnh — chỉ có một bộ, ai cũng dùng chung, từ lúc tòa nhà khai trương (program start) đến khi giải thể (program stop).

> Static variables can be accessed by calling with the class name ClassName.VariableName.

Vì tiện ích thuộc về tòa nhà chứ không phải căn hộ nào, nên gọi theo **tên tòa nhà** (`ClassName.VariableName`) chứ không theo tên căn hộ.

> **Tóm gọn 1 câu:** Biến static là "tiện ích chung cả tòa" — một bản duy nhất, sống cùng chương trình, gọi bằng tên class chứ không qua object.
