# JDK, JRE, JVM và cách khởi động với Java 25

> Trước khi viết dòng code Java đầu tiên, bạn cần hiểu ba chữ viết tắt làm nền tảng của cả hệ sinh thái: **JVM**, **JRE**, **JDK** — và biết dùng `jshell`, công cụ thử nghiệm code tức thì có sẵn trong JDK.

## 1. Ba tầng: JDK ⊃ JRE ⊃ JVM

Mục này làm sáng tỏ ba chữ viết tắt hay bị nhầm lẫn nhất của Java. Đọc để biết mình cần cài cái gì (JDK) và vì sao người chỉ chạy app thì không cần nó.

**Tầng 1 — Trực giác:** Tưởng tưởng một **bếp ăn**:

- **JVM** là **đầu bếp** — người thực sự chế biến món (chạy bytecode).
- **JRE** là **bếp hoàn chỉnh** — đầu bếp + nguyên liệu + dụng cụ (JVM + thư viện chuẩn).
- **JDK** là **cả nhà hàng** — bếp + kho nguyên liệu + phòng phát triển menu (JRE + compiler + công cụ dev).

**Tầng 2 — Định nghĩa:**

| Thành phần | Là gì | Bao gồm |
|------------|-------|---------|
| **JVM** (Java Virtual Machine) | Máy ảo thực thi bytecode, quản lý bộ nhớ, GC | Chỉ là runtime engine |
| **JRE** (Java Runtime Environment) | Môi trường để **chạy** chương trình Java | JVM + thư viện lớp chuẩn |
| **JDK** (Java Development Kit) | Bộ công cụ để **viết và build** chương trình | JRE + `javac` + `jshell` + `javadoc` + debugger... |

> **Cách đọc bảng này:** đọc từ dưới lên theo cột "Bao gồm" — mỗi tầng chứa tầng trước đó (JDK chứa JRE, JRE chứa JVM), nên chỉ cần nhớ "cái gì viết code thì phải có tầng trên cùng".

```
┌─────────────────────────────────────┐
│ JDK (viết code)                     │
│  ┌───────────────────────────────┐  │
│  │ JRE (chạy code)               │  │
│  │  ┌─────────────────────────┐  │  │
│  │  │ JVM (thực thi bytecode) │  │  │
│  │  └─────────────────────────┘  │  │
│  │  + thư viện chuẩn (java.lang, │  │
│  │     java.util, ...)           │  │
│  └───────────────────────────────┘  │
│  javac, jshell, jar, jdb, jlink...  │
└─────────────────────────────────────┘
```

**Tầng 3 — Vì sao cần phân biệt?**

- Người **dùng** ứng dụng Java (chạy file `.jar` đã build) chỉ cần JRE.
- Bạn — người **viết** code — cần JDK, vì `javac` (trình biên dịch) chỉ có trong JDK.
- Từ Java 11, Oracle không còn phát hành JRE riêng: JDK chính là JRE + công cụ. Bạn sẽ hầu như chỉ gặp JDK.

❌ **Pitfall:** Cài JDK xong chạy `java -version` thấy OK nhưng `javac -version` báo "command not found" → bạn đang cài **JRE** (hoặc chỉ PATH trỏ về JRE cũ). Cài lại JDK chuẩn và cấu hình `JAVA_HOME`.

> **Tóm gọn 1 câu:** JDK ⊃ JRE ⊃ JVM như nhà hàng ⊃ bếp ⊃ đầu bếp — ai nấu ăn (viết code) cần cả nhà hàng, ai chỉ ăn (chạy app) có cái bếp là đủ.

## 2. Bytecode — "vùng đất trung gian" của Java

Mục này giải thích chương trình Java đi qua "trạm trung chuyển" nào trước khi chạy được trên máy bạn. Đọc để hiểu vì sao một file Java chạy được ở cả Windows lẫn Linux.

Chu trình của một chương trình Java:

```
Hello.java  --javac-->  Hello.class (bytecode)  --JVM-->  chạy trên OS bất kỳ
   (mã nguồn)              (trung gian)              (Windows, macOS, Linux...)
```

