# Java — Lớp File

## Lớp File là gì?

Mục này trả lời câu hỏi định vị quan trọng: `File` là gì, làm được gì và **không** làm được gì — hiểu nhầm chỗ này là mất mấy tiếng debug oan.

Lớp `File` trong Java (thuộc gói `java.io`) biểu diễn **đường dẫn** tới file hoặc thư mục một cách trừu tượng. Bạn dùng nó để tạo, tìm kiếm, xóa, đổi tên file/thư mục... Lưu ý: `File` chỉ làm việc với **siêu dữ liệu** (tên, kích thước, quyền), không đọc/ghi nội dung bên trong file — việc đó thuộc về các stream/reader.

Ví von: đối tượng `File` giống như **thẻ nhân thân của căn nhà** — ghi rõ địa chỉ, diện tích, chủ quyền — nhưng không phải căn nhà. Muốn xem đồ đạc bên trong (đọc/ghi nội dung) thì bạn phải bước vào nhà qua cửa (stream/reader), cầm thẻ thì chỉ biết thông tin ngoài mặt tiền.

> **Tóm gọn 1 câu:** `File` là "thẻ căn cước" của đường dẫn — quản lý thông tin bên ngoài file, còn nội dung bên trong là việc của stream/reader.

## Các constructor của lớp File

Mục này liệt kê 4 cách "sinh mã số" cho một đường dẫn — tùy bạn có sẵn đối tượng cha, chuỗi đường dẫn hay URI.

| Constructor | Ý nghĩa |
|-------------|---------|
| `File(File parent, String child)` | Tạo đối tượng File từ thư mục cha và tên con |
| `File(String pathname)` | Tạo đối tượng File từ một chuỗi đường dẫn |
| `File(String parent, String child)` | Tạo đối tượng File từ đường dẫn cha (chuỗi) và tên con |
| `File(URI uri)` | Tạo đối tượng File từ một URI dạng `file:` |

> **Cách đọc bảng này:** mỗi dòng là một cách khai báo mới; cột trái cho biết "cung cấp gì để tạo", cột phải là việc nó làm. Phổ biến nhất là dòng 2 — truyền nguyên chuỗi đường dẫn.

> 📄 **Code ví dụ:** [`examples/01-cac-constructor-cua-lop-file.java`](examples/01-cac-constructor-cua-lop-file.java)

## Các phương thức quan trọng của lớp File

Mục này là "bảng chức năng" đầy đủ của lớp `File`, chia theo nhóm việc: hỏi thông tin, hỏi quyền/trạng thái, hỏi thời gian-kích thước, tạo/xóa/đổi tên, liệt kê nội dung, và file tạm. Đọc theo nhóm sẽ đỡ choáng hơn nhiều so với học thuộc cả 30 dòng.

### Thông tin cơ bản

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 1 | `public String getName()` | Trả về tên file hoặc thư mục của đường dẫn này |
| 2 | `public String getParent()` | Trả về chuỗi đường dẫn thư mục cha, hoặc `null` nếu không có |
| 3 | `public File getParentFile()` | Trả về đối tượng File của thư mục cha, hoặc `null` nếu không có |
| 4 | `public String getPath()` | Chuyển đường dẫn trừu tượng thành chuỗi pathname |
| 5 | `public boolean isAbsolute()` | Kiểm tra đường dẫn có phải tuyệt đối không |
| 6 | `public String getAbsolutePath()` | Trả về chuỗi đường dẫn tuyệt đối |

> **Cách đọc bảng này:** các bảng dưới đây đều theo cùng khuôn mẫu — cột **Phương thức** là cái bạn gõ trong code, cột **Mô tả** là kết quả nhận về. Prefix `public` cứ bỏ qua khi đọc; chữ `boolean` đầu kết quả nghĩa là phương thức trả về đúng/sai thay vì dữ liệu.

