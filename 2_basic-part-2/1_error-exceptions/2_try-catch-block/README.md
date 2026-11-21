# Try và Catch

## Exception trong Java là gì?

Trước khi học cách "bắt" exception, bạn cần nhớ exception là gì và nó đến từ đâu — mục này ôn lại nền tảng đó trong vài dòng.

`Exception` (ngoại lệ) là một sự cố phát sinh trong quá trình chạy chương trình. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn và chương trình có thể kết thúc bất thường — vì vậy bạn cần xử lý chúng.

Lý do exception xảy ra có thể là:

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

> **Tóm gọn 1 câu:** Exception là sự cố làm gián đoạn chương trình — và try/catch chính là "lưới an toàn" để hứng nó.

## Các nhóm exception trong Java

Mục này chỉ ôn nhanh 3 nhóm exception để bạn định vị mình đang bắt loại nào; chi tiết đã có ở bài riêng.

- **Checked exceptions** — compiler kiểm tra, bắt buộc phải xử lý.
- **Unchecked exceptions** — xảy ra lúc chạy (runtime), compiler không ép xử lý.
- **Errors** — lỗi hệ thống, thường không xử lý được.

Bạn xem chi tiết từng nhóm ở bài `1_exceptions-introduce` và `8_built-in-exceptions`.

## Java try và catch

Mục này là trái tim của bài: cặp bài trùng `try` + `catch` — cách đặt "lưới" quanh đoạn code nguy hiểm và hứng sự cố rơi vào.

> Bạn bắt một exception bằng cặp từ khóa `try` và `catch`. Khối try/catch được đặt **bao quanh đoạn code có thể gây exception**.

Hãy hình dung: `try` là sân khấu nơi diễn viên có thể trượt ngã, `catch` là thảm an toàn đặt sẵn bên dưới — ngã thì đỡ, chương trình không sập.

### Khối try

- Khi exception xảy ra trong khối try, nó sẽ được xử lý bởi khối `catch` tương ứng đi kèm.
- Mỗi khối `try` phải được theo sau **ngay** bởi một khối `catch` hoặc khối `finally` — đặt `try` đứng một mình là compiler không cho phép.

### Khối catch

Nếu exception xảy ra trong protected code, khối `catch` đi sau khối `try` sẽ được kiểm tra để tìm loại exception khớp — như lễ tân so tên trên vé với danh sách khách mời: khớp ai thì người đó đón.

### Cú pháp try/catch

> 📄 **Code ví dụ:** [`examples/01-cu-phap-try-catch.java`](examples/01-cu-phap-try-catch.java)

### Ví dụ

> 📄 **Code ví dụ:** [`examples/02-vi-du.java`](examples/02-vi-du.java)

Ở đây `a[3]` gây `ArrayIndexOutOfBoundsException` (mảng chỉ có chỉ số 0 và 1), code trong `catch` chạy thay thế, rồi chương trình **tiếp tục chạy bình thường** — in ra "Out of the block". Đó chính là giá trị của try/catch: sự cố có thật nhưng chương trình không chết.

> **Tóm gọn 1 câu:** `try` bao quanh code dễ gặp sự cố, `catch` hứng sự cố đó để chương trình chạy tiếp thay vì sập.

## Nhiều khối catch (Multiple Catch Blocks)

Mục này nói về trường hợp một đoạn code có thể gặp nhiều loại sự cố khác nhau — mỗi loại cần một cách xử lý riêng, nên cần nhiều "thảm an toàn" xếp lớp.

Một khối `try` có thể đi kèm nhiều khối `catch`, mỗi khối bắt một loại exception:

> 📄 **Code ví dụ:** [`examples/03-nhieu-khoi-catch-multiple-catch-blocks.java`](examples/03-nhieu-khoi-catch-multiple-catch-blocks.java)

> 📄 **Code ví dụ:** [`examples/04-nhieu-khoi-catch-multiple-catch-blocks.java`](examples/04-nhieu-khoi-catch-multiple-catch-blocks.java)

> Lưu ý: exception xảy ra ở đâu thì các dòng sau đó trong `try` sẽ **không được thực thi** — ví dụ khi `1/b` ném `ArithmeticException`, dòng `a[3]` không chạy nữa.

## Bắt nhiều loại exception trong một catch

Từ Java 7, bạn có thể gộp nhiều loại exception vào **một** khối `catch` bằng toán tử `|` — như một cửa sổ tiếp đón nhận cả hồ sơ loại A lẫn loại B, miễn là xử lý giống nhau:

> 📄 **Code ví dụ:** [`examples/05-bat-nhieu-loai-exception-trong-mot-catch.java`](examples/05-bat-nhieu-loai-exception-trong-mot-catch.java)

> Nếu muốn dùng dạng multiple này, bạn phải chỉ rõ các loại exception cụ thể.

> 📄 **Code ví dụ:** [`examples/06-bat-nhieu-loai-exception-trong-mot-catch.java`](examples/06-bat-nhieu-loai-exception-trong-mot-catch.java)

> **Tóm gọn 1 câu:** Nhiều `catch` = nhiều thảm riêng cho từng loại ngã; nhiều exception trong một `catch` = một thảm chung cho những ngã xử lý giống nhau.

## Lưu ý

Mục này chốt hai điểm người mới hay trầy: thứ tự catch và các bài liên quan để học tiếp.

- Khối `catch` con (cụ thể hơn) phải đứng **trước** khối `catch` cha (chung hơn) — đặt `catch (Exception e)` đầu tiên sẽ khiến compiler báo lỗi "exception has already been caught". Ví von: khách mời VIP phải được đón trước, để mặc lễ tân chung đón hết thì VIP chẳng bao giờ được phục vụ riêng.
- Xem thêm: `3_try-with-resource` (tự đóng tài nguyên), `6_finally-block` (khối luôn chạy), `7_throw-error` (ném exception chủ động).
