# Java throws and throw

## What Is an Exception in Java?

Mục nền ôn nhanh khái niệm trước khi phân biệt `throw` và `throws`.

`An exception` is a problem that arises during the execution of a program. When an Exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.

Diễn đạt lại: exception là sự cố giữa chừng làm chương trình gián đoạn — không xử lý thì chương trình dừng bất thường.

## Why Exception Occurs?

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

> Some of these exceptions are caused by user error, others by programmer error, and others by physical resources that have failed in some manner.

## Java throws and throw

Mục này là nội dung chính của bài: hai từ khóa trông giống nhau đến mức nhiều bạn nhầm lẫn — một cái là **chủ động ném** exception, một cái là **dán cảnh báo** rằng phương thức có thể ném.

If a method does not handle a checked exception, the method must declare it using the `throws` keyword. The `throws` keyword appears at the end of a method's signature.

Try to understand the difference between throws and throw keywords,
- `throws` is used to postpone the handling of a checked exception
- `throw` is used to invoke an exception explicitly.

Ví von cho dễ nhớ: `throw` là **bóp còi báo cháy** — bạn phát hiện sự cố và chủ động gióng lên ngay tại chỗ. Còn `throws` là **tấm biển "khu này có thể cháy"** treo trước cửa — phương thức không tự xử, mà khai báo trước để ai bước vào (gọi nó) tự chuẩn bị bình chữa cháy.

## Syntax

> 📄 **Code ví dụ:** [`examples/01-syntax.java`](examples/01-syntax.java)

### Java Throws and Throw Example

### Example 1

> 📄 **Code ví dụ:** [`examples/02-example-1.java`](examples/02-example-1.java)

### Example 2

> 📄 **Code ví dụ:** [`examples/03-example-2.java`](examples/03-example-2.java)

> **Tóm gọn 1 câu:** `throw` ném exception ngay tại chỗ, `throws` chỉ treo biển cảnh báo ở chữ ký phương thức — một cái là hành động, một cái là lời khai báo.
