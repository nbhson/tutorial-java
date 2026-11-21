# Interface


> Interface là một kiểu dữ liệu tham chiếu trong Java. Nó là tập hợp các phương thức abstract (trừu tượng). Khi một lớp kế thừa interface, thì nó sẽ kế thừa những phương thức abstract của interface đó.

Ví von: interface giống một bản hợp đồng dịch vụ — ghi rõ "bạn phải làm được những việc gì" nhưng không viết cách làm; ai ký hợp đồng (implements) là cam kết thực hiện đủ các mục trong đó.

Mục này liệt kê các quy tắc "bất di bất dịch" của interface — đọc để biết vì sao interface lại là chìa khóa thay thế đa kế thừa và để thống nhất chuẩn code giữa các nhóm.

Một số đặc điểm của interface:

- Không thể khởi tạo, nên không có phương thức khởi tạo.
- Tất cả các phương thức trong interface luôn ở dạng public abstract mà không cần khai báo.
- Các thuộc tính trong interface luôn ở dạng public static final mà không cần khai báo, yêu cầu phải có giá trị.
- Mục đích của interface là để thay thế đa kế thừa lớp của những ngôn ngữ khác (ví dụ như C++, Python…). Ngoài ra, interface sẽ giúp đồng bộ và thống nhất trong việc phát triển hệ thống trao đổi thông tin.

Ví von cho điểm cuối: một lớp chỉ được có một "cha ruột" (extends một class), nhưng có thể có bao nhiêu "chứng chỉ nghề" (implements interface) tùy ý — nên Java dùng chứng chỉ thay cho đa kế thừa để tránh rắc rối Diamond Problem.

**Tóm lại:** một lớp chỉ được `extends` một class duy nhất, nhưng có thể `implements` bao nhiêu interface tùy ý.

> **Tóm gọn 1 câu:** Interface là "hợp đồng dịch vụ" — chỉ quy định phải làm gì, không quy định làm thế nào, và một lớp được ký bao nhiêu hợp đồng cũng được.
