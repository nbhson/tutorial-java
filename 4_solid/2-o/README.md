# O — Open/Closed Principle (Mở cho mở rộng, đóng cho sửa đổi)

> **Code nên MỞ để mở rộng nhưng ĐÓNG để sửa đổi.** Muốn thêm hành vi mới? Thêm class mới — đừng chạm vào code cũ đã chạy ổn định.

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Cầu electrical có **ổ cắm chuẩn**. Muốn thêm thiết bị mới, bạn cắm thêm — không phải đục tường đi lại dây điện. OCP thiết kế code theo cách đó: có "ổ cắm" (interface), thiết bị mới (class mới) cắm vào là chạy.

**Tầng 2 — Định nghĩa (Bertrand Meyer):** *"Software entities should be open for extension, but closed for modification."* — Mở rộng được (thêm hành vi mới) nhưng đóng với việc sửa (không phải thay code cũ).

**Tầng 3 — Vì sao?** Mỗi lần sửa code cũ là một lần **rủi ro gây regression** ở phần đã test, đã chạy production. OCP hướng mọi thay đổi về phía **thêm code mới** — code cũ không đổi, không cần retest toàn bộ.

Hãy hình dung code cũ như một cây cầu đang có xe chạy: sửa dầm giữa cầu thì phải chặn xe, còn xây thêm làn mới bên cạnh thì giao thông vẫn thông. OCP chọn phương án xây làn mới.

> **Tóm gọn 1 câu:** OCP là "đóng cửa sửa, mở cửa thêm" — thêm tính năng bằng class mới, đừng chạm vào code cũ đã chạy ổn.

## 2. Ví dụ ❌ vi phạm

Mục này cho bạn thấy mảnh đất kinh điển sinh ra vi phạm OCP: một khối `if-else` "rẻ" hôm nay, nhưng mỗi loại mới lại phải mổ xẻ nó thêm một lần.

> 📄 **Code ví dụ:** [`examples/01-2-vi-du-vi-pham.java`](examples/01-2-vi-du-vi-pham.java)

**Vấn đề:** thêm pizza "seafood" cho Đà Nẵng, "chicken" cho Sài Gòn → phải mở file `PizzaStore` sửa if-else liên tục. Mỗi sửa là một lần nguy cơ phá luồng order đang chạy đúng.

## 3. Ví dụ ✅ tuân thủ — chính là cách pizza-app đã làm

Mục này chỉ ra rằng pizza-app của chúng ta đã tuân thủ OCP sẵn: ba khu vực mở rộng bằng cách *thêm* store, không ai *sửa* `PizzaStore`.

> 📄 **Code ví dụ:** [`examples/02-3-vi-du-tuan-thu-chinh-la-cach.java`](examples/02-3-vi-du-tuan-thu-chinh-la-cach.java)

Xem file thực tế `6-pizza-app/src/Store/DNPizzaStore.java`, `SGPizzaStore.java`, `HuePizzaStore.java` — ba store mở rộng cùng một "ổ cắm" mà không ai sửa `PizzaStore`.

Đây chính là pattern **Factory Method**: phần **bất biến** (quy trình order) nằm ở lớp cha, phần **có thể thay đổi** (cách tạo pizza) được đẩy xuống lớp con.

## 4. OCP với interface — mở rộng theo hướng đa hình

Mục này nói về cách đạt OCP gọn nhất: đặt ra một "ổ cắm" (interface), để mọi thứ mới chỉ cần cắm vào chứ không phải đi lại dây điện của code cũ.

> 📄 **Code ví dụ:** [`examples/03-4-ocp-voi-interface-mo-rong-theo-huong.java`](examples/03-4-ocp-voi-interface-mo-rong-theo-huong.java)

Thêm khuyến mãi mới = viết class mới + thêm vào list. `MayTinhHoaDon` không bị sửa một ký tự nào.

## 5. Dấu hiệu nhận biết vi phạm

Mục này là "chẩn đoán nhanh": nếu mấy triệu chứng dưới đây xuất hiện, chỗ đó có khả năng cao chưa đóng với sửa đổi.

- 🔴 `if-else`/`switch` kiểm tra type/loại xuất hiện ở **nhiều nơi** trong hệ thống.
- 🔴 Thêm một loại mới (loại tài khoản, phương thức thanh toán...) phải sửa 3-5 file cũ.
- 🔴 Class "gốc" liên tục được chạm vào trong git log.

❌ **Pitfall 1 — Áp dụng OCP sớm thái quá:** với 2 case, if-else đơn giản vẫn tốt hơn một hệ interface. Chỉ "đóng" khi bạn **đã thấy** yêu cầu thay đổi lặp lại (rule of three).

❌ **Pitfall 2 — Dùng kế thừa vô tội vạ để "mở rộng":** nhiều tầng `extends` chồng lên nhau khiến logic khó lần. OCP thường đạt được gọn hơn bằng **composition + interface** (strategy) hơn là thừa kế sâu.

❌ **Pitfall 3 — Sửa "nhẹ nhàng" bên trong class cũ vẫn tính là vi phạm OCP:** ví dụ sửa private method của `PizzaStore` cho khu vực mới — dù không đổi API, bạn vẫn đụng vào code cũ đã test.

## 6. Bài tập

Mục này để bạn tự tay "đóng cửa" một khối if-else của riêng mình — làm xong bài 1 bạn sẽ nhớ OCP cả đời.

1. Cho code điều hướng ưu đãi theo rank khách hàng bằng if-else (5 loại). Refactor sang OCP với interface `UuDai`. Đếm số file phải sửa khi thêm loại thứ 6 — trước và sau.
2. Trong pizza-app, thêm thành phố mới (ví dụ `CantHoPizzaStore` + 3 loại pizza) **không sửa bất kỳ file cũ nào**. Chạy lại `Main` để chứng minh.
3. Nhận diện một chỗ trong dự án của bạn mà mỗi lần thêm tính năng lại phải sửa switch. Chuyển sang strategy/interface.

## 7. Tóm tắt

Mục này gom lại bài thành bốn gạch đầu dòng để ôn nhanh.

- OCP: mở cho **mở rộng**, đóng cho **sửa đổi**.
- Công cụ chính: abstract class/interface + kế thừa hoặc composition; factory method trong pizza-app là ví dụ mẫu.
- Thêm tính năng = thêm code mới; code cũ đã test không bị chạm vào.
- Đừng lạm dụng: OCP có chi phí (nhiều interface/class), áp dụng ở nơi thay đổi thường xuyên.

> **Tóm gọn 1 câu:** OCP là xây làn đường mới thay vì đào lại làn cũ — thêm class/interface khi yêu cầu đổi, và chỉ áp dụng ở những nơi thay đổi thường xuyên.
