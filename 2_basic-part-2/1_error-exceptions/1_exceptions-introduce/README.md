# Exceptions (Ngoại lệ)

## Exception trong Java là gì?

Vậy làm sao chương trình "gặp sự cố" và ta ứng phó ra sao? Đó chính là chủ đề exception của bài này — đọc xong bạn sẽ biết exception là gì, chia làm mấy nhóm, và Java cho ta những công cụ nào để xử lý.

`Exception` (ngoại lệ) là một sự cố phát sinh **trong quá trình chạy chương trình**. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn và chương trình có thể kết thúc bất thường — điều này không được khuyến khích, vì vậy bạn cần **xử lý (handle)** chúng.

Hãy hình dung exception giống như xe đang chạy đột nhiên gặp gạch chắn đường: nếu không có lối tắt, cả chuyến xe (chương trình) dừng ngay tại chỗ. Xử lý exception chính là bố trí sẵn lối vòng để xe chạy tiếp.

## Vì sao exception xảy ra?

Mục này liệt kê mấy tình huống đời thường hay sinh ra exception — đọc để có trực giác trước khi vào phân loại kỹ thuật bên dưới.

Exception có thể xảy ra vì nhiều lý do khác nhau. Một số tình huống điển hình:

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

> Có exception do lỗi người dùng, có exception do lỗi lập trình viên, và có exception do tài nguyên vật lý gặp sự cố.

## Các nhóm exception trong Java

Mục này nói về cách Java phân loại exception thành 3 nhóm — đọc để biết nhóm nào compiler "ép" xử lý, nhóm nào không, vì cách đối phó mỗi nhóm là khác nhau.

Java chia exception thành 3 nhóm chính:

- **Checked exceptions**
- **Unchecked exceptions**
- **Errors**

Ví von: checked exception là "lời nhắc trước deadline" (compiler nhắc trước khi chạy), unchecked là "chấn thương ngoài sân" (xảy ra lúc chạy mới biết), còn Error là "động đất" — không phải lỗi của bạn và cũng không có gì để sửa.

### Checked Exceptions

`Checked exception` là exception được **compiler kiểm tra ngay lúc biên dịch** (nên còn gọi là compile-time exception). **Bạn không thể bỏ qua chúng — bắt buộc phải bắt (try/catch) hoặc khai báo ném ra (throws).** Giống như nhà xe bắt buộc bạn mua vé trước khi vào: không mua là không cho qua.

> 📄 **Code ví dụ:** [`examples/01-checked-exceptions.java`](examples/01-checked-exceptions.java)

Compiler báo lỗi vì `FileReader` có thể ném `FileNotFoundException` mà bạn chưa xử lý.

### Unchecked Exceptions

- `Unchecked exception` là exception xảy ra tại **thời điểm chạy (runtime)**.
- Thường do lỗi lập trình: sai logic, dùng API không đúng cách.
- **Compiler bỏ qua, không ép bạn xử lý.** Compiler tin bạn "tự biết đường" — như cha mẹ không nhắc bạn thở, nhưng bạn quên thở thì vẫn ngất.

> 📄 **Code ví dụ:** [`examples/02-unchecked-exceptions.java`](examples/02-unchecked-exceptions.java)

> **Tóm gọn 1 câu:** Checked là compiler nhắc trước, unchecked là lúc chạy mới "tai nạn", Error là sự cố ngoài tầm với — hiểu nhóm nào bạn mới biết cách phòng.

### Errors

Đây **không phải exception** mà là những vấn đề phát sinh ngoài tầm kiểm soát của người dùng hoặc lập trình viên (ví dụ: `StackOverflowError`, `OutOfMemoryError`) — kiểu mất điện cả khu phố, bạn không thể "catch" cho có điện. Lỗi thường bị bỏ qua trong mã của bạn vì bạn hiếm khi có thể làm gì khi chúng xảy ra. Chúng cũng chỉ bộc lộ lúc chạy chương trình, không bị compiler kiểm tra.

> Trong Java, quá trình **biên dịch (compilation)** chạy trước quá trình **thực thi (execution)**: mã nguồn được dịch thành bytecode, sau đó JVM thực thi bytecode đó.

![alt text](image.png)

## Xử lý exception trong Java

Mục này giới thiệu hộp công cụ xử lý exception của Java: try/catch để bắt, try-with-resources để tự đóng tài nguyên, và throw/throws để ném — đọc để định hướng trước khi đi sâu từng bài riêng.

### Try/catch

Bạn bắt exception bằng cặp từ khóa `try` và `catch`. Hãy nghĩ như đội cứu hộ đứng canh: `try` là khu vực "nếu có sự cố cứ hô", còn `catch` là đội được cử đến xử lý. Khối try/catch đặt **bao quanh đoạn code có thể gây exception** — đoạn code này được gọi là *protected code*:

