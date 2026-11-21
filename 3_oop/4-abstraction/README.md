# Abstraction

> Trừu tượng là việc xử lý các ý tưởng hơn là các sự kiện. 

Ví dụ, khi bạn xem xét trường hợp của e-mail, các chi tiết phức tạp như những gì xảy ra ngay khi bạn gửi e-mail, **giao thức mà máy chủ e-mail của bạn sử dụng được ẩn khỏi người dùng.** Do đó, để gửi e-mail, bạn chỉ cần nhập nội dung, đề cập đến địa chỉ của người nhận và nhấp vào gửi. Bạn không cần biết phía sau "cỗ máy" làm gì — đó chính là tinh thần của abstraction.

## Java Abstraction

Mục này định nghĩa abstraction trong Java và chỉ ra 2 công cụ để đạt được nó: abstract class và interface — nền tảng để hiểu DI và Spring Boot về sau.

`Abstraction` là quá trình ẩn các chi tiết triển khai khỏi người dùng, chỉ cung cấp chức năng cho người dùng. Nói cách khác, người dùng sẽ có thông tin về những gì đối tượng thực hiện thay vì cách thức thực hiện. Trong lập trình Java,  abstraction is achieved using `Abstract classes` and `interfaces.` Ví von: abstraction giống như lái xe — bạn chỉ cần biết vô lăng, chân ga, chân phanh, còn hộp số, xăng chảy thế nào là chuyện của thợ máy.

> **Tóm gọn 1 câu:** Abstraction là "điều khiển từ xa" — bạn bấm nút để nhận kết quả, còn mạch điện bên trong thì được che đi.

## Java Abstract Classes

Mục này nói về abstract class: "bản thiết kế dang dở" — có thể có sẵn vài phần làm rồi, nhưng buộc lớp con phải hoàn thiện phần còn lại. Đọc để nắm 5 quy tắc quan trọng của nó.

- Các lớp trừu tượng Java có thể chứa hoặc không chứa các phương thức trừu tượng, tức là các phương thức không có phần thân (public void get(); )
- Tuy nhiên, nếu một lớp có ít nhất một phương thức trừu tượng thì lớp đó **phải được khai báo là trừu tượng.**
- Nếu một lớp được khai báo là abstract, thì nó không thể được khởi tạo.
- Để sử dụng một abstract class, bạn phải kế thừa nó từ một lớp khác, cung cấp các implementations cho các phương thức trừu tượng trong đó.
- Nếu bạn kế thừa một lớp trừu tượng, bạn phải cung cấp các triển khai cho tất cả các phương thức trừu tượng trong đó.

Ví von: abstract class giống một căn nhà thô — đã có tường, có mái, nhưng bắt buộc thợ (lớp con) mới hoàn thiện được; bạn không thể "vào ở" (khởi tạo) một căn nhà thô.

## Example: Java Abstract Class

> 📄 **Code ví dụ:** [`examples/01-example-java-abstract-class.java`](examples/01-example-java-abstract-class.java)

Bạn có thể thấy rằng ngoại trừ các phương thức trừu tượng, lớp Employee giống với lớp bình thường trong Java. Lớp này hiện là trừu tượng, nhưng vẫn có ba trường, bảy phương thức và một hàm tạo.

Now you can try to instantiate the Employee class in the following way −

> 📄 **Code ví dụ:** [`examples/02-example-java-abstract-class.java`](examples/02-example-java-abstract-class.java)

When you compile the above class, it gives you the following error −

> 📄 **Code ví dụ:** [`examples/03-example-java-abstract-class.sh`](examples/03-example-java-abstract-class.sh)

## Inheriting the Java Abstract Class

Mục này cho thấy lối ra duy nhất để dùng được abstract class: xây một lớp con cụ thể "hoàn thiện căn nhà thô", rồi khởi tạo lớp con đó.

We can inherit the properties of Employee class just like concrete class in the following way −

> 📄 **Code ví dụ:** [`examples/04-inheriting-the-java-abstract-class.java`](examples/04-inheriting-the-java-abstract-class.java)

Tại đây, bạn không thể khởi tạo lớp Employee, nhưng bạn có thể khởi tạo lớp Salary và sử dụng phiên bản này, bạn có thể truy cập tất cả ba trường và bảy phương thức của lớp Employee như được hiển thị bên dưới.

> 📄 **Code ví dụ:** [`examples/05-inheriting-the-java-abstract-class.java`](examples/05-inheriting-the-java-abstract-class.java)

## Java Abstract Methods

Mục này nói về "hợp đồng bắt buộc" mà lớp cha ký: phương thức trừu tượng chỉ có chữ ký, không có thân — lớp con nào nhận hợp đồng thì phải hoàn thành.

Nếu bạn muốn một lớp chứa một phương thức cụ thể nhưng muốn việc triển khai thực tế của phương thức đó được xác định bởi các lớp con, bạn có thể khai báo phương thức đó trong lớp cha dưới dạng trừu tượng.

- abstract keyword is used to declare the method as abstract.
- Bạn phải đặt từ khóa abstract trước tên phương thức trong phần khai báo phương thức.
- Một phương thức trừu tượng chứa chữ ký phương thức nhưng không có thân phương thức
- Thay vì dấu ngoặc nhọn, phương thức trừu tượng sẽ có dấu chấm phẩy (;) ở cuối.

### Example 1: Implementing Abstract Method in Java

> 📄 **Code ví dụ:** [`examples/06-example-1-implementing-abstract-method-in-java.java`](examples/06-example-1-implementing-abstract-method-in-java.java)

Việc khai báo một phương thức là trừu tượng có hai hậu quả −

- Lớp chứa nó phải được khai báo là trừu tượng.
- Bất kỳ lớp nào kế thừa lớp hiện tại đều phải ghi đè phương thức trừu tượng hoặc tự khai báo là trừu tượng.

Lưu ý − Cuối cùng, lớp con phải triển khai phương thức trừu tượng; nếu không, bạn sẽ có một hệ thống phân cấp các lớp trừu tượng không thể khởi tạo được — giống như chuỗi người nhận ủy nhiệm cứ chuyển tiếp hợp đồng cho nhau mà không ai chịu làm việc, cuối cùng chẳng ai có kết quả để giao.


Giả sử lớp Salary kế thừa lớp Employee, thì nó phải triển khai phương thức computePay() như được hiển thị bên dưới −

> 📄 **Code ví dụ:** [`examples/07-example-1-implementing-abstract-method-in-java.java`](examples/07-example-1-implementing-abstract-method-in-java.java)

### Example 2: Implementing Abstract Method in Java 

> 📄 **Code ví dụ:** [`examples/08-example-2-implementing-abstract-method-in-java.java`](examples/08-example-2-implementing-abstract-method-in-java.java)

> **Tóm gọn 1 câu:** Abstract class là "căn nhà thô bắt buộc hoàn thiện" — có sẵn một phần, nhưng lớp con phải lấp nốt các phương thức trừu tượng trước khi dùng được.
