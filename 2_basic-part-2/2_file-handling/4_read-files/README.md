# Java - Reading File

## Reading to file in Java

Mục này trả lời: file có sẵn nội dung rồi, làm sao **đọc ra** chương trình? Ba cách phổ biến dưới đây, mỗi cách phù hợp một loại dữ liệu.

We can read a file in Java using multiple ways. Following are three most popular ways to create a file in Java −

> Using FileInputStream() constructor
> Using FileReader.read() method
> Using Files.readAllLines() method

Ví von: đọc file giống đọc sách — `FileReader` là đọc từng dòng bằng mắt thường (dành cho chữ), `FileInputStream` là soi từng điểm ảnh (dành cho dữ liệu nhị phân như ảnh, âm thanh), còn `Files.readAllLines()` là chụp cả quyển sách thành danh sách dòng một phát là xong.

## Reading File Using FileReader.read() Method

Mục này đi chi tiết cách đọc phổ biến nhất cho file văn bản: `FileReader` — đọc từng ký tự, phù hợp khi bạn xử lý từng dòng chữ trong file.

Syntax

> 📄 **Code ví dụ:** [`examples/01-reading-file-using-filereader-read-method.java`](examples/01-reading-file-using-filereader-read-method.java)

Example

> 📄 **Code ví dụ:** [`examples/02-reading-file-using-filereader-read-method.java`](examples/02-reading-file-using-filereader-read-method.java)

> **Tóm gọn 1 câu:** Đọc file = chọn đúng "cách đọc sách": `FileReader` cho văn bản, `FileInputStream` cho nhị phân, `Files.readAllLines()` cho file nhỏ muốn đọc nguyên một phát.
