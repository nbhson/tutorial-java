# Data Types

Trước khi đựng gì vào "hộp" (biến), phải chọn **kiểu hộp**: kiểu dữ liệu quyết định hộp to bao nhiêu, đựng được gì, làm được gì với nội dung bên trong. Mục này nói về hai dòng hộp chính của Java: nguyên thủy và tham chiếu.

Có 2 kiểu dữ liệu trong java:
- Kiểu dữ liệu nguyên thủy: có 8 kiểu dữ liệu nguyên thủy trong java

-	Kiểu dữ liệu tham chiếu
    - Kiểu dữ liệu tham chiếu là kiểu dữ liệu của đối tượng, biến của kiểu dữ liệu tham chiếu chỉ chứa địa chỉ của đối tượng tại bộ nhớ stack. Đối tượng dữ liệu lại nằm ở bộ nhớ Heap. Một số kiểu dữ liệu cụ thể như các mảng (Array), lớp đối tượng (Class) hoặc kiểu lớp giao tiếp (Interface)

Ví von dễ nhớ nhất: primitive là **tiền mặt trong túi bạn** (giá trị nằm ngay tại chỗ), reference là **thẻ ATM** (đầu thẻ chỉ là "địa chỉ", tiền thật nằm ở ngân hàng — tức Heap). Đưa thẻ cho ai, người ta đi đến đúng ví tiền của bạn, chứ không mang cả tài khoản theo.

## Java Primitive Data Types

There are eight primitive data types supported by Java. Below is the list of the primitive data types:

- **Boolean**: giá trị là true hoặc false.
- **Byte**: lưu trữ kiểu số nguyên kích cỡ là 1 byte (1 byte = 8 bit). Có giá trị lưu trữ từ -128 đến 127.
- **Short**: lưu trữ kiểu số nguyên kích cỡ là 2 byte (16 bit). Giá trị có thể lưu từ -32,768 đến 32,767.
- **Int**: lưu trữ kiểu số nguyên kích cỡ là 4 byte (32 bit). Gía trị có thế lưu từ -2,147,483,648 đến 2,147,483,647.
- **Long**: lưu trữ kiểu số nguyên kích cỡ là 8 byte (64 bit). Gía trị có thể lưu từ … đến …
- **Float**: lưu trữ kiểu số thực kích cỡ là 4 byte. Khi gán giá trị cần “f” phía sau.
- **Double**: lưu trữ kiểu số thực kích cỡ là 8 byte. Khi gán giá trị có thể có hoặc không “d” ở phía sau.
- **Char**: Kiểu dữ liệu để lưu trữ kí tự trong bảng Unicode

Hãy hình dung 8 kiểu này như **bộ hộp đựng đồ kích thước từ nhỏ đến lớn**: hộp nuốt nhỏ (boolean, byte), hộp vừa (short, int), hộp to (long), hộp đựng chất lỏng (float, double) và hộp đựng từng ký tự (char). Chọn hộp vừa đủ — đựng hạt tiêu mà lấy container thì phí bộ nhớ.

> 📄 **Code ví dụ:** [`examples/01-java-primitive-data-types.java`](examples/01-java-primitive-data-types.java)

> **Tóm gọn 1 câu:** 8 kiểu primitive là 8 cỡ hộp có sẵn — chọn cỡ vừa với dữ liệu, cứ int và double làm mặc định khi phân vân.

## Java Reference/Object Data Type

- The reference data types are created using defined constructors of the classes. - They are used to access objects. These variables are declared to be of a specific type that cannot be changed. For example, Employee, Puppy, etc.

Với kiểu tham chiếu, biến chỉ cầm **chìa khóa trỏ đến object** chứ không ôm cả object trong người. Kiểu của biến gắn chặt từ lúc khai báo và không đổi được — như hợp đồng thuê ghi rõ "căn hộ loại B" thì không tự chuyển thành loại C được.

> 📄 **Code ví dụ:** [`examples/02-java-reference-object-data-type.java`](examples/02-java-reference-object-data-type.java)

> **Tóm gọn 1 câu:** Primitive giữ giá trị thật trong tay, reference chỉ giữ địa chỉ — bản chất khác nhau này là gốc của mọi câu chuyện `==` vs `equals()` sau này.
