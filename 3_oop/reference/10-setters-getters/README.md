# Setters, Getters và hạn chế của chúng — Builder Pattern

> Getter/setter là thứ đầu tiên bạn học về encapsulation — nhưng cũng là nơi **lạm dụng nhiều nhất**. Bài này chỉ ra khi nào setter là anti-pattern, và giới thiệu **Builder Pattern** — cách tạo object an toàn thay thế.

## 1. Nhắc lại: Getter/Setter là gì?

Mục này ôn nhanh nền móng trước khi phê bình: getter/setter sinh ra để làm gì và tại sao ai cũng học nó đầu tiên.

**Tầng 1 — Trực giác:** Biến `private` là **cửa khóa**; getter/setter là **lễ tân** đứng trước cửa — khách không tự vào phòng, phải thông qua lễ tân kiểm tra.

**Tầng 2 — Định nghĩa:** Encapsulation = ẩn dữ liệu (`private`) + chỉ cho thao tác qua method công khai. Getter đọc giá trị, setter gán giá trị.

> 📄 **Code ví dụ:** [`examples/01-1-nhac-lai-getter-setter-la-gi.java`](examples/01-1-nhac-lai-getter-setter-la-gi.java)

> **Tóm gọn 1 câu:** Getter/setter là "lễ tân" của class — đúng vai trò thì giữ phép lịch sự, nhưng để lễ tân làm thay cả việc kinh doanh thì hỏng (mục sau nói kỹ).

## 2. Khi nào setter là anti-pattern?

Mục này là trái tim của bài: chỉ ra mặt tối của setter — tại sao "đặt ai cũng vào được" lại phá hỏng dữ liệu, và đâu là cách đặt câu hỏi đúng trước khi viết một setter.

### 2.1. Setter "mù" — mọi invariant có thể bị phá

> 📄 **Code ví dụ:** [`examples/02-2-1-setter-mu-moi-invariant-co-the.java`](examples/02-2-1-setter-mu-moi-invariant-co-the.java)

**Tầng 3 — Vì sao có hại?** Setter vô điều kiện biến class thành **túi dữ liệu mở**: trạng thái hợp lệ chỉ tồn tại "đôi lúc", và mọi method khác phải tự phòng thủ kiểm tra null/âm/rỗng. Object có **constructor hợp lệ duy nhất** thì trạng thái sai **không thể tồn tại**.

### 2.2. So sánh ❌/✅

> 📄 **Code ví dụ:** [`examples/03-2-2-so-sanh.java`](examples/03-2-2-so-sanh.java)

**Nguyên tắc: đặt câu hỏi "hành vi nghiệp vụ này là gì?"** — `napTien`, `rutTien`, `guiEmail`, `dongDon` — thay vì `setX`/`setY` máy móc.

### 2.3. Getter cũng có thể là vấn đề

❌ Getter trả về **tham chiếu mảng/collection** — bên ngoài sửa được nội dung bên trong:

> 📄 **Code ví dụ:** [`examples/04-2-3-getter-cung-co-the-la-van.java`](examples/04-2-3-getter-cung-co-the-la-van.java)

Xem code mẫu `10-setters-getters/src/Main.java`: `setSchoolYear` nhận mảng và lưu `schoolYear.clone()` — đây là cách **phòng thủ đúng** khi bắt buộc nhận mảng: copy dữ liệu vào, tránh bên ngoài thay đổi mảng gốc sau khi set. Ví von: thay vì đưa cho khách bản gốc sơ đồ phòng, hãy đưa bản photo — khách vẽ vẽ lung tung trên bản photo thì bản gốc vẫn nguyên.

## 3. Builder Pattern — thay thế constructor nhiều tham số

Mục này giới thiệu "người sửa sai" cho cả setter lẫn constructor dài: Builder Pattern — dựng object bằng từng câu có tên rõ nghĩa, và chỉ "bàn giao" khi đủ dữ liệu.

### 3.1. Vấn đề: "telescoping constructor"

> 📄 **Code ví dụ:** [`examples/05-3-1-van-e-telescoping-constructor.java`](examples/05-3-1-van-e-telescoping-constructor.java)

### 3.2. Builder — gọi bằng tên, đọc như tiếng Anh

> 📄 **Code ví dụ:** [`examples/06-3-2-builder-goi-bang-ten-oc-nhu.java`](examples/06-3-2-builder-goi-bang-ten-oc-nhu.java)

