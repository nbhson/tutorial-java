# Dynamic Binding

## Dynamic Binding

Mục này định nghĩa dynamic binding — cơ chế quyết định "gọi hàm nào" được chốt lúc chạy chứ không phải lúc biên dịch; đây là động cơ ngầm của đa hình.

Dynamic binding cũng được gọi là đa hình thời gian chạy (run-time polymorphism) hoặc liên kết muộn (late binding). Liên kết động sử dụng các đối tượng để giải quyết liên kết.

Ví von: dynamic binding giống điều phối xe at-taxi — app không chốt xe nào đón bạn từ khi bạn đặt (compile), mà nhìn xe nào đang gần nhất (kiểu thật của object) ngay lúc đó rồi mới ghép; quyết định chốt muộn nhưng chính xác.

## Đặc điểm của Java Dynamic Binding

Mục này tóm gọn 4 đặc điểm nhận dạng của dynamic binding — dùng để phân biệt nó với static binding trong bài kế tiếp.

- Linking - Liên kết giữa method call và việc triển khai phương thức được giải quyết tại thời điểm chạy.
- Resolve mechanism − Dynamic binding uses object type to resolve binding.
- Example − Method overriding is the example of Dynamic binding.
- Type of Methods − Virtual methods use dynamic binding.

> 📄 **Code ví dụ:** [`examples/01-ac-iem-cua-java-dynamic-binding.java`](examples/01-ac-iem-cua-java-dynamic-binding.java)

> **Tóm gọn 1 câu:** Dynamic binding là "chốt sổ lúc chạy" — Java nhìn kiểu thật của object (không phải kiểu của biến tham chiếu) để chọn đúng phương thức đã bị ghi đè.
