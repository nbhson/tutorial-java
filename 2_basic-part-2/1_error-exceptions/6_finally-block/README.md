# Exceptions

## Exception trong Java là gì?

Mục nền ôn nhanh khái niệm trước khi vào khối `finally`.

`An exception` is a problem that arises during the execution of a program. When an Exception occurs the normal flow of the program is disrupted and the program/Application terminates abnormally, which is not recommended, therefore, these exceptions are to be handled.

Diễn đạt lại: exception là sự cố giữa chừng làm chương trình gián đoạn — không xử lý thì chương trình dừng bất thường.

## Vì sao exception xảy ra?

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

> Some of these exceptions are caused by user error, others by programmer error, and others by physical resources that have failed in some manner.

## Finally

Mục này trả lời câu hỏi: có việc gì bắt buộc phải làm **dù thành công hay thất bại** không? Câu trả lời là khối `finally` — việc dọn dẹp không được bỏ qua.

The finally block follows a try block or a catch block. `A finally block of code always executes,` irrespective of occurrence of an Exception.

Ví von: `finally` như nhân viên rạp phim tắt đèn và dọn ghế sau mỗi suất chiếu — dù phim hay, phim dở, hay mất điện giữa buổi, công việc dọn dẹp vẫn phải chạy. Đó là chỗ đứng duy nhất trong code mà bạn coi là "chắc chắn được thực thi".

> 📄 **Code ví dụ:** [`examples/01-finally.java`](examples/01-finally.java)

# Points To Remember While Using Finally Block

- Không bắt buộc phải có mệnh đề cuối cùng bất cứ khi nào có khối thử/bắt.
- Khối `finally` không được thực thi trong trường hợp phương thức exit() được gọi trước khối cuối cùng hoặc xảy ra lỗi nghiêm trọng khi thực thi chương trình — chỉ khi JVM "tắt máy" hẳn thì nhân viên dọn dẹp mới không kịp làm việc.
- `finally` block is executed even method returns a value before finally block.

# Why Java Finally Block Used?

- Java finally block can be used for clean-up (closing) the connections, files opened, streams, etc. those must be closed before exiting the program — đúng vai trò "trả đồ sau khi mượn, bất kể có làm xong việc hay không".
- It can also be used to print some final information.

## Example

> 📄 **Code ví dụ:** [`examples/02-example.java`](examples/02-example.java)

> 📄 **Code ví dụ:** [`examples/03-example.java`](examples/03-example.java)

> **Tóm gọn 1 câu:** `finally` là "việc dọn dẹp bắt buộc" — chạy bất kể exception có xảy ra hay không, chỉ trừ khi JVM tắt hẳn (exit hoặc lỗi nghiêm trọng).
