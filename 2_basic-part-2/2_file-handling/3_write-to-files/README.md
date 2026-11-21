# Java - Write File

## Write to file in Java

Mục này trả lời: file đã có rồi, giờ **ghi nội dung vào** thì có mấy cách? Ba cách phổ biến dưới đây, mỗi cách một "chiếc bút" khác nhau.

We can write to a file in Java using multiple ways. Following are three most popular ways to create a file in Java −

> Using FileOutputStream() constructor
> Using FileWriter.write() method
> Using Files.write() method

Ví von: ghi file giống viết thư — `FileWriter` là cây bút viết từng dòng chữ, `FileOutputStream` là bút "viết bằng số" cho dữ liệu nhị phân, còn `Files.write()` là dịch vụ gửi thư gọn nhẹ một phát là xong.

## Write To File Using FileWriter.write() Method

Mục này đi chi tiết cách được dùng nhiều nhất cho văn bản: `FileWriter` — mở file ở chế độ ghi, mỗi lần `write()` là một lần nét bút chạm giấy.

Syntax

> 📄 **Code ví dụ:** [`examples/01-write-to-file-using-filewriter-write-method.java`](examples/01-write-to-file-using-filewriter-write-method.java)

Example

> 📄 **Code ví dụ:** [`examples/02-write-to-file-using-filewriter-write-method.java`](examples/02-write-to-file-using-filewriter-write-method.java)

> **Tóm gọn 1 câu:** Ghi file = chọn đúng "chiếc bút": `FileWriter` cho chữ, `FileOutputStream` cho byte, `Files.write()` cho thao tác một dòng gọn gàng — bài này tập trung vào `FileWriter`.
