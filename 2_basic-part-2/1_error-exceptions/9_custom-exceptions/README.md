# Custom Exceptions (Ngoại lệ tự định nghĩa) — throw & throws

> Bài này gốc là bài về `throw`/`throws`, nhưng trọng tâm là **tự tạo exception của riêng bạn**. Bạn xem thêm cách dùng cơ bản của `throw`/`throws` trong bài `1_exceptions-introduce`.

## Exception trong Java là gì?

`Exception` là một sự cố phát sinh trong quá trình chạy chương trình. Khi exception xảy ra, luồng thực thi bình thường bị gián đoạn, chương trình có thể kết thúc bất thường — vì vậy bạn cần xử lý chúng.

Lý do exception xảy ra có thể là:

- Người dùng nhập dữ liệu không hợp lệ.
- File cần mở không tồn tại.
- Kết nối mạng bị mất giữa chừng, hoặc JVM hết bộ nhớ.

## Custom Exception là gì?

Mục này trả lời vì sao exception có sẵn chưa đủ mà phải tự tạo — ngắn gọn: để lỗi nghiệp vụ của bạn "có tên riêng" thay vì loanh quanh với `RuntimeException("...")`.

`Custom Exception` (ngoại lệ tự định nghĩa) là việc bạn **tạo ra lớp exception của riêng mình** để mô tả đúng lỗi của bài toán. Các exception tùy chỉnh thường kế thừa từ lớp `Exception`.

Ví von: exception có sẵn như các nút báo chung trong tòa nhà (cháy, trộm), còn custom exception là hệ thống báo hiệu riêng của ngân hàng — "quầy hết tiền", "sai mã PIN" — đúng ngữ cảnh của chính mình, ai nghe cũng hiểu ngay vấn đề là gì.

## Khi nào cần custom exception?

> - Để **phân loại exception** theo từng loại lỗi riêng trong dự án của bạn.
> - Để cho phép **xử lý exception ở cấp ứng dụng** (bắt đúng loại lỗi nghiệp vụ, ví dụ "tài khoản không đủ tiền").

> **Tóm gọn 1 câu:** Custom exception là "đặt tên riêng cho lỗi của bài toán bạn" — bắt được chính xác ở cấp ứng dụng thay vì đoán mò qua message.

## Tạo custom exception trong Java

Mục này là phần kỹ thuật chính: extends lớp nào để ra loại exception nào, kèm hai ví dụ đối chiếu checked vs unchecked.

### Cú pháp

Chỉ cần `extends` lớp `Exception` có sẵn để tạo exception của riêng bạn. **Đây được coi là checked exception.**

> 📄 **Code ví dụ:** [`examples/01-cu-phap.java`](examples/01-cu-phap.java)

### Quy tắc tạo custom exception

- Mọi exception phải là con (trực tiếp hoặc gián tiếp) của `Throwable`.
- Nếu muốn tạo **checked exception** (bắt buộc phải `handle` hoặc `declare` theo Handle-or-Declare Rule), hãy kế thừa lớp `Exception` — compiler sẽ "nhắc trước deadline" như checked thông thường.
- Nếu muốn tạo **runtime exception** (không bị compiler ép xử lý), hãy kế thừa lớp `RuntimeException`.

### Ví dụ 1 — kế thừa `Exception` (checked, theo quy tắc Handle-or-Declare)

> 📄 **Code ví dụ:** [`examples/02-vi-du-1-ke-thua-exception-checked-theo.java`](examples/02-vi-du-1-ke-thua-exception-checked-theo.java)

Trong ví dụ này:

- `withdraw()` khai báo `throws InsufficientFundsException` — nơi gọi **bắt buộc** phải bắt nó trong `try/catch` (Handle) hoặc khai báo tiếp `throws` (Declare).
- `InsufficientFundsException` mang thêm dữ liệu riêng (`amount`) — điều mà exception có sẵn không làm được.

### Ví dụ 2 — kế thừa `RuntimeException` (unchecked)

> 📄 **Code ví dụ:** [`examples/03-vi-du-2-ke-thua-runtimeexception-unchecked.java`](examples/03-vi-du-2-ke-thua-runtimeexception-unchecked.java)

Ở đây `InsufficientFundsException` kế thừa `RuntimeException`, nên `withdraw()` **không cần khai báo `throws`** và nơi gọi cũng không bắt buộc phải `try/catch`. Compiler không kiểm tra — nhưng chương trình vẫn dừng nếu exception không được bắt.

## So sánh hai cách

Mục này đặt hai ví dụ cạnh nhau trên một bảng — đọc để chọn hướng kế thừa cho custom exception của bạn.

| Tiêu chí | `extends Exception` | `extends RuntimeException` |
|----------|--------------------|---------------------------|
| Loại | Checked | Unchecked |
| Compiler ép xử lý? | ✅ Bắt buộc `try/catch` hoặc `throws` | ❌ Không |
| Phù hợp khi | Lỗi nghiệp vụ mà nơi gọi nên/không thể bỏ qua | Lỗi lập trình hoặc lỗi không mong muốn lúc chạy |

> **Cách đọc bảng này:** đọc theo cột để so sánh hai "con đường" kế thừa — chọn cột nào tùy thuộc bạn muốn lỗi này có bị compiler ép xử lý hay không; cột **Phù hợp khi** là chìa khóa quyết định.

> **Tóm gọn 1 câu:** `extends Exception` cho lỗi nghiệp vụ cần được xử lý nghiêm túc, `extends RuntimeException` cho lỗi không ai xử lý nổi lúc code — chọn đường nào, compiler đối xử với bạn theo đường đó.