### 3.3. Vì sao Builder tốt hơn setter?

| Tiêu chí | Setter + no-arg constructor | Builder |
|----------|------------------------------|---------|
| Trạng thái "nửa vời" tồn tại? | ✗ Có — object được tạo trước, điền sau | ✅ Không — object chỉ tồn tại khi đủ dữ liệu |
| Validation tập trung? | ✗ Rải rác mỗi setter | ✅ Một chỗ trong `build()` |
| Immutable được? | ✗ Không (setter = mutable) | ✅ Có — tất cả field `final` |
| Thread-safe? | ✗ Phải lo đồng bộ | ✅ Nếu immutable |
| Đọc code gọi | `setX(...); setY(...)` — thứ tự tùy ý | Chuỗi method có tên rõ nghĩa |

> **Cách đọc bảng này:** mỗi dòng là một tiêu chí so sánh; cột giữa là cách làm cũ (setter + no-arg constructor), cột phải là Builder. Đọc từ trên xuống để thấy Builder thắng ở mọi "hạng thi" — đặc biệt hai dòng đầu (trạng thái nửa vời và validation) là lý do quyết định.

**Tầng 3 — Vì sao immutable đáng giá?** Object không đổi trạng thái thì: chia sẻ giữa các thread an toàn tuyệt đối, dùng làm key Map/phần tử Set không lo hash thay đổi, và dễ suy luận (nhìn là biết giá trị — không cần tìm "ai set nó ở đâu"). Ví von: immutable object giống như tấm vé in sẵn — ai cầm vé cũng thấy cùng một thông tin, không ai được ghi đè lên vé của người khác.

**Khi nào vẫn dùng setter hợp lệ?**
- Framework yêu cầu (JPA entity, Jackson deserialize, JavaBean của thư viện cũ) — thỏa hiệp chấp nhận được.
- Object thật sự mutable theo nghiệp vụ, có phương thức nghiệp vụ bảo vệ invariant (như `napTien`).

## 4. Record + validation — lựa chọn hiện đại (Java 16+)

Mục này nói về "lối tắt đời mới": nếu object chỉ là dữ liệu, record của Java 16+ thay thế phần lớn nhu cầu viết Builder thủ công.

Khi object chỉ là dữ liệu, `record` + compact constructor thay thế phần lớn nhu cầu Builder:

> 📄 **Code ví dụ:** [`examples/07-4-record-validation-lua-chon-hien-ai-java.java`](examples/07-4-record-validation-lua-chon-hien-ai-java.java)

Chỉ khi có **nhiều tham số tùy chọn** thì Builder mới hơn hẳn record.

## 5. Bài tập

Mục này là "bài tập về nhà" để biến lý thuyết anti-pattern thành phản xạ code thật của bạn.

1. Refactor `TaiKhoan` có 4 setter thành class immutable với constructor + `napTien`/`rutTien`. Chứng minh không thể tạo tài khoản số dư âm.
2. Viết Builder cho `TaiKhoan` khi có nhiều field tùy chọn (chi nhánh, loại TK, hạn mức...).
3. Viết record `SanPham` với validation, dùng nó làm key của `HashMap` — giải thích vì sao an toàn.
4. Tìm trong dự án của bạn class có > 5 setter; liệt kê invariant bị phá khả dĩ; chuyển sang constructor/Builder.

## 6. Tóm tắt

- Getter/setter là công cụ encapsulation cơ bản, nhưng **setter vô điều kiện = anti-pattern**: cho phép object ở trạng thái không hợp lệ.
- Ưu tiên: **constructor/record với validation** → object hợp lệ ngay từ khi sinh ra.
- Nhiều tham số tùy chọn → **Builder Pattern**: immutable, validation tập trung, code gọi dễ đọc.
- Getter trả collection/mảng → trả bản sao không đổi (`List.copyOf`, `clone()` như code mẫu).
- Setter chỉ chấp nhận khi framework bắt buộc hoặc khi object thật sự mutable có method nghiệp vụ bảo vệ.

> **Tóm gọn 1 câu:** Setter vô điều kiện là "cửa để ngỏ" — hãy khóa bằng constructor/record có validation, và mở của rộng có kiểm soát bằng Builder khi tham số nhiều.
