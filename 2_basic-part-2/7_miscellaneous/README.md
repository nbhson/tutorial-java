# Miscellaneous (Java 21+ — Records, Sealed, Pattern Matching)

> Java đang thay đổi nhanh hơn bao giờ hết. Bài này tổng hợp các tính năng hiện đại bạn nên dùng ngay: **record**, **sealed class/interface**, **pattern matching** cho switch và instanceof, cùng vài chi tiết nhỏ khác. Chúng giúp code ngắn hơn, an toàn hơn, dễ đọc hơn.

Nếu ví Java là một căn bếp, thì mấy tính năng này giống như các dụng cụ làm bếp đời mới: vẫn nấu đúng món cũ, nhưng ít phải rửa bát (boilerplate) hơn hẳn. Mục tiêu xuyên suốt của cả bài là: viết ít code hơn, để compiler lo phần kiểm tra thay cho bạn.

## 1. Record — immutable data carrier

Mục này nói về `record` — cách khai báo một "hộp đựng dữ liệu" chỉ trong 1 dòng, thay vì phải tự viết constructor, getter, equals, hashCode, toString. Đọc để biết bạn có thể bỏ bao nhiêu dòng lặp đi lặp lại.

### 1.1. Vấn đề trước record

> 📄 **Code ví dụ:** [`examples/01-1-1-van-e-truoc-record.java`](examples/01-1-1-van-e-truoc-record.java)

Hãy hình dung: bạn chỉ muốn đựng 3 miếng đồ (tên, tuổi, email) mà phải đóng cả một chiếc thùng gỗ to đùng với 7 cái khóa... Đó là cảm giác khi phải viết POJO thủ công.

### 1.2. Record — 1 dòng thay thế 70 dòng

Giống như thay cả bộ đồ bếp cồng kềnh bằng một nồi cơm điện: bật một nút, mọi thứ có sẵn.

> 📄 **Code ví dụ:** [`examples/02-1-2-record-1-dong-thay-the-70.java`](examples/02-1-2-record-1-dong-thay-the-70.java)

**Trình biên dịch tự động tạo:**

1. Các trường `private final` cho mỗi tham số.
2. Constructor nhận đủ tham số (canonical constructor).
3. Accessor: `name()`, `age()`, `email()` (không có prefix `get`).
4. `equals()` / `hashCode()` / `toString()` — dựa trên tất cả các trường.

> **Tóm gọn 1 câu:** Record là "đồ nội thất lắp sẵn" — khai báo danh sách trường, mọi thứ còn lại compiler tự dựng cho bạn.

### 1.3. Record với validation — compact constructor

> 📄 **Code ví dụ:** [`examples/03-1-3-record-voi-validation-compact-constructor.java`](examples/03-1-3-record-voi-validation-compact-constructor.java)

### 1.4. Record có phương thức và field tĩnh

> 📄 **Code ví dụ:** [`examples/04-1-4-record-co-phuong-thuc-va-field.java`](examples/04-1-4-record-co-phuong-thuc-va-field.java)

### 1.5. Giới hạn của record — phải hiểu rõ

Mục này nói về những điều record **không làm được** — biết rõ giới hạn để không dùng nhầm chỗ, giống như biết nồi cơm điện không nấu được phở.

❌ **Record KHÔNG cho phép:**

> 📄 **Code ví dụ:** [`examples/05-1-5-gioi-han-cua-record-phai-hieu.java`](examples/05-1-5-gioi-han-cua-record-phai-hieu.java)

✅ **Vì sao thiết kế như vậy?** Record đại diện cho **dữ liệu thuần túy bất biến** (data carrier). Ví von: record là một phong bì niêm phong — gấp giấy vào xong là dán keo, không ai mở ra chỉnh chữ nữa. Immutability mang lại 3 lợi ích: thread-safe miễn phí, dùng được làm key Map/phần tử Set an toàn, dễ suy luận trạng thái. Đó cũng là lý do record dùng tốt cho DTO, event, message, kết quả query.

