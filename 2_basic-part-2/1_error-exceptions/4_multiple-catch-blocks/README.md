# Exceptions

## Exception trong Java là gì?

Mục mở đầu này ôn nhanh nền tảng trước khi vào kỹ thuật "nhiều khối catch".

`An exception` is a problem that arises during the execution of a program. When an Exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.

Để diễn đạt bằng tiếng Việt cho dễ hình dung: exception là sự cố phát sinh khi chương trình đang chạy, làm luồng thực thi bị gián đoạn — nếu không hứng lấy mà xử lý, chương trình sẽ kết thúc bất thường.

## Vì sao exception xảy ra?

Một exception có thể xuất phát từ nhiều nguồn; mấy tình huống sau là quen thuộc nhất:

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

> Có exception do lỗi người dùng, có exception do lỗi lập trình viên, và có exception do tài nguyên vật lý gặp sự cố.

## Multiple Catch Blocks trong Java

Mục này là nội dung chính của bài: khi một đoạn code có thể gặp **nhiều loại** sự cố, bạn xếp nhiều khối `catch` — mỗi khối hứng một loại, như một dãy quầy tiếp đón chuyên từng loại hồ sơ.

Multiple catch blocks in Java are used to catch/handle multiple exceptions that may be thrown from a particular code section. A try block can have multiple catch blocks to handle multiple exceptions.

Ví von: `try` là quầy thu tiền một cửa hàng, còn các khối `catch` là các quầy khiếu nại chuyên trách — quầy thẻ ngân hàng, quầy đổi trả, quầy bảo hành. Khách mang vấn đề nào thì được dẫn tới đúng quầy đó.

> 📄 **Code ví dụ:** [`examples/01-syntax-multiple-catch-blocks.java`](examples/01-syntax-multiple-catch-blocks.java)

## Những điểm cần nhớ khi dùng nhiều khối catch

- Mỗi lần chỉ có thể xử lý một loại ngoại lệ. Trong chế độ được bảo vệ, chỉ một loại ngoại lệ sẽ được nêu ra, do đó nó sẽ chỉ được xử lý trong khối bắt có liên quan — một "vụ tai nạn" chỉ được một quầy tiếp, không hai quầy cùng giành.
- Thứ tự của khối bắt là rất quan trọng. Thứ tự phải từ ngoại lệ cụ thể đến ngoại lệ chung. Trong trường hợp khối ngoại lệ cha xuất hiện trước khối ngoại lệ con, trình biên dịch sẽ khiếu nại và sẽ đưa ra lỗi thời gian biên dịch. Ví von: xếp hàng theo "chuyên môn hẹp trước, chuyên môn rộng sau" — nếu quầy tổng hợp đứng đầu thì mọi khách đều bị nó "nuốt" mất.

### Ví dụ Multiple Catch Blocks trong Java

> 📄 **Code ví dụ:** [`examples/02-example-of-java-multiple-catch-blocks.java`](examples/02-example-of-java-multiple-catch-blocks.java)

> **Tóm gọn 1 câu:** Một `try` được phục vụ bởi nhiều `catch` — xếp catch cụ thể trước, catch chung sau, và mỗi exception chỉ rơi vào đúng một khối.

## Xử lý nhiều exception trong một khối catch duy nhất

Mục này giới thiệu cách gộp: nếu nhiều loại exception được xử lý **giống hệt nhau**, khỏi cần nhiều khối — gộp chung một khối bằng toán tử `|`.

### Cú pháp

> 📄 **Code ví dụ:** [`examples/03-syntax.java`](examples/03-syntax.java)

## Ví dụ

> 📄 **Code ví dụ:** [`examples/04-example.java`](examples/04-example.java)

> **Tóm gọn 1 câu:** Nhiều `catch` cho nhiều cách xử lý, một `catch` gộp với `|` cho một cách xử lý dùng chung — chọn theo "các lỗi này được xử lý có giống nhau không?"