**Vì sao thiết kế này?** "Write once, run anywhere": bạn biên dịch **một lần** ra `.class`, file đó chạy trên JVM của Windows, macOS hay Linux đều được. JVM dịch bytecode thành lệnh máy gốc khi chạy (qua JIT compiler — Just-In-Time, càng chạy càng tối ưu). Ví von: bytecode như bản nhạc chép bằng ký hiệu phổ thông — một bản nhạc, dàn nhạc nào (HĐH nào) có người đọc được ký hiệu đó đều diễn được.

> **Tóm gọn 1 câu:** Bytecode là "ngôn ngữ chung trung gian" — viết một lần, JVM của hệ điều hành nào cũng dịch được ra tiếng máy của nó.

## 3. JDK 25 — phiên bản LTS hiện tại

Mục này chọn "tấm vé" phiên bản Java bạn nên đi theo: vì sao là 25, và Java 25 mang theo những vũ khí hiện đại nào mà khóa học sẽ dùng.

Java phát hành mỗi 6 tháng một phiên bản; mỗi 2 năm có một bản **LTS** (Long-Term Support): 8 → 11 → 17 → 21 → **25**. Cho học tập và dự án production, hãy dùng Java 25 LTS (9/2025).

> 📄 **Code ví dụ:** [`examples/01-3-jdk-25-phien-ban-lts-hien-tai.sh`](examples/01-3-jdk-25-phien-ban-lts-hien-tai.sh)

### Các tính năng hiện đại của JDK 25 mà khóa học sẽ dùng

| Tính năng | Phiên bản ổn định | Dùng để |
|-----------|-------------------|---------|
| Virtual Threads | Java 21 (final) | Đa luồng hàng triệu thread |
| Record | Java 16 | Lớp dữ liệu bất biến |
| Sealed classes | Java 17 | Giới hạn cây kế thừa |
| Pattern Matching switch | Java 21 | Switch theo kiểu dữ liệu |
| Text Block | Java 15 | Chuỗi nhiều dòng |
| Compact Source Files | Java 21+ (simple source files) | Viết `main` không cần class wrapper |
| Scoped Values, Structured Concurrency (preview) | Java 21-25 | Concurrency hiện đại |

> **Cách đọc bảng này:** mỗi dòng là một tính năng "đời mới" bạn sẽ gặp lại trong khóa học; cột Dùng để cho biết nó thay bạn làm việc gì. Chưa cần nhớ hết — quay lại bảng này khi gặp tên lạ trong các bài sau.

> **Tóm gọn 1 câu:** JDK 25 LTS là chuyến tàu có lịch chạy dài hạn — lên đúng toa này để khỏi phải đổi vé giữa chừng.

## 4. jshell — REPL của Java

Mục này giới thiệu "máy tính bỏ túi" của Java: gõ một dòng, thấy kết quả ngay. Đọc để có công cụ thử cú pháp mới mà không cần dựng cả project.

**Tầng 1 — Trực giác:** `jshell` giống **máy tính bỏ túi**: gõ một dòng Java, thấy kết quả ngay — không cần tạo project, class, `main`.

**Tầng 2 — Cách dùng:**

> 📄 **Code ví dụ:** [`examples/02-4-jshell-repl-cua-java.sh`](examples/02-4-jshell-repl-cua-java.sh)

**Lệnh jshell hữu ích:**

| Lệnh | Ý nghĩa |
|------|---------|
| `/vars` | Xem các biến đã khai báo |
| `/methods` | Xem các method đã định nghĩa |
| `/list` | Xem lại code đã gõ |
| `/open file.jsh` | Chạy code từ file |
| `/exit` | Thoát |

> **Cách đọc bảng này:** đây là bảng "lệnh điều khiển" của jshell — khi muốn xem lại mình đã làm gì, dùng `/vars`, `/methods`, `/list`; xong việc thì `/exit`. Không cần học thuộc, dùng đến đâu tra đến đó.

**Vì sao học jshell sớm?** Khi học cú pháp mới (Stream, regex, Optional...), bạn có thể thử nghiệm trong 10 giây thay vì tạo project Eclipse/IntelliJ mất 2 phút. Thói quen "thử trong jshell trước khi viết vào code" tăng tốc việc học đáng kể — như nếm thử món ở quầy trước khi quyết định gọi cả phần.

❌ **Pitfall:** jshell cho phép khai báo không kiểu (`var x = 5`, thậm chí chỉ `5+3`), và không bắt buộc class. Đừng mang thói quen đó về file `.java` thật.

