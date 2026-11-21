# COMPILER

Vậy code bạn viết bằng Java, máy tính đọc hiểu bằng cách nào? Câu trả lời nằm ở **compiler** (trình biên dịch). Mục này nói về "người phiên dịch" đầu tiên trong chuỗi từ mã nguồn đến chương trình chạy được.

**Compiler** hay còn gọi là trình biên dịch, là một chương trình có nhiệm vụ dịch các code của một ngôn ngữ lập trình thành một chương trình tương đương của ngôn ngữ cấp thấp hơn (thường là ngôn ngữ máy). Ví von: bạn viết thư bằng tiếng Việt, compiler là phiên dịch viên dịch sang thứ tiếng mà máy tính đọc hiểu — không dịch thì thư chỉ là giấy nguệch ngoạc với máy.

> Javac chính là Compiler của Java sẽ dịch code Java sang mã bytecode,

- Bytecode chính là vị trí trung gian giữa mà nguồn (ở đây là java) và mã máy (machine code).
- Thông qua Java VM sẽ chuyển byte code sang mã máy để chạy chương trình.

Hãy hình dung chuỗi như một chuyến hàng đi xa: code Java (tiếng Việt) → bytecode (tiếng Anh "quốc tế") → JVM (người địa phương) dịch tiếp ra mã máy (tiếng bản xứ) để máy chạy được.

![alt text](image.png)

## Hello word

Phần này mổ xẻ chương trình Hello World đầu tiên để xem các dòng "lễ nghi" khai báo có ý nghĩa gì — đọc xong bạn sẽ hiểu vì sao Java bắt viết đúng những từ khóa đó.

### Public Main Class

> 📄 **Code ví dụ:** [`examples/01-public-main-class.java`](examples/01-public-main-class.java)

This line is creating a new class `MyFirstJavaProgram` and being public, this class is to be defined in the same name file as `MyFirstJavaProgram`.java. This convention helps *Java compiler* to identify the name of public class to be created before reading the file content.

Nói theo cách đời thường: `public class` giống như **bảng hiệu trước cửa tiệm** — tên trên bảng hiệu (tên class public) phải trùng với tên trên hợp đồng thuê mặt bằng (tên file `.java`), nếu không compiler tìm nhầm cửa hàng. Đó là lý do file phải có tên `MyFirstJavaProgram.java` đúng từng chữ.

### Public Static Void Main

> 📄 **Code ví dụ:** [`examples/02-public-static-void-main.java`](examples/02-public-static-void-main.java)

This line represents the main method that JVM calls when this program is loaded into memory. This method is used to execute the program. Once this method is finished, program is finished in single threaded environment.

Coi `main` như **cổng vào chính của tòa nhà**: JVM lần đầu đặt chân đến chương trình thì luôn đứng ở cổng này mà bước vào. Cổng đóng (main kết thúc) là cả tòa nhà đóng cửa (chương trình dừng, trong môi trường đơn luồng).

### Compilation and Execution

- Trong Java, quá trình biên dịch (`compilation`) chạy trước quá trình thực thi (`execution`). Trong quá trình biên dịch, mã nguồn Java được biên dịch thành mã bytecode, sau đó mã bytecode này được thực thi bởi máy ảo Java (JVM) trong quá trình thực thi.

Giống như trình diễn kịch: biên dịch là lúc **dàn dựng sân khấu** (dịch script thành đầu mục chuẩn), thực thi là lúc **diễn cho khán giả xem** (JVM chạy bytecode). Dàn dựng xong rồi mới diễn, thứ tự không thể đảo.

> **Tóm gọn 1 câu:** Compiler (`javac`) dịch code Java ra bytecode, rồi JVM mới đem bytecode đó chạy thành chương trình — hai buổi diễn, buổi trước chuẩn bị, buổi sau trình diễn.

### Runtime

Trong lập trình, "`Runtime`" thường đề cập đến `môi trường thực thi của chương trình`, bao gồm các thư viện, biến môi trường và các tài nguyên hệ thống khác mà chương trình cần để chạy. Khái niệm "Runtime" không trực tiếp liên quan đến quá trình biên dịch (compilation) mà chỉ liên quan đến quá trình thực thi (execution) của chương trình. Runtime cung cấp môi trường để chương trình có thể thực thi và tương tác với hệ thống.

Ví von: nếu compile là lúc chuẩn bị đồ trong bếp, thì runtime là lúc **khách đã ngồi vào bàn** — nhà hàng phải có đủ bàn ghế, nước, nhân viên (thư viện, tài nguyên hệ thống) thì món mới lên được.

> **Tóm gọn 1 câu:** Runtime là "suất diễn" — mọi thứ chỉ xảy ra khi chương trình đang chạy, sau khi biên dịch đã xong.

## Tài liệu liên quan

- Chi tiết cài đặt JDK và chạy Hello World: [0_setup](../../../0_setup/README.md)
- Ba tầng JDK/JRE/JVM: [0_jdk_jre_jvm](../0_jdk_jre_jvm/README.md)
