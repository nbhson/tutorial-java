# Thao tác với thư mục (Directory Operations)

Một **thư mục (directory)** là một "File" đặc biệt — có thể chứa danh sách các file và thư mục khác. Bạn dùng đối tượng `File` để tạo thư mục, liệt kê các file bên trong thư mục. Chi tiết đầy đủ xem danh sách phương thức của lớp `File` trong bài `1_files`.

## Tạo thư mục

Có hai phương thức tiện ích của `File` dùng để tạo thư mục:

- `mkdir()` — tạo **một** thư mục; trả về `true` nếu thành công, `false` nếu thất bại. Thất bại xảy ra khi đường dẫn đã tồn tại, hoặc không thể tạo vì **thư mục cha chưa tồn tại**.
- `mkdirs()` — tạo thư mục **cùng toàn bộ các thư mục cha** còn thiếu.

### Ví dụ tạo thư mục

```java
package com.tutorialspoint;

import java.io.File;

public class DirectoryTest {
   public static void main(String args[]) {
      String dirname = "/tmp/user/java/bin";
      File directory = new File(dirname);

      // Create directory now.
      directory.mkdirs();

      // create new file object
      File file = new File("/tmp/user/java/bin");

      System.out.println(file.exists());      
   }
}
```

## Liệt kê (đọc) nội dung thư mục

Bạn dùng phương thức `list()` của đối tượng `File` để liệt kê tất cả file và thư mục con bên trong:

```java
package com.tutorialspoint;

import java.io.File;

public class DirectoryTest {

   public static void main(String[] args) {
      File file = null;
      String[] paths;
  
      try {      
         // create new file object
         file = new File("/tmp");

         // array of files and directory
         paths = file.list();

         // for each name in the path array
         for(String path:paths) {
            // prints filename and directory name
            System.out.println(path);
         }
      } catch (Exception e) {
         // if any error occurs
         e.printStackTrace();
      }
   }
}
```

## Xóa thư mục

Bạn dùng phương thức `delete()` của đối tượng `File` để xóa thư mục. Lưu ý: **thư mục phải rỗng** mới xóa được.

```java
package com.tutorialspoint;

import java.io.File;

public class DirectoryTest {

   public static void main(String[] args) {
      File file = new File("/tmp/user/java/bin");
      if(file.exists()) {
         boolean success = file.delete();

         if (success) {
            System.out.println("The directory has been successfully deleted."); 
         }else {
            System.out.println("The directory deletion failed.");
         }        
      }else {
         System.out.println("The directory is not present."); 
      }
   }
}
```

## Gợi ý hiện đại

Với các thao tác thư mục phức tạp hơn (duyệt cây, copy đệ quy, so sánh...), bạn nên dùng `java.nio.file`: `Files.walk()`, `Files.copy()`, `Files.delete()` với `Path` — xem bài `7_files-io`.
