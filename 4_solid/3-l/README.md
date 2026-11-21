# L — Liskov Substitution Principle (Nguyên tắc thay thế Liskov)

> **Mọi nơi dùng lớp cha phải chạy đúng khi thay bằng lớp con.** Lớp con không được "làm hỏng lời hứa" của lớp cha — nếu hỏng, kế thừa đó là sai từ thiết kế.

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Nếu một "chim cánh cụt" kế thừa "chim" nhưng **không bay được**, thì mọi đoạn code viết cho "chim" với giả định `bay()` hoạt động sẽ nổ tung khi gặp cánh cụt. Đó là vi phạm LSP — cánh cụt không nên kế thừa "chim bay".

**Tầng 2 — Định nghĩa (Barbara Liskov, 1987):** *Nếu S là subtype của T, thì object kiểu T có thể được thay bằng object kiểu S mà không làm thay đổi tính đúng đắn của chương trình.*

**Tầng 3 — Vì sao?** Đa hình chỉ có ích khi caller có thể **tin tưởng hợp đồng** của lớp cha:

- Preconditions: lớp con không được **thắt chặt** điều kiện vào.
- Postconditions: lớp con không được **lỏng lẻo** kết quả ra.
- Invariants: trạng thái của lớp con phải vẫn đúng như lớp cha hứa.

Nếu lớp con phá hợp đồng, caller buộc phải `instanceof` để phòng hờ → mất lợi ích đa hình, sinh code rác.

Hãy hình dung hợp đồng như "tem bảo hành" của lớp cha: lớp con được thay thế chỗ cha, nhưng tem phải còn nguyên — nếu lớp con phải gỡ tem để "gắn lại kiểu mình thích" thì quan hệ kế thừa đó sai từ đầu.

> **Tóm gọn 1 câu:** LSP là "thay người giữ chỗ mà không đổi luật chơi": lớp con đứng chỗ lớp cha phải chạy đúng như lời lớp cha đã hứa.

## 2. Ví dụ ❌ vi phạm kinh điển

Mục này đưa ra bài toán "vuông kế thừa chữ nhật" nổi tiếng — để bạn thấy vi phạm LSP có thể trông rất hợp lý về mặt toán học nhưng sai về hành vi.

> 📄 **Code ví dụ:** [`examples/01-2-vi-du-vi-pham-kinh-ien.java`](examples/01-2-vi-du-vi-pham-kinh-ien.java)

**Vì sao sai?** Caller của `ChuNhat` có quyền kỳ vọng "setW không ảnh hưởng h". `Vuong` phá kỳ vọng đó → mọi code generic xử lý `ChuNhat` đều có thể sai.

## 3. Ví dụ vi phạm khác — làm rỗng method cha

Mục này nói về "chiêu trốn việc" phổ biến nhất: subclass override method của cha rồi để trống hoặc ném exception — dấu hiệu cho thấy nó vốn không đáng là con của cha này.

> 📄 **Code ví dụ:** [`examples/02-3-vi-du-vi-pham-khac-lam-rong.java`](examples/02-3-vi-du-vi-pham-khac-lam-rong.java)

Cái gì `throw UnsupportedOperationException` trong override = gần như chắc chắn vi phạm LSP (và ISP — xem bài 4).

## 4. Ví dụ ✅ tuân thủ — trong pizza-app

Mục này cho bạn xem mặt "làm đúng": các subclass pizza trong project đều thay thế được lớp cha mà không ai phải phòng hờ gì cả.

> 📄 **Code ví dụ:** [`examples/03-4-vi-du-tuan-thu-trong-pizza-app.java`](examples/03-4-vi-du-tuan-thu-trong-pizza-app.java)

Không subclass nào `throw` lung tung, không subclass nào "quên" gọi `prepare()`. Đó là LSP đúng chuẩn: **thay thế được, hành vi tương thích**.

## 5. Bài toán vuông/chữ nhật — giải đúng bằng thiết kế

Mục này nói về lối thoát khi kế thừa sai bản chất: thay vì ép hai thứ "giống nhau bề ngoài" làm cha con, ta cho chúng cùng ký một hợp đồng nhỏ gọn hơn.

✅ Khi hành vi **khác bản chất**, đừng ép kế thừa — tách abstraction chung nhỏ hơn:

> 📄 **Code ví dụ:** [`examples/04-5-bai-toan-vuong-chu-nhat-giai-ung.java`](examples/04-5-bai-toan-vuong-chu-nhat-giai-ung.java)

Cả hai cùng thỏa hợp đồng `dienTich()` — không ai phá lời hứa của ai.

## 6. Dấu hiệu nhận biết vi phạm

Mục này là danh sách "tem rách" cần săm soi: thấy một trong các dấu hiệu dưới đây, khả năng cao quan hệ kế thừa đang có vấn đề.

- 🔴 Override method rồi `throw UnsupportedOperationException` / để trống.
- 🔴 Override làm method **ném exception dễ hơn** cha (thắt chặt preconditions).
- 🔴 Override trả kết quả **hẹp/khác** hợp đồng cha (ví dụ trả null khi cha hứa luôn có).
- 🔴 Caller bắt buộc `instanceof` trước khi gọi để "phòng" một số subclass.
- 🔴 Tên class con chứa "Nhưng..." (VinhNhưngKhongDi...).

❌ **Pitfall:** "Compile được" ≠ đúng LSP. LSP là về **hành vi**, không phải chữ ký method. Java chỉ kiểm tra chữ ký; LSP phải do bạn tự bảo vệ bằng thiết kế và test.

## 7. Bài tập

Mục này để bạn tự tay vá một quan hệ kế thừa sai — lý thuyết "cánh cụt không bay" chỉ thật sự thấm khi bạn sửa lại cây kế thừa của chính mình.

1. Cho `Chim { bay(); }` và `CanhCut extends Chim` không bay được. Refactor theo 2 cách: (a) tách `CoTheBay` interface, (b) đổi hierarchy. So sánh.
2. Tìm một override trong dự án của bạn mà subclass làm "ngược" kỳ vọng của cha. Sửa lại cho đúng hợp đồng hoặc tách abstraction.
3. Viết test generic `void kiemTra(Hinh h)` hoạt động đúng cho mọi `Hinh` — chứng minh design mới tuân thủ LSP.

## 8. Tóm tắt

Mục này gom lại bài thành bốn gạch đầu dòng để ôn nhanh.

- LSP: lớp con thay lớp cha mà **không phá hợp đồng** (pre/post-conditions, invariants).
- Kế thừa sai (Vuong extends ChuNhat) trông hợp lý về "tự nhiên" nhưng phá hành vi.
- `throw UnsupportedOperationException` trong override = mùi code LSP/ISP.
- Fix: tách abstraction nhỏ hơn phản ánh đúng hành vi, hoặc composition.

> **Tóm gọn 1 câu:** LSP là "tem bảo hành phải còn nguyên khi thay thế" — kế thừa sai bản chất thì tách hợp đồng nhỏ hơn, đừng ép cha con.
