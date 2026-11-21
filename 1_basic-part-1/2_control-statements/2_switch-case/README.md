# Switch — từ cổ điển đến Pattern Matching (Java 21+)

> `switch` cho phép so sánh một giá trị với danh sách các case. Java hiện đại đã biến switch từ "dãy if-else có break" thành **switch expression** gọn gàng và thậm chí **match theo kiểu dữ liệu**.

## 1. Switch cổ điển

Mục này mở đầu với dạng switch gốc: so giá trị và rẽ nhánh bằng case/break. Đọc để hiểu cách hoạt động — và hiểu vì sao Java buộc phải ra đời bản nâng cấp ở các mục sau.

**Tầng 1 — Trực giác:** `if-else` là **chuỗi câu hỏi có/không** ("nếu A thì... nếu B thì..."). `switch` là **bảng tra cứu**: đưa giá trị vào, chọn đúng ô tương ứng — như nút thang máy, bạn bấm đúng tầng là đi đúng cửa, không phải hỏi "có phải tầng 3 không?" từng tầng một.

> 📄 **Code ví dụ:** [`examples/01-1-switch-co-ien.java`](examples/01-1-switch-co-ien.java)

❌ **Pitfall — quên `break`:** code **rơi xuyên** (fallthrough) xuống case sau:

> 📄 **Code ví dụ:** [`examples/02-1-switch-co-ien.java`](examples/02-1-switch-co-ien.java)

Đây là nguồn bug kinh điển — chính vì vậy Java 14 đã ra **switch expression**.

> **Tóm gọn 1 câu:** Switch cổ điển là bảng tra cứu có "cửa trượt" — quên `break` là trượt xuyên qua các ô bên dưới.

## 2. Switch expression (Java 14+) — arrow `->`

Mục này giới thiệu bản nâng cấp of switch: mũi tên `->` thay cho break, gọn hơn và an toàn hơn. Đọc để biết cú pháp hiện đại bạn sẽ dùng trong code mới.

> 📄 **Code ví dụ:** [`examples/03-2-switch-expression-java-14-arrow.java`](examples/03-2-switch-expression-java-14-arrow.java)

**Vì sao arrow `->` an toàn hơn?** Không có fallthrough, và compiler **bắt buộc** mọi case phải được xử lý (exhaustiveness) — thiếu case là lỗi biên dịch. Ví von: bản cổ điển là cửa trượt mở kỳ hỉ (quên break là tuột xuống tầng dưới), bản expression là cửa đóng chặt từng phòng — hết phòng này qua phòng khác phải "được phép".

> **Tóm gọn 1 câu:** Mũi tên `->` "kín cửa" hơn break — không trượt case, thiếu case compiler còn gõ cửa nhắc bạn.

## 3. Pattern matching switch (Java 21+) — switch theo KIỂU

Mục này nâng switch lên tầm cao mới: thay vì so giá trị, switch so **kiểu dữ liệu** của đối tượng. Đọc để thay thế những chuỗi `if instanceof` dài lê thê.

Trước Java 21, `switch` chỉ so sánh giá trị bằng. Java 21 cho phép switch theo **kiểu dữ liệu** — thay thế chuỗi `if instanceof` dài:

> 📄 **Code ví dụ:** [`examples/04-3-pattern-matching-switch-java-21-switch-theo.java`](examples/04-3-pattern-matching-switch-java-21-switch-theo.java)

**Điểm mới:**

- `case Integer i` — vừa kiểm tra kiểu vừa **binding biến** `i`.
- `when` — điều kiện bổ sung (guard) tinh chỉnh case.
- `case null` — cuối cùng switch xử lý được null mà không ném NPE.
- Compiler kiểm tra **exhaustive**: nếu bạn switch trên `Object` mà không có `default`, code không biên dịch được.

> **Tóm gọn 1 câu:** Pattern matching switch là "bảng phân loại hành lý theo chất liệu" — nhìn vào kiểu của từng món để xếp vào đúng quầy, kèm điều kiện `when` tinh chỉnh.

## 4. Pattern matching + Record destructuring

Mục này kết hợp switch với record để vừa phân loại vừa "mổ" lấy dữ liệu bên trong trong một dòng. Đọc để thấy cú pháp hiện đại gọn đến mức nào.

> 📄 **Code ví dụ:** [`examples/05-4-pattern-matching-record-destructuring.java`](examples/05-4-pattern-matching-record-destructuring.java)

Kết hợp với **sealed interface** → compiler kiểm tra đủ mọi nhánh (xem chi tiết ở bài 2_basic-part-2/7_miscellaneous).

> **Tóm gọn 1 câu:** Switch + record destructuring là "mở phong bì ngay tại quầy" — phân loại và rút dữ liệu bên trong chỉ trong một case duy nhất.

## 5. switch trên String và enum

Mục này bổ sung hai kiểu dữ liệu hay dùng switch nhất ngoài số: chuỗi và enum. Đọc để biết switch "ăn" được gì.

> 📄 **Code ví dụ:** [`examples/06-5-switch-tren-string-va-enum.java`](examples/06-5-switch-tren-string-va-enum.java)

> **Tóm gọn 1 câu:** String và enum là "khách quen" của switch — enum còn được compiler gác thiếu case miễn phí.

## 6. ❌/✅ Pitfalls tổng hợp

Mục này gom các lỗi hay gặp của switch vào một file ví dụ duy nhất để bạn soi và tự rút kinh nghiệm.

> 📄 **Code ví dụ:** [`examples/07-6-pitfalls-tong-hop.java`](examples/07-6-pitfalls-tong-hop.java)

> **Tóm gọn 1 câu:** Xem pitfalls người khác mắc trước — rẻ hơn nhiều so với tự mắc rồi debug lúc 2 giờ sáng.

## 6. Bài tập

1. Viết switch expression chuyển tháng (1–12) thành mùa; thiếu case → compiler báo lỗi, quan sát.
2. Viết `static String dienGiai(Object o)` dùng pattern matching switch với `when`: số dương/âm, chuỗi rỗng/không rỗng, null, còn lại.
3. Cho `record GiaoDich(String loai, double tien)`; viết switch tính phí giao dịch theo loại (chuyển tiền: 0.1%, rút tiền: 5.000đ, chuyển khác...).
4. So sánh chuỗi if-else vs switch expression cho bài toán tính điểm học lực (A/B/C/D/F) — bản nào dễ đọc hơn, vì sao?

## 7. Tóm tắt

- Switch cổ điển: dùng `break` đúng, cẩn thận fallthrough.
- **Switch expression** (Java 14+): arrow `->`, gọn, trả giá trị, không rơi xuyên.
- **Pattern matching switch** (Java 21+): switch theo kiểu, `when` guard, `case null`, record destructuring, exhaustiveness kiểm tra lúc compile.
- Dùng switch khi ánh xạ giá trị/kiểu → kết quả; dùng if-else khi điều kiện là khoảng giá trị phức tạp.

> **Tóm gọn 1 câu:** Switch đi từ "bảng tra có cửa trượt" đến "bộ máy phân loại thông minh" — dùng khi ánh xạ giá trị/kiểu ra kết quả, và để if-else lo những điều kiện dạng khoảng.
