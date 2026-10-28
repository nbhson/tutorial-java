# Interface


> Interface là một kiểu dữ liệu tham chiếu trong Java. Nó là tập hợp các phương thức abstract (trừu tượng). Khi một lớp kế thừa interface, thì nó sẽ kế thừa những phương thức abstract của interface đó.

Một số đặc điểm của interface:

- Không thể khởi tạo, nên không có phương thức khởi tạo.
- Tất cả các phương thức trong interface luôn ở dạng public abstract mà không cần khai báo.
- Các thuộc tính trong interface luôn ở dạng public static final mà không cần khai báo, yêu cầu phải có giá trị.
- Mục đích của interface là để thay thế đa kế thừa lớp của những ngôn ngữ khác (ví dụ như C++, Python…). Ngoài ra, interface sẽ giúp đồng bộ và thống nhất trong việc phát triển hệ thống trao đổi thông tin.

**Tóm lại:** một lớp chỉ được `extends` một class duy nhất, nhưng có thể `implements` bao nhiêu interface tùy ý.