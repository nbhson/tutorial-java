# Overriding

## Lợi ích của Overriding trong Java

Mục này trả lời "ghi đè để làm gì": cho lớp con quyền tự quyết hành vi của mình thay vì ăn theo nguyên bản của lớp cha.

Lợi ích của việc ghi đè là: `khả năng xác định hành vi cụ thể cho loại lớp con`, nghĩa là lớp con có thể triển khai phương thức lớp cha dựa trên yêu cầu của lớp đó. Theo thuật ngữ hướng đối tượng, ghi đè có nghĩa là ghi đè chức năng của phương thức hiện có.

Ví von: overriding như con cái nhận lại "nghề truyền gia" từ cha nhưng làm theo cách của thế hệ mình — nghề vẫn là nghề đó, cách làm thì cập nhật.

## Java Method Overriding

Mục này định nghĩa chính xác overriding và điều kiện "đủ tư cách" để một phương thức được coi là ghi đè.

- Ghi đè phương thức cho phép chúng ta đạt được đa hình thời gian chạy và được sử dụng để viết các định nghĩa cụ thể của phương thức lớp con đã được định nghĩa trong lớp cha.
- Phương thức là lớp cha và phương thức được ghi đè trong lớp con phải có cùng chữ ký khai báo như danh sách tham số, kiểu và kiểu trả về.

## Cách sử dụng method overriding trong Java

Mục này tóm gọn 2 công dụng trọng tâm — đọc để biết overriding "được sinh ra" vì hai mục đích nào.

Dưới đây là hai công dụng quan trọng của method overriding trong Java:

> - Dùng để đạt được **đa hình thời gian chạy (run-time polymorphism)**.
> - Dùng để viết định nghĩa cụ thể cho phương thức của lớp con (phương thức này gọi là **overridden method**).

## Example of Method Overriding in Java

Mục này cho xem một ví dụ ghi đè hoàn chỉnh — cùng tên, cùng chữ ký, nhưng hành vi khác nhau giữa cha và con.

> 📄 **Code ví dụ:** [`examples/01-example-of-method-overriding-in-java.java`](examples/01-example-of-method-overriding-in-java.java)

## Rules for Method Overriding

Mục này là "luật chơi" bắt buộc của ghi đè — 6 quy tắc dưới đây quyết định code của bạn được compile hay bị gạch.

- Danh sách `param phải giống hệt` với danh sách param của phương thức được ghi đè.
- Kiểu trả về phải giống hoặc là con của kiểu trả về được khai báo trong phương thức ghi đè ban đầu trong lớp cha.
- `Access modifier` không thể hạn chế hơn mức truy cập của phương thức ghi đè. Ví dụ: Nếu phương thức của class cha được khai báo là public thì phương thức ghi đè trong lớp con không thể là private hoặc protected.
- Một phương thức được khai báo là `final` không thể bị ghi đè.
- Phương thức khai báo `static` không thể bị ghi đè, nhưng có thể được **khai báo lại (re-declare)** trong lớp con.
- Constructor không thể bị ghi đè.

Ví von quy tắc thứ 3: "kế thừa không được thu hẹp quyền" — cha mở cửa cho cả hàng xóm vào thì con không được treo biển cấm.

## Java Method and Constructor Overriding

Mục này giải thích một câu hỏi thường gặp: vì sao constructor không ghi đè được — vì luật "cùng tên" của ghi đè không thể thỏa với constructor.

Trong Java, mỗi lớp có một tên khác nhau và tên của hàm tạo giống với tên lớp. Do đó, chúng ta không thể ghi đè một hàm tạo vì chúng không thể có cùng tên.

## Java Method Overriding: dùng từ khóa super

Mục này chỉ "cửa thoát" khi bạn muốn dùng lại bản gốc của cha: từ khóa `super` — ghi đè xong vẫn quay lại xin cha bản cũ khi cần.

Khi muốn gọi **phiên bản của superclass** cho một phương thức đã bị ghi đè, bạn dùng từ khóa `super`:

> 📄 **Code ví dụ:** [`examples/02-java-method-overriding-dung-tu-khoa-super.java`](examples/02-java-method-overriding-dung-tu-khoa-super.java)

## Kết quả

> 📄 **Code ví dụ:** [`examples/03-ket-qua.sh`](examples/03-ket-qua.sh)

> **Tóm gọn 1 câu:** Overriding là "con làm lại nghề của cha theo cách riêng" — cùng chữ ký là điều kiện bắt buộc, `super` là đường quay lại bản gốc khi cần.