> **Tóm gọn 1 câu:** Record là "phong bì niêm phong" — đựng dữ liệu bất biến, không phải công cụ để dựng object nhiều hành vi.

## 2. Sealed classes — giới hạn cây kế thừa

Mục này nói về `sealed` — cách bạn nói với compiler: "chỉ có đúng mấy đứa con này thôi, đứa khác không được vào". Đọc để hiểu vì sao giới hạn kế thừa lại là một lợi thế, không phải hạn chế.

### 2.1. Vấn đề: kế thừa mở quá mức

Hãy tưởng tượng một câu lạc bộ mở cửa cho ai cũng vào — bạn không bao giờ biết thành viên là ai.

> 📄 **Code ví dụ:** [`examples/06-2-1-van-e-ke-thua-mo-qua.java`](examples/06-2-1-van-e-ke-thua-mo-qua.java)

**Hậu quả:** code xử lý Shape phải phỏng đoán "còn những loại nào nữa?" — không thể đảm bảo xử lý hết mọi case.

### 2.2. Sealed — khai báo "chỉ những con này mới được"

Sealed giống như danh sách khách mời có kiểm tra ở cửa: chỉ ai được ghi tên trong `permits` mới được vào.

> 📄 **Code ví dụ:** [`examples/07-2-2-sealed-khai-bao-chi-nhung-con.java`](examples/07-2-2-sealed-khai-bao-chi-nhung-con.java)

**Quy tắc bắt buộc:**

1. `permits` liệt kê tường minh (hoặc nếu tất cả nằm cùng file thì có thể bỏ).
2. Mỗi subclass phải khai báo một trong 3 modifier:
   - `final` — chặn tiếp tục kế thừa.
   - `sealed` — giới hạn tiếp tầng kế.
   - `non-sealed` — mở lại cho công chúng.

### 2.3. Vì sao sealed đáng giá?

Mục này giải thích phần thưởng khi chấp nhận giới hạn: compiler giờ biết **toàn bộ** các case, nên có thể bắt lỗi thiếu case ngay lúc compile.

**Tầng 3:** Sealed + pattern matching switch tạo ra **exhaustiveness checking** — compiler ép bạn xử lý **tất cả** các trường hợp, như giám thị chấm bài: thiếu một câu là bị gạch ngay, không chờ đến lúc chạy mới lộ:

> 📄 **Code ví dụ:** [`examples/08-2-3-vi-sao-sealed-ang-gia.java`](examples/08-2-3-vi-sao-sealed-ang-gia.java)

Đây chính là mô hình **algebraic data type (ADT)** của các ngôn ngữ hàm, giờ đã có trong Java. Dùng nó khi: domain có số loại **hạn chế và ổn định** (loại thanh toán, trạng thái đơn hàng, node của cây cú pháp).

❌ **Pitfall:** Dùng sealed cho domain mở (loại plugin, listener)... bạn sẽ phải sửa file sealed mỗi lần thêm plugin — mất tính mở rộng, như ở chung một chung cư đóng cửa mà cứ có người mới chuyển đến là phải xin phép ban quản lý.

> **Tóm gọn 1 câu:** Sealed là "danh sách khách mời" — cho compiler biết đủ mọi case để nó gác thiếu case ngay lúc biên dịch.

## 3. Pattern matching cho instanceof (Java 16+)

Mục này nói về cách kiểm tra kiểu và "bắt tay" luôn biến đã ép kiểu trong một dòng duy nhất — bỏ được khối if/else cast lồng nhau dài cổ.

> 📄 **Code ví dụ:** [`examples/09-3-pattern-matching-cho-instanceof-java-16.java`](examples/09-3-pattern-matching-cho-instanceof-java-16.java)

> **Tóm gọn 1 câu:** `instanceof` pattern matching gộp "kiểm tra kiểu + ép kiểu + khai báo biến" vào một dòng — như quét mã vạch là biết luôn giá và tên.

## 4. Pattern matching cho switch (Java 21)

### 4.1. Switch cổ điển vs hiện đại

