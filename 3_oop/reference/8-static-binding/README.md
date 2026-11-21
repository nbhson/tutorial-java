# Static Binding

## Static Binding

Mục này định nghĩa static binding — "chốt sổ sớm": Java quyết định gọi hàm nào ngay từ lúc biên dịch, đối ngẫu hoàn toàn với dynamic binding.

Static binding còn được gọi là liên kết thời điểm biên dịch (compile-time binding) hoặc liên kết sớm. (early binding.)

Ví von: static binding như đặt bàn cưới trước 3 tháng — quán biết chính xác phải chuẩn bị món gì cho bàn nào ngay từ hôm đặt, không chờ đến giờ tiệc mới phân công.

## Đặc điểm của Java Static Binding 

Mục này liệt kê 4 đặc điểm nhận dạng — đối chiếu từng dòng với bài Dynamic Binding để thấy hai cơ chế là "ảnh soi" của nhau.

- Liên kết − Liên kết giữa lệnh gọi phương thức và triển khai phương thức được giải quyết tại thời điểm biên dịch. 
- Cơ chế giải quyết − Static binding sử dụng kiểu của lớp và các trường để giải quyết binding. 
- Ví dụ − Quá tải phương thức là ví dụ về Static binding. 
- Kiểu phương thức − private, final và static và các biến sử dụng static binding.

## Example of Java Static Binding

Mục này cho xem ví dụ cụ thể: cùng một tên gọi, nhưng compiler đã biết chính xác chạy phiên bản nào trước cả khi chương trình chạy.

> 📄 **Code ví dụ:** [`examples/01-example-of-java-static-binding.java`](examples/01-example-of-java-static-binding.java)

> 📄 **Code ví dụ:** [`examples/02-example-of-java-static-binding.java`](examples/02-example-of-java-static-binding.java)

> **Tóm gọn 1 câu:** Static binding là "chốt sổ lúc biên dịch" — kiểu biến quyết định gọi hàm nào, đặc trưng bởi overloading và các phương thức private/final/static.
