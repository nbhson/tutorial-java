# Overloading (Nạp chồng phương thức)

## Method Overloading trong Java

Mục này định nghĩa nạp chồng — một tên gọi, nhiều cách dùng: cùng gọi `add()` nhưng truyền bao nhiêu tham số, kiểu gì là Java tự chọn đúng phiên bản.

Khi một lớp có **hai hoặc nhiều phương thức cùng tên nhưng tham số khác nhau** (khác số lượng hoặc kiểu dữ liệu), tại thời điểm gọi, dựa trên tham số được truyền vào, phương thức tương ứng sẽ được gọi. Cơ chế này gọi là **method overloading** (nạp chồng phương thức).

Ví von: overloading giống một quầy dịch vụ chỉ có một cái tên "Nhận gửi tiền" nhưng có nhiều cửa sổ — bạn đưa 2 tờ tiền thì tới cửa sổ số 2, đưa 3 tờ thì tới cửa sổ số 3; nhân viên (compiler) nhìn "đồ bạn cầm" mà điều hướng, chứ không cần bạn nói cửa nào.

## Lợi ích của method overloading

Mục này trả lời "khó chịu vậy làm để làm gì": vì sao nạp chồng giúp code dễ đọc hơn thay vì khó đoán hơn.

- Nạp chồng cải thiện **khả năng đọc mã** và giảm sự trùng lặp mã.
- Nạp chồng cũng giúp đạt được **đa hình thời gian biên dịch (compile-time polymorphism)**.

## Ví dụ

Mục này đi qua ví dụ cụ thể với hai phương thức `add` cùng tên — đọc để thấy compiler chọn phiên bản dựa trên tham số như thế nào.

Ở đây ta tạo một lớp `Tester` có hai phương thức cùng tên (`add`) và cùng kiểu trả về, điểm khác biệt duy nhất là tham số nhận vào (một phương thức nhận hai biến `int`, phương thức kia nhận ba biến `int`):

> 📄 **Code ví dụ:** [`examples/01-vi-du.java`](examples/01-vi-du.java)

Khi bạn gọi `add()`, dựa trên tham số truyền vào, phần thân phương thức tương ứng sẽ được thực thi:

> 📄 **Code ví dụ:** [`examples/02-vi-du.java`](examples/02-vi-du.java)

## Các cách nạp chồng hợp lệ

Mục này liệt kê "luật hợp lệ" của nạp chồng: chỉ cần khác tham số là được — ghi nhớ 2 cách dưới đây.

Trong cùng một lớp, với các phương thức cùng tên, bạn có thể nạp chồng bằng cách:

> - **Khác số lượng tham số**
> - **Khác kiểu dữ liệu tham số**

## Các cách nạp chồng KHÔNG hợp lệ

Mục này quan trọng ngang phần trên: chỉ ra những cái "nghe như overloading nhưng không phải" — compiler sẽ gạch ngay, đọc để khỏi tự hỏi vì sao bị lỗi trùng phương thức.

Những cách sau **không** tạo ra overloading — compiler sẽ báo lỗi trùng phương thức:

> - **Khác kiểu trả về** (tham số vẫn giống nhau)

Ví von: đổi màu đồng phục mà vẫn đứng cùng cửa sổ, nhận cùng loại giấy tờ — khách đâu có phân biệt được, compiler cũng vậy.

> 📄 **Code ví dụ:** [`examples/03-cac-cach-nap-chong-khong-hop-le.java`](examples/03-cac-cach-nap-chong-khong-hop-le.java)

> - **Khác static/non-static** (tham số vẫn giống nhau)

> 📄 **Code ví dụ:** [`examples/04-cac-cach-nap-chong-khong-hop-le.java`](examples/04-cac-cach-nap-chong-khong-hop-le.java)

## Ví dụ đầy đủ

Mục này gom các ví dụ chạy được đầy đủ theo từng cách nạp chồng hợp lệ — dùng để gõ lại và thử tay.

### Overloading: khác số lượng tham số

Bạn có thể nạp chồng dựa trên số lượng tham số khác nhau.

#### Số lượng tham số khác nhau (phương thức static)

> 📄 **Code ví dụ:** [`examples/05-so-luong-tham-so-khac-nhau-phuong-thuc.java`](examples/05-so-luong-tham-so-khac-nhau-phuong-thuc.java)

#### Số lượng tham số khác nhau (phương thức non-static)

> 📄 **Code ví dụ:** [`examples/06-so-luong-tham-so-khac-nhau-phuong-thuc.java`](examples/06-so-luong-tham-so-khac-nhau-phuong-thuc.java)

### Overloading: khác kiểu tham số

> 📄 **Code ví dụ:** [`examples/07-overloading-khac-kieu-tham-so.java`](examples/07-overloading-khac-kieu-tham-so.java)

> **Tóm gọn 1 câu:** Overloading là "một tên, nhiều cửa sổ" — muốn hợp lệ thì phải khác tham số; khác mỗi kiểu trả về hay static thì compiler không nhận.
