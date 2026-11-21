# Exceptions

## Exception trong Java là gì?

Mục nền ôn nhanh khái niệm trước khi vào cấu trúc try lồng nhau.

`An exception` is a problem that arises during the execution of a program. When an Exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.

Diễn đạt lại: exception là sự cố giữa chừng làm gián đoạn chương trình — không hứng lấy thì chương trình dừng bất thường, nên ta phải xử lý.

## Vì sao exception xảy ra?

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

> Some of these exceptions are caused by user error, others by programmer error, and others by physical resources that have failed in some manner.

## Nested Try Block

Mục này là nội dung chính của bài: đặt một khối `try` bên trong một khối `try` khác — giống như bảo hiểm tầng: phòng tuyến trong chưa đỡ được thì phòng tuyến ngoài gánh.

A try block can be nested within another try block. This structure is termed as Nested try block. Whenever an exception is raised within a nested try block, its exception is pushed to Stack. The exception propagates from child to parent try block and so on.

Hãy hình dung như tòa nhà nhiều tầng chống cháy: cháy ở một phòng thì hệ thống của phòng đó dập trước; nếu phòng không dập được, đám cháy "lan lên" và hệ thống của cả tầng, rồi cả tòa nhà mới can thiệp. Exception cũng "lan" từ try con lên try cha như vậy.

> 📄 **Code ví dụ:** [`examples/01-nested-try-block.java`](examples/01-nested-try-block.java)

## Những điểm cần nhớ khi dùng Nested Try Block

- Child catch block should have specific exception for better code clarity. Parent catch block can have more generic exception handled so that if child catch block is not able to handle the exception then parent catch block can handle it. Nói ngắn gọn: tầng trong bắt lỗi **cụ thể** (biết rõ đang xử lý cái gì), tầng ngoài bắt lỗi **chung** làm "lưới vớt" cuối cùng.
- If a exception is handled correctly in child catch block, then in parent, another exception can be raised and handled. Tức là tầng trong xử lý xong thì không "đem bán" nữa, nhưng tầng ngoài vẫn có thể gặp và xử lý một exception khác phát sinh sau đó.

> 📄 **Code ví dụ:** [`examples/02-pointer-to-remember-while-using-nested-try-block.java`](examples/02-pointer-to-remember-while-using-nested-try-block.java)

> 📄 **Code ví dụ:** [`examples/03-pointer-to-remember-while-using-nested-try-block.java`](examples/03-pointer-to-remember-while-using-nested-try-block.java)

> **Tóm gọn 1 câu:** Nested try là phòng tuyến xếp tầng: try con bắt lỗi cụ thể, không xong thì exception lan lên try cha làm lớp lưới vớt.