> 📄 **Code ví dụ:** [`examples/10-4-1-switch-co-ien-vs-hien-ai.java`](examples/10-4-1-switch-co-ien-vs-hien-ai.java)

### 4.2. Pattern matching trong switch label

> 📄 **Code ví dụ:** [`examples/11-4-2-pattern-matching-trong-switch-label.java`](examples/11-4-2-pattern-matching-trong-switch-label.java)

### 4.3. Sealed + exhaustiveness = an toàn tuyệt đối

Khi switch hiểu được toàn bộ "menu" của một sealed type, việc bỏ sót một món là lỗi compile — giống như máy POS từ chối in hóa đơn nếu thiếu một dòng.

> 📄 **Code ví dụ:** [`examples/12-4-3-sealed-exhaustiveness-an-toan-tuyet-oi.java`](examples/12-4-3-sealed-exhaustiveness-an-toan-tuyet-oi.java)

> **Tóm gọn 1 câu:** Pattern matching switch + sealed = "chấm bài không bỏ sót" — thiếu case là compiler gạch ngay trước khi chạy.

## 5. Text Block (Java 15+)

Mục này nói về cách viết chuỗi nhiều dòng (SQL, JSON, HTML) mà không phải nối `\n` — như viết thư trên giấy kẻ dòng thay vì chép từng chữ một ra màn hình.

> 📄 **Code ví dụ:** [`examples/13-5-text-block-java-15.java`](examples/13-5-text-block-java-15.java)

Lý tưởng cho: SQL query, JSON, HTML, Regex, code mẫu trong test.

## 6. Switch expression (Java 14+ cũng cần)

> 📄 **Code ví dụ:** [`examples/14-6-switch-expression-java-14-cung-can.java`](examples/14-6-switch-expression-java-14-cung-can.java)

## 7. Bài tập

1. Viết record `GiaoDich(String ma, double soTien, LocalDate ngay, String loai)`. Thêm validation: `soTien > 0`. Viết static factory method `giaoDichPhatSinh(Scanner)`.
2. Viết sealed interface `HinhHoc` với `HinhTron`, `HinhChuNhat`, `HinhVuong`. Dùng pattern matching switch (Java 21) để tính diện tích, đảm bảo exhaustiveness.
3. Viết `String moTa(Object obj)` dùng `instanceof` pattern matching (Java 16+) — không dùng `if/else` lồng nhau.
4. Tạo DSL JSON đơn giản dùng text block + record: mỗi record có `toJson()` trả xâu JSON hợp lệ.
5. Viết AST (cây cú pháp trừu tượng) cho biểu thức số học: `Expr` sealed, các subclass record, viết hàm `evaluate` dùng switch expression exhaustiveness.
6. So sánh `equals()` của 2 record cùng giá trị — chúng bằng nhau không? Tại sao? Minh họa dùng `HashMap<PersonRecord, String>` và so sánh với POJO.

## 8. Tóm tắt

- **record** = immutable data carrier tự sinh getter/equals/hashCode/toString; dùng cho DTO, event, message; thêm validation qua compact constructor.
- **sealed** = giới hạn đúng số loại kế thừa; kết hợp với switch/pattern matching tạo exhaustiveness checking.
- **pattern matching instanceof** (Java 16+) = khai báo biến ngay trong `instanceof`, giảm 50% code kiểm tra kiểu.
- **pattern matching switch** (Java 21) = switch expression với nhãn dạng `case T t -> expr`, hỗ trợ `when` guard và record destructuring.
- **text block** = chuỗi nhiều dòng không cần `\n` và `\`"`; dùng cho SQL, JSON, HTML.
- Tất cả 4 tính năng này hướng tới mục tiêu: **immutability by default**, **compile-time exhaustiveness**, **less boilerplate**.

> **Tóm gọn 1 câu:** Record cắt boilerplate, sealed khóa số lượng loại, pattern matching tận dụng cả hai — tất cả nhằm chuyển phần "nhớ hết mọi trường hợp" từ đầu bạn sang cho compiler.
