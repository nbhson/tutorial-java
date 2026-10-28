# Java — Lớp File

## Lớp File là gì?

Lớp `File` trong Java (thuộc gói `java.io`) biểu diễn **đường dẫn** tới file hoặc thư mục một cách trừu tượng. Bạn dùng nó để tạo, tìm kiếm, xóa, đổi tên file/thư mục... Lưu ý: `File` chỉ làm việc với **siêu dữ liệu** (tên, kích thước, quyền), không đọc/ghi nội dung bên trong file — việc đó thuộc về các stream/reader.

## Các constructor của lớp File

| Constructor | Ý nghĩa |
|-------------|---------|
| `File(File parent, String child)` | Tạo đối tượng File từ thư mục cha và tên con |
| `File(String pathname)` | Tạo đối tượng File từ một chuỗi đường dẫn |
| `File(String parent, String child)` | Tạo đối tượng File từ đường dẫn cha (chuỗi) và tên con |
| `File(URI uri)` | Tạo đối tượng File từ một URI dạng `file:` |

```java
File f1 = new File("data.txt");                       // đường dẫn tương đối
File f2 = new File("/Users/you/docs", "report.pdf");  // thư mục cha + tên file
```

## Các phương thức quan trọng của lớp File

### Thông tin cơ bản

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 1 | `public String getName()` | Trả về tên file hoặc thư mục của đường dẫn này |
| 2 | `public String getParent()` | Trả về chuỗi đường dẫn thư mục cha, hoặc `null` nếu không có |
| 3 | `public File getParentFile()` | Trả về đối tượng File của thư mục cha, hoặc `null` nếu không có |
| 4 | `public String getPath()` | Chuyển đường dẫn trừu tượng thành chuỗi pathname |
| 5 | `public boolean isAbsolute()` | Kiểm tra đường dẫn có phải tuyệt đối không |
| 6 | `public String getAbsolutePath()` | Trả về chuỗi đường dẫn tuyệt đối |

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

```java
import java.io.File;
import java.io.IOException;

public class FileDemo {
    public static void main(String[] args) throws IOException {
        File file = new File("example.txt");

        if (file.createNewFile()) {
            System.out.println("Đã tạo: " + file.getName());
        } else {
            System.out.println("File đã tồn tại.");
        }

        System.out.println("Đường dẫn tuyệt đối: " + file.getAbsolutePath());
        System.out.println("Kích thước: " + file.length() + " bytes");
        System.out.println("Có thể ghi? " + file.canWrite());
        System.out.println("Là thư mục? " + file.isDirectory());

        // Đổi tên
        File newName = new File("renamed.txt");
        if (file.renameTo(newName)) {
            System.out.println("Đã đổi tên thành " + newName.getName());
        }

        // Xóa
        if (newName.delete()) {
            System.out.println("Đã xóa file.");
        }
    }
}
```

## Lưu ý

- `File` tạo ra **đối tượng trong bộ nhớ**, không tạo file thật trên đĩa — phải gọi `createNewFile()` thì file mới xuất hiện.
- Với các thao tác hiện đại (duyệt cây, copy, đọc toàn bộ file...), bạn nên ưu tiên `java.nio.file` (`Path`, `Files`) — xem bài `6_directory-operators` và `7_files-io`.
