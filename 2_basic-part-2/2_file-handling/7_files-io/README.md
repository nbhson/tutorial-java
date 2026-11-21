# Java - Files and I/O

Gói java.io chứa hầu như mọi lớp bạn có thể cần để thực hiện nhập và xuất (I/O) trong Java. Hãy hình dung I/O như hệ thống ống nước của chương trình: nước (dữ liệu) chảy từ đâu vào, chảy ra đâu — và bài này giới thiệu từng loại "ống".

## Stream

Mục này định nghĩa khái niệm nền của cả bài: stream — dòng dữ liệu chảy qua chương trình của bạn.

Một luồng có thể được định nghĩa là một chuỗi dữ liệu. Có hai loại Luồng
- `InPutStream` − được sử dụng để đọc dữ liệu từ một nguồn.
- `OutPutStream` − được sử dụng để ghi dữ liệu vào một đích.

Ví von: stream chính là ống nước — `InputStream` là ống nước chảy **vào** nhà (đọc), `OutPutStream` là ống thải ra ngoài (ghi). Đứng ở góc nhìn chương trình: nguồn bên ngoài chảy vào, hoặc chương trình đẩy ra.

![alt text](image.png)

## FileInputStream/FileOutputStream (Byte Streams)

Mục này nói về loại ống chuyên chở **byte thô** — đọc/ghi từng khối 8 bit, phù hợp dữ liệu nhị phân như ảnh, âm thanh.

`Java Byte Streams` được sử dụng để thực hiện nhập và xuất các byte 8 bit. Mặc dù có nhiều lớp liên quan đến Byte Streams nhưng các lớp được sử dụng thường xuyên nhất là `FileInputStream` và `FileOutputStream`. Sau đây là một ví dụ sử dụng hai lớp này để sao chép tệp đầu vào vào tệp đầu ra −

> 📄 **Code ví dụ:** [`examples/01-fileinputstream-fileoutputstream-byte-streams.java`](examples/01-fileinputstream-fileoutputstream-byte-streams.java)

## FileReader/FileWriter (Character Streams)

Mục này nói về loại ống chuyên chở **ký tự** — hiểu bảng mã Unicode nên đọc ghi tiếng Việt, tiếng Nhật không bị "vỡ chữ".

Java` Byte streams` are used to perform input and output of **8-bit** bytes, whereas Java `Character streams` are used to perform input and output for **16-bit unicode**.

Though there are many classes related to character streams but the most frequently used classes are, `FileReader` and `FileWriter`.

Nói ngắn gọn: byte stream chở "thùng hàng không hỏi nội dung", character stream chở "đúng từng ký tự đã giải mã" — đọc file chữ thì dùng character stream để không phải tự xử lý encoding.

> 📄 **Code ví dụ:** [`examples/02-filereader-filewriter-character-streams.java`](examples/02-filereader-filewriter-character-streams.java)

> **Tóm gọn 1 câu:** Byte stream chở 8-bit thô cho dữ liệu nhị phân, character stream chở 16-bit Unicode cho văn bản — chọn ống theo "đơn hàng" bạn vận chuyển.

## BufferedInputStream / BufferedOutputStream

Mục này nói về "ống có bể chứa trung gian" — mẹo tăng tốc quen thuộc khi đọc ghi dữ liệu lớn.

là hai lớp trong Java được sử dụng để cải thiện hiệu suất đọc và ghi dữ liệu từ và đến các luồng (streams) như `FileInputStream` và `FileOutputStream` bằng cách sử dụng bộ đệm (buffer).

Ví von: thay vì múc nước từng cốc một từ giếng (mỗi lần múc là một lần đi xuống giếng), bạn đặt một cái xô trung gian — múc đầy xô một lần, rồi múc từ xô ra cốc thoải mái. Buffer chính là cái xô đó: giảm số lần "xuống giếng" (tác vụ đĩa tốn kém).

> 📄 **Code ví dụ:** [`examples/03-bufferedinputstream-bufferedoutputstream.java`](examples/03-bufferedinputstream-bufferedoutputstream.java)

## Standard Streams

Mục này giới thiệu ba ống đặc biệt có sẵn từ khi chương trình khởi động: bàn phím, màn hình và kênh báo lỗi.

Tất cả các ngôn ngữ lập trình đều cung cấp hỗ trợ cho chuẩn I/O, trong đó chương trình của người dùng có thể lấy dữ liệu đầu vào từ bàn phím rồi tạo ra đầu ra trên màn hình máy tính.

- `Standard Input` − This is used to feed the data to user's program and usually a keyboard is used as standard input stream and represented as **System.in**.
- `Standard Output` − This is used to output the data produced by the user's program and usually a computer screen is used for standard output stream and represented as **System.out**.
- `Standard Error` − This is used to output the error data produced by the user's program and usually a computer screen is used for standard error stream and represented as System.err.

Ví von: ba ống chuẩn như ba lối đi cố định của một quán ăn — cửa vào khách (System.in), cửa ra món (System.out), và lối thoát hiểm riêng cho sự cố (System.err) để lỗi không lẫn vào đĩa món chính.

> 📄 **Code ví dụ:** [`examples/04-standard-streams.java`](examples/04-standard-streams.java)

> **Tóm gọn 1 câu:** java.io cho bạn các "ống nước": byte/character stream nối tới file, buffered stream tăng tốc bằng bể chứa, còn System.in/out/err là ba ống chuẩn gắn sẵn từ lúc khởi động.