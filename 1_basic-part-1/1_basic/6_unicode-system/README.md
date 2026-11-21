# Unicode System

Vậy làm sao một chương trình Java chạy được với tiếng Việt, tiếng Nhật hay emoji — những ký tự ngoài bảng A-Z? Câu trả lời là **Unicode**: Java "khai sinh" đã nói được mọi ngôn ngữ.

> Java programming language, being platform-independent, has built-in support for Unicode characters, allowing developers to create applications that can work seamlessly with diverse languages and scripts.

Nói theo cách đời thường: nếu ASCII là **quán ăn chỉ có menu tiếng Anh**, thì Unicode là **buffet quốc tế** — hơn 140.000 món (ký tự) từ chữ "ờ" tiếng Việt đến tiếng Nhật, tiếng Ả Rập. Java dùng Unicode làm bảng mã mặc định nên bạn viết `"Xin chào"` hay `"こんにちは"` vào code đều chạy được, bất kể máy nào.

Bảng dưới đây liệt kê 4 cách "gọi món" từ buffet Unicode trong code Java — mỗi mục một cách, từ gõ dấu escape cho đến gán trực tiếp.

## Using Unicode Escape Sequences

Mục này cho cách viết ký tự Unicode bằng "mã shorthand" `\uXXXX` — hữu ích khi bàn phím không gõ được ký tự đó.

Ví von: `\uXXXX` giống như **gọi món bằng số thứ tự trong menu** — không cần đọc đúng tên món (ký tự), chỉ cần báo mã là nhà hàng mang đúng món ra.

> 📄 **Code ví dụ:** [`examples/01-using-unicode-escape-sequences.java`](examples/01-using-unicode-escape-sequences.java)

> **Tóm gọn 1 câu:** Escape sequence `\uXXXX` là "mã số món ăn" — ký tự nào không gõ được trên bàn phím thì gọi bằng mã.

## Storing Unicode Values Directly

Mục này cho cách gõ thẳng ký tự Unicode vào chuỗi, không cần qua mã escape.

Giống như **chỉ thẳng tay viết tên món** thay vì gọi bằng số — đọc code lên là thấy ngay chữ gì, dễ hiểu nhất cho người sau đọc lại.

> 📄 **Code ví dụ:** [`examples/02-storing-unicode-values-directly.java`](examples/02-storing-unicode-values-directly.java)

> **Tóm gọn 1 câu:** Gõ trực tiếp ký tự vào chuỗi khi được — code dễ đọc hơn hẳn dãy mã `\uXXXX` rợn mắt.

## Assigning Unicode Values to Variables

Mục này đưa giá trị Unicode (dưới dạng số) vào biến `char` — cho thấy char thực chất đang lưu một mã số.

Ví von: `char` như **chỗ ngồi đánh số trong rạp hát** — bạn gán số ghế (mã Unicode), và ký tự tương ứng "ngồi" vào đó. Char thật ra là số khoác áo ký tự.

> 📄 **Code ví dụ:** [`examples/03-assigning-unicode-values-to-variables.java`](examples/03-assigning-unicode-values-to-variables.java)

> **Tóm gọn 1 câu:** Mỗi ký tự Unicode đứng sau một con số — gán số vào `char` là bạn đã "xếp ghế" cho ký tự đó.

##  Assigning Unicode Characters and Values to Variables

Mục này kết hợp cả hai cách trên trong cùng một chương trình, để bạn đối chiếu: cùng một ký tự, viết bằng chữ hay bằng số đều cho kết quả như nhau.

Giống như **hai người gọi cùng một món — một người đọc tên, một người báo số** — bếp vẫn ra đúng một đĩa như nhau. Đây là bài tập đối chiếu giúp chốt: ký tự và mã số của nó là hai mặt của cùng một đồng xu.

> 📄 **Code ví dụ:** [`examples/04-assigning-unicode-characters-and-values-to-variables.java`](examples/04-assigning-unicode-characters-and-values-to-variables.java)

> **Tóm gọn 1 câu:** Ký tự hay mã số — hai cách viết, một kết quả; hiểu được điều này là bạn đã nắm bản chất Unicode trong Java.
