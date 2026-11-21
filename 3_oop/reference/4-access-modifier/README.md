# Access Modifiers (Phạm vi truy cập)

> Access modifiers trong Java dùng để chỉ định **phạm vi truy cập** của biến, thành viên dữ liệu, phương thức, lớp hoặc constructor. Chúng giúp hạn chế và bảo vệ việc truy cập dữ liệu.

Ví von: access modifiers là hệ thống thẻ từ trong một tòa nhà — có khu ai cũng vào được (public), có khu chỉ nhân viên công ty (default), có khu chỉ dành cho người nhà (protected), và có phòng riêng khóa kín chỉ chủ nhân mới mở được (private).

Java có 4 loại access modifier, liệt kê theo phạm vi từ hẹp đến rộng:

- `default` (không cần từ khóa)
- `private`
- `protected`
- `public`

![alt text](image.png)

## Default Access Modifier

Mục này nói về mức truy cập "không viết gì cả" — tưởng như mở, thật ra chỉ mở trong nội bộ cùng khu (package).

`Default access modifier` nghĩa là bạn **không khai báo tường minh** modifier nào cho lớp, field, phương thức...

> Biến hoặc phương thức không có modifier chỉ có thể được truy cập bởi **các lớp trong cùng package**. Lưu ý: field trong interface mặc định là `public static final` và phương thức trong interface mặc định là `public`.

Ví dụ:

> 📄 **Code ví dụ:** [`examples/01-default-access-modifier.java`](examples/01-default-access-modifier.java)

## Private Access Modifier

Mục này nói về mức "khóa kín nhất": chỉ chủ nhân của class mới được đụng vào — nền tảng của encapsulation.

> Các phương thức, biến, constructor khai báo `private` **chỉ có thể được truy cập bên trong chính lớp khai báo chúng**.

- `private` là mức truy cập **hạn chế nhất**. Class và interface không thể là `private`.
- Biến `private` vẫn có thể truy cập từ ngoài lớp **thông qua các phương thức getter/setter public**.
- Dùng `private` là cách chính để một đối tượng **đóng gói (encapsulate)** và ẩn dữ liệu khỏi thế giới bên ngoài.

Ví dụ 1:

> 📄 **Code ví dụ:** [`examples/02-private-access-modifier.java`](examples/02-private-access-modifier.java)

Ví dụ 2 — khi getter/setter cũng là `private`, lớp ngoài không gọi được nữa:

> 📄 **Code ví dụ:** [`examples/03-private-access-modifier.java`](examples/03-private-access-modifier.java)

## Protected Access Modifier

Mục này nói về mức "người nhà": cho phép con cháu (lớp con) và hàng xóm cùng khu (cùng package) dùng, nhưng chặn người lạ hoàn toàn.

> Các biến, phương thức, constructor khai báo `protected` trong superclass **chỉ được truy cập bởi các lớp con (kể cả ở package khác)** hoặc **bất kỳ lớp nào trong cùng package** với lớp chứa thành phần đó.

- `protected` không áp dụng được cho class và interface. Với interface, các phương thức và field **không thể** khai báo `protected`.
- `protected` cho lớp con cơ hội dùng lại các phương thức/biến hỗ trợ, đồng thời ngăn các lớp không liên quan dùng chúng.

> 📄 **Code ví dụ:** [`examples/04-protected-access-modifier.java`](examples/04-protected-access-modifier.java)

## Public Access Modifier

Mục này nói về mức "mở cửa hoàn toàn" — nơi bạn đặt những thứ muốn mọi người dùng, như sảnh chờ và bảng hiệu.

Lớp, phương thức, constructor, interface... khai báo `public` có thể được truy cập từ **bất kỳ lớp nào**. Vì vậy các field, phương thức, khối lệnh bên trong một lớp public đều có thể được truy cập từ bất kỳ đâu trong chương trình Java.

Cú pháp:

> 📄 **Code ví dụ:** [`examples/05-public-access-modifier.java`](examples/05-public-access-modifier.java)

Ví dụ:

> 📄 **Code ví dụ:** [`examples/06-public-access-modifier.java`](examples/06-public-access-modifier.java)

## Access Modifiers và kế thừa

Mục này nói về quy tắc "kế thừa không được giảm quyền": lớp con nhận thừa kế gì thì không được thu hẹp mức mở của nó.

Với các phương thức được **kế thừa**, Java áp dụng các quy tắc sau:

> - Phương thức khai báo `public` trong superclass **phải** vẫn là `public` trong mọi lớp con.
> - Phương thức khai báo `protected` trong superclass phải là `protected` hoặc `public` trong lớp con; **không thể** là `private`.
> - Phương thức `private` không được kế thừa, nên không có quy tắc nào cho chúng.

![alt text](image-1.png)

## Tổng kết

Mục này gom cả bài thành một bảng tra nhanh — khi phân vân "modifier này cho phép ai vào?", quay lại đây là ra.

| Modifier | Cùng class | Cùng package | Lớp con (khác package) | Mọi nơi |
|----------|:---:|:---:|:---:|:---:|
| `private` | ✅ | ❌ | ❌ | ❌ |
| `default` | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

> **Cách đọc bảng này:** mỗi dòng là một modifier, mỗi cột là "nơi đứng" của code đang gọi. Đọc từ trái sang phải là phạm vi mở dần ra: dấu ✅ nghĩa là được phép truy cập. Quy tắc nhớ nhanh: `private` chỉ có 1 dấu ✅, và mỗi cấp xuống dưới lại mở thêm đúng 1 cột.

> **Tóm gọn 1 câu:** Access modifier là "hệ thống thẻ từ": private — khóa phòng riêng, default — nội bộ tòa nhà, protected — người nhà và hàng xóm, public — mở cửa cho tất cả.