> 📄 **Code ví dụ:** [`examples/03-try-catch.java`](examples/03-try-catch.java)

Chi tiết từng dạng bạn xem ở các bài riêng: `2_try-catch-block`, `4_multiple-catch-blocks`, `6_finally-block`.

### Try-with-resources

Thông thường, khi dùng tài nguyên như stream, connection..., bạn phải đóng chúng một cách tường minh trong khối `finally` — giống như nhớ khóa cửa mỗi lần ra khỏi nhà. Trong chương trình sau, chúng ta đọc dữ liệu từ file bằng `FileReader` rồi đóng nó trong `finally`:

> 📄 **Code ví dụ:** [`examples/04-try-with-resources.java`](examples/04-try-with-resources.java)

Từ Java 7, cú pháp **try-with-resources** cho phép tự động đóng tài nguyên khi ra khỏi khối `try` — như cửa tự động khóa sau khi bạn bước ra, không cần nhớ gì cả:

> 📄 **Code ví dụ:** [`examples/05-try-with-resources.java`](examples/05-try-with-resources.java)

> 📄 **Code ví dụ:** [`examples/06-try-with-resources.java`](examples/06-try-with-resources.java)

> **Tóm gọn 1 câu:** Java cho 3 công cụ xử lý exception: try/catch là đội cứu hộ, try-with-resources là cửa tự khóa, còn throw/throws là còi báo động bạn chủ động gióng lên.

### Throws / Throw

- `throw` — ném một exception cụ thể ra khỏi phương thức, giống bấm chuông báo cháy khi phát hiện sự cố.
- `throws` — khai báo rằng phương thức này có thể ném loại exception đó, đẩy việc xử lý cho nơi gọi — như dán biển "khu vực này có thể cháy" để ai vào thì tự chuẩn bị bình chữa cháy.

> 📄 **Code ví dụ:** [`examples/07-throws-throw.java`](examples/07-throws-throw.java)

## Các phương thức quan trọng của lớp Exception

Mục này liệt kê các phương thức bạn dùng để "hỏi chuyện" một exception: nó nói gì, do đâu, và nó đi qua những đâu. Quen thuộc nhất là `getMessage()` khi in log lỗi.

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 1 | `public String getMessage()` | Trả về thông điệp chi tiết của exception — được khởi tạo trong constructor của `Throwable` |
| 2 | `public Throwable getCause()` | Trả về nguyên nhân gốc của exception (dạng một đối tượng `Throwable`) |
| 3 | `public String toString()` | Trả về tên lớp nối với kết quả của `getMessage()` |
| 4 | `public void printStackTrace()` | In kết quả của `toString()` cùng stack trace ra `System.err` |
| 5 | `public StackTraceElement[] getStackTrace()` | Trả về mảng các phần tử trong stack trace (phần tử 0 là đỉnh ngăn xếp gọi hàm) |
| 6 | `public Throwable fillInStackTrace()` | Ghi đè stack trace của đối tượng này bằng stack trace hiện tại |

> **Cách đọc bảng này:** mỗi dòng là một phương thức của đối tượng exception; cột **Mô tả** cho biết "hỏi được gì". Lúc debug, hãy đọc theo thứ tự nhu cầu: muốn biết lỗi là gì → dòng 1, muốn biết do đâu → dòng 2, muốn biết lỗi đi qua những hàm nào → dòng 4–5.

> **Tóm gọn 1 câu:** Đối tượng exception là "hộp đen" của sự cố — `getMessage`, `getCause` và `printStackTrace` là ba nút ghi âm bạn mở lên để điều tra.

## Tóm tắt

Mục này gom cả bài thành một bảng 3 dòng để ôn nhanh trước khi sang các bài chi tiết.

| Nhóm | Thời điểm phát hiện | Bắt buộc xử lý? | Ví dụ |
|------|--------------------|-----------------|-------|
| Checked | Lúc biên dịch | ✅ Có (`try/catch` hoặc `throws`) | `IOException`, `SQLException` |
| Unchecked | Lúc chạy | ❌ Không ép | `NullPointerException`, `ArithmeticException` |
| Error | Lúc chạy | Không xử lý được | `StackOverflowError`, `OutOfMemoryError` |

> **Cách đọc bảng này:** đọc theo từng dòng là một nhóm exception, so sánh cột "Thời điểm phát hiện" và "Bắt buộc xử lý?" giữa các dòng để thấy khác biệt cốt lõi: chỉ có Checked là compiler ép xử lý.

> **Tóm gọn 1 câu:** Exception là sự cố giữa chừng — checked phải xử lý trước, unchecked nổ lúc chạy, Error thì đành chịu; việc của bạn là bắt bằng try/catch hoặc khai báo bằng throws.