### Kiểm tra quyền và trạng thái

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 7 | `public boolean canRead()` | Kiểm tra ứng dụng có quyền đọc file này không |
| 8 | `public boolean canWrite()` | Kiểm tra ứng dụng có quyền ghi/sửa file này không |
| 9 | `public boolean exists()` | Kiểm tra file hoặc thư mục có tồn tại thật không |
| 10 | `public boolean isDirectory()` | Kiểm tra đường dẫn này có phải là thư mục không |
| 11 | `public boolean isFile()` | Kiểm tra đường dẫn này có phải là file thông thường không |

### Thuộc tính thời gian và kích thước

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 12 | `public long lastModified()` | Trả về thời điểm sửa đổi lần cuối (mili giây từ epoch 01/01/1970), hoặc `0L` nếu file không tồn tại |
| 13 | `public long length()` | Trả về kích thước file (byte); giá trị không xác định nếu là thư mục |
| 25 | `public boolean setLastModified(long time)` | Đặt thời điểm sửa đổi lần cuối |
| 26 | `public boolean setReadOnly()` | Đánh dấu file chỉ được đọc |

### Tạo, xóa, đổi tên

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 14 | `public boolean createNewFile() throws IOException` | Tạo file rỗng mới nếu chưa tồn tại file cùng tên; trả về `true` nếu tạo thành công |
| 15 | `public boolean delete()` | Xóa file hoặc thư mục (thư mục phải rỗng); trả về `true` nếu xóa thành công |
| 16 | `public void deleteOnExit()` | Yêu cầu xóa file/thư mục khi JVM kết thúc |
| 22 | `public boolean mkdir()` | Tạo thư mục; trả về `true` nếu thành công |
| 23 | `public boolean mkdirs()` | Tạo thư mục cùng toàn bộ thư mục cha còn thiếu |
| 24 | `public boolean renameTo(File dest)` | Đổi tên (di chuyển) file |

### Liệt kê nội dung

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 17 | `public String[] list()` | Trả về mảng tên các file/thư mục con |
| 18 | `public String[] list(FilenameFilter filter)` | Liệt kê theo bộ lọc `FilenameFilter` |
| 20 | `public File[] listFiles()` | Trả về mảng đối tượng File của các file/thư mục con |
| 21 | `public File[] listFiles(FileFilter filter)` | Liệt kê theo bộ lọc `FileFilter` |

### Tạo file tạm và so sánh

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 27 | `public static File createTempFile(String prefix, String suffix, File directory) throws IOException` | Tạo file rỗng tạm thời trong thư mục chỉ định, tên sinh từ prefix/suffix |
| 28 | `public static File createTempFile(String prefix, String suffix) throws IOException` | Như trên nhưng trong thư mục tạm mặc định của hệ thống |
| 29 | `public int compareTo(File pathname)` | So sánh hai đường dẫn theo thứ tự từ điển |
| 31 | `public boolean equals(Object obj)` | Hai đường dẫn bằng nhau nếu trỏ cùng một file/thư mục |
| 32 | `public String toString()` | Trả về chuỗi pathname — giống hệt `getPath()` |

## Ví dụ tổng hợp

> 📄 **Code ví dụ:** [`examples/02-vi-du-tong-hop.java`](examples/02-vi-du-tong-hop.java)

## Lưu ý

Mục này chốt hai "cái bẫy" thực tế khi làm việc với lớp `File`.

- `File` tạo ra **đối tượng trong bộ nhớ**, không tạo file thật trên đĩa — phải gọi `createNewFile()` thì file mới xuất hiện. Giống như viết tên vào danh sách khách mời chưa có nghĩa là khách đã đến.
- Với các thao tác hiện đại (duyệt cây, copy, đọc toàn bộ file...), bạn nên ưu tiên `java.nio.file` (`Path`, `Files`) — xem bài `6_directory-operators` và `7_files-io`.

> **Tóm gọn 1 câu:** Lớp `File` = thẻ căn cước của đường dẫn: tra thông tin, hỏi quyền, tạo/xóa/đổi tên được, nhưng đọc-ghi nội dung thì phải nhờ stream, và code mới nên nghiêng về `java.nio.file`.
