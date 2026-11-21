# S — Single Responsibility Principle (Trách nhiệm duy nhất)

> **Một class chỉ nên có MỘT lý do để thay đổi.** Nếu bạn phải nói "class này làm A, B, C" thì nó đang làm quá nhiều việc.

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Một nhân viên giỏi là người **chuyên** về một việc. Một class cũng vậy: `Calculator` tính toán, `Printer` in ấn, `Database` lưu trữ. Đừng biến một class thành "nhân viên làm đủ thứ".

**Tầng 2 — Định nghĩa (Robert C. Martin):** *"A class should have only one reason to change."* — Một class chỉ nên có **một lý do duy nhất** để bị sửa đổi. "Lý do thay đổi" = nhóm các bên liên quan (stakeholder) yêu cầu sửa.

**Tầng 3 — Vì sao?** Nếu class làm 3 việc, 3 nhóm người khác nhau đều có lý do chạm vào nó:

- Kế toán thay công thức tính lương → sửa class.
- Marketing đổi mẫu báo cáo → sửa class.
- DBA đổi schema → sửa class.

Ba nhóm cùng đụng vào một file → conflict khi merge, một sửa làm gãy hai chỗ còn lại, test không biết tin ai.

Hãy hình dung một quán ăn chỉ có **một người làm đủ mọi việc**: nấu, thu ngân, rửa bát, giao hàng. Chỉ cần anh ta nghỉ một buổi, cả quán sập. Class làm nhiều việc cũng y như vậy.

> **Tóm gọn 1 câu:** SRP nói rằng một class chỉ nên có một lý do để thay đổi — một người thợ, một chuyên môn.

## 2. Ví dụ ❌ vi phạm

Mục này cho bạn xem một class "làm đủ thứ" điển hình — tính lương, in báo cáo và lưu database cùng nằm một chỗ, để bạn tự nhận ra nó ở chính code của mình.

> 📄 **Code ví dụ:** [`examples/01-2-vi-du-vi-pham.java`](examples/01-2-vi-du-vi-pham.java)

Ba lý do thay đổi trong một class = tần suất sửa gấp 3 lần, nguy cơ hỏng chéo gấp nhiều hơn.

## 3. Ví dụ ✅ tuân thủ

Mục này bê tảng ❌ phía trên ra và dán nhãn lại: mỗi nhóm hành vi một class riêng, ai có lý do thay đổi gì thì chỉ đụng vào class của mình.

> 📄 **Code ví dụ:** [`examples/02-3-vi-du-tuan-thu.java`](examples/02-3-vi-du-tuan-thu.java)

## 4. Áp dụng vào pizza-app

Mục này chứng minh SRP không phải lý thuyết suông: chính project pizza-app của chúng ta đang tuân thủ nó trong từng class.

Trong `6-pizza-app/src`, mỗi class có một trách nhiệm rõ ràng:

- `Pizza.java` — mô tả quy trình chung của một chiếc pizza (prepare/bake/cut/box).
- `Pizza/DN/DNCheesePizza.java` — định nghĩa thành phần của pizza phô mai kiểu Đà Nẵng.
- `Store/PizzaStore.java` — điều phối: đặt hàng → tạo pizza → quy trình chế biến.
- `Store/DNPizzaStore.java` — quyết định tạo pizza nào cho khu vực Đà Nẵng.

> 📄 **Code ví dụ:** [`examples/03-4-ap-dung-vao-pizza-app.java`](examples/03-4-ap-dung-vao-pizza-app.java)

**Vì sao tách như vậy?** Lý do thay đổi của "quy trình chế biến" (nấu chín khác nhau) và "thành phần pizza" (món mới) là hai lý do **khác nhau** — tách class giúp sửa cái này không động vào cái kia.

## 5. Dấu hiệu nhận biết vi phạm

Mục này là bộ "kim chỉ nam chẩn đoán": quét qua vài dấu hiệu dưới đây là biết ngay một class đang ôm quá nhiều việc.

- 🔴 Tên class chứa `Manager`, `Util`, `Helper`, `Service` bao trùm mọi thứ.
- 🔴 Import của class gồm những "thế giới" không liên quan: vừa `java.sql`, vừa `java.io`, vừa `javax.swing`.
- 🔴 Method của class viết cho những **người dùng khác nhau**.
- 🔴 Muốn test tính lương nhưng phải mock database và máy in.

❌ **Pitfall 1 — Tách cứng nhắc theo tầng kỹ thuật:** chia `Controller/Service/Repository` rồi nhét **mọi** logic vào một `Service` khổng lồ. SRP không phải "mỗi tầng một class" mà là "mỗi **lý do thay đổi** một class".

❌ **Pitfall 2 — Tách quá mảnh:** mỗi method một class → class tạo object khổng lồ, phải đi lang thang qua 20 file để hiểu luồng. SRP là về **lý do thay đổi**, không phải số dòng.

## 6. Bài tập

Mục này là phần "luyện tay" — lý thuyết đã hiểu phải đi qua tay mới nhớ, bạn thử cả ba bài dưới đây trước khi sang bài OCP.

1. Tìm trong code cũ của bạn một class dài > 200 dòng, liệt kê các nhóm method, tách thành các class theo trách nhiệm.
2. Refactor `Employee` (tính lương + gửi email + lưu DB) thành 4 class. Viết unit test riêng cho phần tính lương không cần mock gì.
3. Trong pizza-app, nếu thêm yêu cầu "in hóa đơn sau khi order" — bạn sẽ đặt class đó ở đâu, vì sao không nhét vào `PizzaStore`?

## 7. Tóm tắt

Mục này gom lại cả bài thành ba gạch đầu dòng để bạn ôn nhanh.

- SRP: một class = một lý do thay đổi = một nhóm stakeholder.
- Tách theo trách nhiệm, không phải theo tầng kỹ thuật.
- Lợi ích: ít conflict merge, test dễ, sửa không lo hỏng chéo.

> **Tóm gọn 1 câu:** SRP là "một người thợ, một việc" — tách class theo lý do thay đổi, không phải theo tầng kỹ thuật hay số dòng code.