> **Tóm gọn 1 câu:** jshell là sân chơi thử-sai-miễn-phí — nếm cú pháp trong 10 giây trước khi đem vào code thật.

## 5. Chuẩn bị môi trường — tóm tắt nhanh

Mục này nén bài cài đặt (chi tiết ở [0_setup](../../../0_setup/README.md)) thành 3 bước ngắn. Đọc nếu bạn đã cài xong và chỉ muốn soát lại.

1. Tải JDK 25 từ [Adoptium](https://adoptium.net/) (Temurin — bản miễn phí, phổ biến nhất) hoặc Oracle JDK.
2. Cài đặt, xác nhận `java -version` và `javac -version` hoạt động.
3. Chuẩn bị IDE: **IntelliJ IDEA Community** (khuyên dùng) hoặc **Eclipse** / **VS Code** + Extension Pack for Java.

> 📄 **Code ví dụ:** [`examples/03-5-chuan-bi-moi-truong-tom-tat-nhanh.sh`](examples/03-5-chuan-bi-moi-truong-tom-tat-nhanh.sh)

> **Tóm gọn 1 câu:** JDK 25 + IDE — hai món đồ duy nhất cần trên bàn trước khi học tiếp.

## 6. Compile & chạy bằng tay (hiểu bản chất trước khi dùng IDE)

Mục này cho bạn "phá xe xem động cơ": tự tay compile và chạy không qua nút Run của IDE. Đọc để hiểu IDE mỗi ngày đang làm giúp mình những bước gì.

> 📄 **Code ví dụ:** [`examples/04-6-compile-chay-bang-tay-hieu-ban-chat.sh`](examples/04-6-compile-chay-bang-tay-hieu-ban-chat.sh)

> 📄 **Code ví dụ:** [`examples/05-6-compile-chay-bang-tay-hieu-ban-chat.java`](examples/05-6-compile-chay-bang-tay-hieu-ban-chat.java)

Từ **Java 21**, bạn còn có thể chạy **một file duy nhất** không cần biên dịch trước:

> 📄 **Code ví dụ:** [`examples/06-6-compile-chay-bang-tay-hieu-ban-chat.sh`](examples/06-6-compile-chay-bang-tay-hieu-ban-chat.sh)

Và từ Java 25, **Compact Source Files** cho phép viết chậm chương trình đầu tiên không cần class:

> 📄 **Code ví dụ:** [`examples/07-6-compile-chay-bang-tay-hieu-ban-chat.java`](examples/07-6-compile-chay-bang-tay-hieu-ban-chat.java)

> **Tóm gọn 1 câu:** Biết tự tay `javac` → `java` là biết "động cơ" của Java — IDE chỉ là vỏ xe bọc bên ngoài.

## 7. Bài tập

1. Cài JDK 25, chạy `java -version` và `javac -version`, chụp lại kết quả.
2. Viết `Hello.java`, compile bằng `javac`, chạy bằng `java`. Xóa `Hello.java`, chạy lại bằng `java Hello` — vẫn chạy được không? Vì sao?
3. Vào `jshell`: khai báo mảng điểm 5 môn, viết method tính trung bình, gọi thử.
4. Chạy `java Hello.java` (single-file). Sửa code, chạy lại — nhận xét về workflow này so với compile thủ công.
5. Tìm hiểu: vì sao file `.class` chạy được trên hệ điều hành khác (thử đọc lại phần bytecode)?

## 8. Tóm tắt

- **JVM** thực thi bytecode; **JRE** = JVM + thư viện (chạy); **JDK** = JRE + compiler + công cụ (phát triển).
- Java biên dịch ra **bytecode** → "write once, run anywhere" trên mọi JVM.
- Dùng **Java 25 LTS**; khóa học dùng virtual threads, records, sealed, pattern matching.
- `jshell` = REPL thử nghiệm code tức thì — công cụ học nhanh nhất.
- `javac` → `java` là chuỗi cơ bản; `java File.java` chạy trực tiếp; Java 25 còn cho phép `void main()` không cần class.

> **Tóm gọn 1 câu:** Ba chữ JDK/JRE/JVM là ba tầng nhà chồng nhau — bạn ở tầng trên cùng (JDK), và bytecode là "chiếc chìa khóa vạn năng" giúp code của bạn chạy trên mọi hệ điều hành.
