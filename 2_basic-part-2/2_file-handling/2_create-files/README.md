# Java - Create File

## Create file in Java

Mục này trả lời câu hỏi đầu tiên của làm việc với file: có mấy cách để **tạo ra** một file? Ba cách phổ biến dưới đây khác nhau ở chỗ nào — và cái nào nên dùng.

We can create a file in Java using multiple ways. Following are three most popular ways to create a file in Java −

> Using FileOutputStream() constructor
> Using File.createNewFile() method // Should using
> Using Files.write() method

Ví von: tạo file giống như tạo sổ tay mới — `File.createNewFile()` là lên quầy xin quyển sổ trắng (chỉ tạo, không viết gì), còn `FileOutputStream` và `Files.write()` là vừa xin sổ vừa bút để viết ngay dòng đầu tiên.

## Create File Using FileOutputStream Constructor

Mục này dùng constructor `FileOutputStream` — vừa tạo file, vừa mở sẵn "đường ống" ghi byte vào đó.

Syntax

> 📄 **Code ví dụ:** [`examples/01-create-file-using-fileoutputstream-constructor.java`](examples/01-create-file-using-fileoutputstream-constructor.java)

Example

> 📄 **Code ví dụ:** [`examples/02-create-file-using-fileoutputstream-constructor.java`](examples/02-create-file-using-fileoutputstream-constructor.java)

## Create File Using File.createNewFile() Method

Mục này dùng cách được khuyến nghị trong ba cách trên: chỉ tạo file rỗng thật trên đĩa, không kèm đường ống ghi.

Syntax

> 📄 **Code ví dụ:** [`examples/03-create-file-using-file-createnewfile-method.java`](examples/03-create-file-using-file-createnewfile-method.java)

Example

> 📄 **Code ví dụ:** [`examples/04-create-file-using-file-createnewfile-method.java`](examples/04-create-file-using-file-createnewfile-method.java)

## Create File Using Files.write() Method

Mục này dùng `Files.write()` của gói `java.nio.file` — cách hiện đại, một dòng vừa tạo vừa ghi nội dung.

Syntax

> 📄 **Code ví dụ:** [`examples/05-create-file-using-files-write-method.java`](examples/05-create-file-using-files-write-method.java)

Example

> 📄 **Code ví dụ:** [`examples/06-create-file-using-files-write-method.java`](examples/06-create-file-using-files-write-method.java)

> **Tóm gọn 1 câu:** Ba cách tạo file = ba mức "cầm sổ hay cầm luôn bút": chỉ tạo rỗng thì dùng `createNewFile()`, tạo kèm ghi thì dùng `FileOutputStream` hoặc `Files.write()` — cách cuối gọn nhất cho code hiện đại.
