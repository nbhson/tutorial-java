# Try-with-resources

## Exception trong Java là gì?

Mục nền này ôn lại "tại sao có exception" để bạn hiểu try-with-resources sinh ra để giải bài toán nào.

`Exception` (ngoại lệ) là một sự cố phát sinh trong quá trình chạy chương trình. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn, chương trình có thể kết thúc bất thường — vì vậy bạn cần xử lý chúng.

Lý do exception xảy ra có thể là: người dùng nhập dữ liệu không hợp lệ, file cần mở không tồn tại, kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ. Chi tiết các nhóm exception (checked/unchecked/error) bạn xem ở bài `1_exceptions-introduce`.

> **Tóm gọn 1 câu:** Exception là sự cố lúc chạy — và với bài này, sự cố đáng sợ nhất là quên đóng tài nguyên sau khi mở.

## Try-with-resources

Mục này trả lời câu hỏi cốt lõi: làm sao chắc chắn file/socket/connection được đóng, kể cả khi chương trình văng exception giữa chừng?

> Câu lệnh try-with-resources trong Java là câu lệnh try được sử dụng để khai báo một hoặc nhiều **tài nguyên** như streams, sockets, database connections... Các tài nguyên này **phải được đóng** khi chương trình hoàn tất. Câu lệnh try-with-resources sẽ đóng các tài nguyên ở cuối khối try **một cách tự động**.

Ví von: mở tài nguyên giống như mượn sách thư viện — try-with-resources là thủ thư tự động thu sách lại ngay khi bạn rời bàn đọc, kể cả khi bạn bị gọi gấp ra giữa chừng. Quên `close()` thủ công thì sách cứ bị "mượn mãi" — ở chương trình thật, đó là file bị khóa, connection không trả về pool.

Tính năng try-with-resources được giới thiệu từ **Java 7**. Nó có thể thay thế cho mô hình try-catch-finally khi làm việc với các đối tượng tài nguyên.

> **Tóm gọn 1 câu:** try-with-resources = khai báo tài nguyên trong ngoặc `try(...)` để Java tự đóng giúp bạn — khỏi cần `finally` + `close()`.

## Cú pháp

> 📄 **Code ví dụ:** [`examples/01-cu-phap.java`](examples/01-cu-phap.java)

## Ví dụ

> 📄 **Code ví dụ:** [`examples/02-vi-du.java`](examples/02-vi-du.java)

Khi ra khỏi khối `try` (bình thường hoặc do exception), `fr.close()` được gọi **tự động** — bạn không cần viết `finally` để đóng tài nguyên.

## Try-with-resources với nhiều tài nguyên

Mục này cho thấy bạn có thể "mượn" nhiều thứ cùng lúc và chúng được thu về theo thứ tự ngược lại — vào sau thì ra trước, như chồng đĩa xếp lên nhau.

> 📄 **Code ví dụ:** [`examples/03-try-with-resources-voi-nhieu-tai-nguyen.java`](examples/03-try-with-resources-voi-nhieu-tai-nguyen.java)

Sau khi kết thúc khối try, các tài nguyên sẽ tự động được đóng mà không cần gọi `close()` rõ ràng. Nếu có lỗi xảy ra trong quá trình đọc file, nó sẽ được bắt và xử lý trong khối catch.

### Không dùng try-with-resources (cách cũ, dài dòng hơn)

> 📄 **Code ví dụ:** [`examples/04-khong-dung-try-with-resources-cach-cu-dai.java`](examples/04-khong-dung-try-with-resources-cach-cu-dai.java)

> **Tóm gọn 1 câu:** Một hay nhiều tài nguyên đi kèm `try(...)` đều được tự đóng theo thứ tự ngược khai báo — so với cách cũ trong ví dụ 04, bạn thấy ngay mình tiết kiệm được bao nhiêu dòng.

## Điều kiện để dùng

Mục này chốt "luật chơi" của try-with-resources: cái gì mới được tự đóng, và chúng được đóng theo trật tự nào.

- Tài nguyên khai báo trong khối `try(...)` phải triển khai interface `AutoCloseable` (có phương thức `close()`) — như chỉ ai có thẻ thành viên mới được dùng dịch vụ tự động trả đồ.
- Các tài nguyên được đóng theo **thứ tự ngược lại** với thứ tự khai báo.
- Bạn vẫn được khai báo biến bên ngoài và tham chiếu vào try-with-resources nếu cần (Java 9+ cho phép dùng biến `final`/effectively final đã khai báo sẵn).

> **Tóm gọn 1 câu:** Muốn tự động đóng thì phải là `AutoCloseable`; nhiều tài nguyên thì đóng ngược thứ tự khai báo — nhớ hai chữ "Auto" và "ngược" là đủ.
