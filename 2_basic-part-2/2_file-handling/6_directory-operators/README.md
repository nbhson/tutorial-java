# Thao tác với thư mục (Directory Operations)

Một **thư mục (directory)** là một "File" đặc biệt — có thể chứa danh sách các file và thư mục khác. Hãy hình dung thư mục như chiếc hộp của hộp: vẫn là đồ vật, nhưng đồ đạc của nó là những hộp nhỏ hơn. Bạn dùng đối tượng `File` để tạo thư mục, liệt kê các file bên trong thư mục. Chi tiết đầy đủ xem danh sách phương thức của lớp `File` trong bài `1_files`.

## Tạo thư mục

Mục này nói về việc "đặt hộp mới" vào hệ thống file — hai phương thức `mkdir` khác nhau ở chỗ: có tự tạo hộp cha còn thiếu hay không.

Có hai phương thức tiện ích của `File` dùng để tạo thư mục:

- `mkdir()` — tạo **một** thư mục; trả về `true` nếu thành công, `false` nếu thất bại. Thất bại xảy ra khi đường dẫn đã tồn tại, hoặc không thể tạo vì **thư mục cha chưa tồn tại** — như muốn đặt hộp vào kệ mà kệ chưa dựng thì không đặt được.
- `mkdirs()` — tạo thư mục **cùng toàn bộ các thư mục cha** còn thiếu — dựng kệ luôn nếu chưa có.

### Ví dụ tạo thư mục

> 📄 **Code ví dụ:** [`examples/01-vi-du-tao-thu-muc.java`](examples/01-vi-du-tao-thu-muc.java)

> **Tóm gọn 1 câu:** `mkdir()` đòi cha phải có sẵn, `mkdirs()` tự dựng cả cây tổ chức — cần tạo đường dẫn sâu thì dùng `mkdirs()` cho lành.

## Liệt kê (đọc) nội dung thư mục

Mục này cho thấy cách "mở hộp ra xem có gì bên trong" — trả về danh sách tên các mục con.

Bạn dùng phương thức `list()` của đối tượng `File` để liệt kê tất cả file và thư mục con bên trong:

> 📄 **Code ví dụ:** [`examples/02-liet-ke-oc-noi-dung-thu-muc.java`](examples/02-liet-ke-oc-noi-dung-thu-muc.java)

## Xóa thư mục

Mục này là chỗ nhiều người trầy nhất: xóa thư mục **chỉ thành công khi nó rỗng**.

Bạn dùng phương thức `delete()` của đối tượng `File` để xóa thư mục. Lưu ý: **thư mục phải rỗng** mới xóa được — giống như quy định "chỉ được đập hộp khi đã dọn hết đồ bên trong".

> 📄 **Code ví dụ:** [`examples/03-xoa-thu-muc.java`](examples/03-xoa-thu-muc.java)

> **Tóm gọn 1 câu:** Ba thao tác với thư mục đều qua đối tượng `File`: tạo (`mkdir`/`mkdirs`), xem (`list`), xóa (`delete` — chỉ ăn khi thư mục rỗng).

## Gợi ý hiện đại

Mục này chỉ đường cho code mới: khi thao tác thư mục phức tạp hơn tạo-xem-xóa, hãy đổi sang `java.nio.file`.

Với các thao tác thư mục phức tạp hơn (duyệt cây, copy đệ quy, so sánh...), bạn nên dùng `java.nio.file`: `Files.walk()`, `Files.copy()`, `Files.delete()` với `Path` — xem bài `7_files-io`.
