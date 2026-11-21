# if-else Statement

Mỗi chương trình đến một lúc phải **ra quyết định**: "nếu khách có vé thì cho vào, không thì trả về". Mục này nói về câu lệnh ra quyết định cơ bản nhất của Java — `if-else`.

## Java if-else Statement

- In Java, the if else statement is used to execute two code blocks based on the given condition

Nói theo cách đời thường: `if-else` là **cổng có bảo vệ** — bảo vệ nhìn "vé" (điều kiện boolean): vé thật thì mở cửa bên này, vé giả thì dẫn khách sang cửa kia. Chỉ một trong hai lối đi được chọn, không bao giờ đi cả hai.

## Syntax of if-else Statement in Java

> 📄 **Code ví dụ:** [`examples/01-syntax-of-if-else-statement-in-java.java`](examples/01-syntax-of-if-else-statement-in-java.java)

If the boolean expression evaluates to true, then the if block of code will be executed, `otherwise` else block of code will be executed.

Điều kiện trong ngoặc phải "tiêu hóa" được thành `true`/`false` — đúng như bảo vệ chỉ trả lời được câu hỏi có/không ("có vé không?"), chứ không trả lời được câu hỏi mở.

> **Tóm gọn 1 câu:** `if-else` là ngã rẽ hai lối — điều kiện đúng đi lối if, sai đi lối else, không bao giờ đi cả hai.

## Flow Diagram of if-else Statement in Java

Hình dưới đây vẽ lại đúng "ca làm việc" của bảo vệ cổng: điều kiện được kiểm tra ở hình thoi, hai mũi tên rẽ ra hai nhánh rồi lại hội tụ về một đường.

![alt text](image.png)

## Java if-else-if Statement

The if...else if...else statement is used for executing multiple code blocks based on the given conditions (Boolean expressions).

Khi có nhiều hơn hai khả năng — "vé thường? vé VIP? vé mời?" — ta xếp thành **chuỗi câu hỏi có thứ tự**: Java hỏi lần lượt từ trên xuống, câu nào khớp thì đi vào đó, những câu hỏi phía sau bỏ qua luôn. Vì vậy thứ tự các điều kiện quan trọng: đặt câu hỏi "chặt" nhất lên đầu.

> 📄 **Code ví dụ:** [`examples/02-java-if-else-if-statement.java`](examples/02-java-if-else-if-statement.java)

> **Tóm gọn 1 câu:** `if-else-if` là chuỗi trạm soát vé — soát từ trên xuống, khớp trạm nào đi trạm đó, còn lại thì về lối "else" cuối cùng.

## Java Nested if-else Statement

The nested if else statement is used for better decision-making when other conditions are to be checked when a given condition is true. In the nested if else statement, you can have an if-else statement block the another if (or, else) block.

Nested if-else là **cửa kiểm soát hai lớp**: qua cửa ngoài (vé hợp lệ) mới tới cửa trong (ghế đúng khu VIP). Điều kiện trong chỉ được hỏi khi điều kiện ngoài đã đúng — như bảo vệ sảnh không hỏi câu về ghế ngồi với khách chưa qua được cửa chính.

> 📄 **Code ví dụ:** [`examples/03-java-nested-if-else-statement.java`](examples/03-java-nested-if-else-statement.java)

Lưu ý: lồng nhiều tầng quá, code lún vào trong như "mật cung" — lồng quá 2 tầng nên cân nhắc tách hàm hoặc gộp điều kiện bằng `&&`.

> **Tóm gọn 1 câu:** Nested if-else là kiểm tra nhiều cửa nối tiếp — cửa sau chỉ mở khi cửa trước đã thông, và đừng lồng quá sâu kẻo lạc trong mật cung.
