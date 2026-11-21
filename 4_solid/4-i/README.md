# I — Interface Segregation Principle (Phân tách giao diện)

> **Đừng ép client phụ thuộc những method nó không dùng.** Nhiều interface nhỏ, chuyên biệt tốt hơn một interface "béo".

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Bạn thuê thợ sửa ống nước, hợp đồng bắt anh ta phải ký cam kết cả "sửa điện, sơn tường, làm móng". Vô lý — anh ta chỉ cần **một hợp đồng về nước**. Interface béo cũng vậy: ép người implement ký vào những cam kết họ không quan tâm.

**Tầng 2 — Định nghĩa (Robert C. Martin):** *"Clients should not be forced to depend on methods they do not use."* — Client không nên bị ép phụ thuộc method mà nó không dùng.

**Tầng 3 — Vì sao?** Interface béo tạo **coupling giả**: một thay đổi nhỏ ở method X (mà client A không dùng) vẫn buộc client A recompile/redeploy. Interface nhỏ theo **nhóm người dùng** (client) giúp thay đổi ảnh hưởng đúng phạm vi.

> **Tóm gọn 1 câu:** ISP là "ký hợp đồng vừa khít": đừng bắt ai đó cam kết những việc họ không làm.

## 2. Ví dụ ❌ vi phạm

Mục này đưa ra hình ảnh interface "béo" kinh điển — máy in bị ép ký cả cam kết scan/fax — để bạn thấy hậu quả nổ lúc runtime mà compiler không cứu được.

> 📄 **Code ví dụ:** [`examples/01-2-vi-du-vi-pham.java`](examples/01-2-vi-du-vi-pham.java)

**Vấn đề:** `MayInInOnly` phải ký hợp đồng scan/fax dù không làm. Runtime sẽ nổ khi ai đó gọi — lỗi mà compiler không cảnh báo.

## 3. Ví dụ ✅ tuân thủ

Mục này "cắt nhỏ" hợp đồng béo phía trên thành từng mảnh đúng nhu cầu — mỗi implementer chỉ ký phần mình thật sự làm.

> 📄 **Code ví dụ:** [`examples/02-3-vi-du-tuan-thu.java`](examples/02-3-vi-du-tuan-thu.java)

**Vì sao tốt hơn?**
- Không còn `UnsupportedOperationException` nào.
- Thêm method mới vào `CoTheFax` chỉ ảnh hưởng các class fax thật — `MayInInOnly` không phải recompile vì lý do liên quan fax.

## 4. Liên hệ với pizza-app

Mục này "soi" pizza-app để khẳng định nó không vi phạm ISP — và chỉ ra chỗ nào nếu thêm bừa sẽ phá vỡ điều đó.

Pizza app không vi phạm ISP: mỗi interface/abstract class đều gọn.

- `Pizza` (abstract) chỉ gồm các bước chung mà **mọi** pizza đều có; phần khác nhau (`box()`) là abstract — mọi subclass buộc phải có, hợp lý vì mọi pizza đều được đóng hộp.
- `PizzaStore` chỉ yêu cầu một method duy nhất `CreatePizza(type)` — đây là interface "vừa khít" với nhu cầu của `Main` và của quy trình order.

> 📄 **Code ví dụ:** [`examples/03-4-lien-he-voi-pizza-app.java`](examples/03-4-lien-he-voi-pizza-app.java)

Nếu `PizzaStore` ép subclass override thêm `doanhThu()`, `nhanVien()`, `khoHang()`... thì đó là ISP bị vi phạm — không phải store nào cũng báo cáo doanh thu theo cùng cách.

## 5. Interface mặc định (default method) — khi nào được dùng?

Mục này nói về "cửa hậu" `default method` của Java 8: nó rất tiện, nhưng cũng dễ bị dùng làm cớ để ôm đồm trong interface.

Từ Java 8, interface có `default method`. Đây **không phải** cái cớ để giữ interface béo:

> 📄 **Code ví dụ:** [`examples/04-5-interface-mac-inh-default-method-khi-nao.java`](examples/04-5-interface-mac-inh-default-method-khi-nao.java)

✅ `default` hợp lệ khi hành vi được xây từ các method trừu tượng **mà mọi implementer đều có**.
❌ `default` là anti-pattern khi nó tồn tại chỉ để "cho qua" những implementer không cần method đó.

## 6. Dấu hiệu nhận biết vi phạm

Mục này là bộ "kim chỉ nam chẩn đoán": thấy một trong các dấu hiệu dưới đây là interface của bạn có nguy cơ đang "béo".

- 🔴 Interface có nhiều method mà 1 implementer chỉ dùng một phần.
- 🔴 Xuất hiện `UnsupportedOperationException` khi implement.
- 🔴 Method hoặc field được comment "không dùng cho trường hợp X".
- 🔴 Client import cả interface để gọi 1-2 method.

❌ **Pitfall — tách quá nhỏ:** một interface một method (trừ trường hợp functional interface như `Runnable`) khiến hệ thống thành "bánh mì sandwich 100 lớp". Tách theo **nhóm hành vi cùng quan tâm của một loại client**, không phải theo số dòng.

## 7. Bài tập

Mục này để bạn luyện tay tách interface theo đúng nhóm người dùng — làm bài 1 là nắm được tinh thần ISP.

1. Cho interface `NhanVien { lamViec(); nghiPhep(); nhanLuong(); lamThemGio(); }` và các loại nhân viên (toàn thời gian, bán thời gian, thực tập sinh không hưởng lương). Tách interface đúng ISP.
2. Trong một dự án, tìm interface có > 10 method; liệt kê từng implementer dùng method nào; đề xuất cách tách.
3. Áp dụng cho pizza-app: nếu thêm `BanhMiStore`, `TraSuaStore` — `PizzaStore` có cần tách thành interface `Store` chung không? Tranh luận lúc nào nên, lúc nào không.

## 8. Tóm tắt

Mục này gom lại bài thành bốn gạch đầu dòng để ôn nhanh.

- ISP: interface nhỏ theo nhóm client; không ép implement method không dùng.
- `UnsupportedOperationException` khi implement = mùi ISP (và LSP).
- `default method` chỉ dùng cho hành vi chung thật sự của mọi implementer.
- Cân bằng: tách theo quan điểm nghiệp vụ, tránh một-interface-một-method máy móc.

> **Tóm gọn 1 câu:** ISP là "hợp đồng vừa khít" — tách interface theo nhóm client, đừng ép ai ký vào việc họ không làm.
