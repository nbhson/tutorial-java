# Encapsulation

## Java Encapsulation 

Mục này định nghĩa encapsulation — một trong 4 trụ cột OOP. Đọc để hiểu vì sao "đóng gói" lại được coi là "data hiding".

`Encapsulation` là một trong bốn khái niệm OOP cơ bản. Ba khái niệm còn lại là kế thừa, đa hình và trừu tượng. 

- Encapsulation trong Java là một cơ chế đóng gói dữ liệu (biến) và mã tác động lên dữ liệu (phương thức) lại với nhau thành một đơn vị duy nhất. 
- Trong encapsulation, các biến của một lớp sẽ được ẩn khỏi các lớp khác và chỉ có thể truy cập thông qua các phương thức của lớp hiện tại của chúng. Do đó, nó còn được gọi là `data hiding`.

Ví von: encapsulation giống như quầy thu ngân ngân hàng — tiền của bạn được cất trong két (biến private), muốn gửi hay rút phải qua nhân viên quầy (getter/setter), không ai được tự tay mở két.

## Achieving Encapsulation in Java

Mục này chỉ 2 bước cụ thể để "đóng gói" một class trong Java — chỉ cần nhớ 2 dòng dưới đây là đủ áp dụng.

To achieve encapsulation in Java −

- Declare the variables of a class as private.
- Provide public setter and getter methods to modify and view the variables values.

> **Tóm gọn 1 câu:** Encapsulation = khóa biến lại thành `private` và mở đúng một cửa ra vào là getter/setter.

## Java Encapsulation Example

Mục này đi qua ví dụ cụ thể để bạn thấy cách các "cửa ra vào" (getter/setter) hoạt động trong code thật.

> 📄 **Code ví dụ:** [`examples/01-java-encapsulation-example.java`](examples/01-java-encapsulation-example.java)

The public setXXX() and getXXX() methods are the access points of the instance variables of the EncapTest class.

Normally, these methods are referred as getters and setters. Therefore, any class that wants to access the variables should access them through these getters and setters.

The variables of the EncapTest class can be accessed using the following program −


> 📄 **Code ví dụ:** [`examples/02-java-encapsulation-example.java`](examples/02-java-encapsulation-example.java)

## Benefits of Encapsulation

Mục này nêu lợi ích thiết thực của việc đóng gói — không phải để "đẹp mã" mà để kiểm soát được dữ liệu.

- The fields of a class can be made read-only or write-only.
- Một lớp có thể kiểm soát toàn bộ những gì được lưu trữ trong các trường của lớp đó.

## Java Encapsulation: Read-Only Class

Mục này nói về biến thể của đóng gói: class "chỉ đọc" — ai cũng được xem nhưng không ai được sửa, như bảng điểm đã niêm yết.

A read-only class can have only getter methods to get the values of the attributes, there should not be any setter method.

> 📄 **Code ví dụ:** [`examples/03-java-encapsulation-read-only-class.java`](examples/03-java-encapsulation-read-only-class.java)

## Java Encapsulation: Write-Only Class

Ngược lại với read-only là class "chỉ ghi" — bạn được nộp giấy tờ vào nhưng không được xem lại, giống hộp thư góp ý kín.

A write-only class can have only setter methods to set the values of the attributes, there should not be any getter method.

> 📄 **Code ví dụ:** [`examples/04-java-encapsulation-write-only-class.java`](examples/04-java-encapsulation-write-only-class.java)

## Example

> 📄 **Code ví dụ:** [`examples/05-example.java`](examples/05-example.java)

> **Tóm gọn 1 câu:** Encapsulation là "quầy thu ngân và chiếc két" — dữ liệu nằm trong két private, mọi thao tác phải qua cửa getter/setter để có kiểm soát.
