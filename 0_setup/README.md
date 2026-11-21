# 0_setup — Cài đặt môi trường Java 25 từ con số 0

> Bài đầu tiên của toàn khóa học: cài **Java 25 LTS**, cấu hình biến môi trường, cài IDE, chạy chương trình **Hello World** đầu tiên — trên cả Windows, macOS và Linux.

## 1. Chọn JDK nào? Vì sao là Java 25?

Mục này trả lời câu hỏi đầu tiên khi vào nghề: cài **bản JDK nào** giữa cả chợ "hãng nấu" Java. Đọc xong bạn sẽ chọn được ngay, không phải lơ ngơ giữa Oracle, Temurin, GraalVM.

**Tầng 1 — Trực giác:** JDK có nhiều "hãng nấu" (distributions) nhưng công thức giống nhau — như mua cơm tấm, quán nào bán cũng cơm tấm, khác nhau ở chỗ nào sạch, ai bán lâu năm. Chọn bản **LTS** (Long-Term Support) để ổn định, chọn nguồn phân phối phổ biến để dễ tìm tài liệu.

**Tầng 2 — Định nghĩa:**

| Khái niệm | Giải thích |
|-----------|------------|
| **LTS** | Phiên bản được hỗ trợ dài hạn (8 → 11 → 17 → 21 → **25**). Học và làm production nên dùng LTS. |
| **Temurin (Adoptium)** | Bản JDK mã nguồn mở phổ biến nhất, miễn phí, từ Eclipse Foundation — **khuyên dùng**. |
| **Oracle JDK** | Bản chính thức của Oracle, miễn phí cho mục đích phát triển. |
| **GraalVM** | JDK đặc biệt: biên dịch native image, khởi động siêu nhanh. |

> **Cách đọc bảng này:** mỗi dòng là một "hãng nấu" JDK; cột Giải thích nói lên tính cách của từng bản. Chỉ cần nhớ dòng được in đậm (Temurin) là ra quyết định được — 3 dòng còn lại để nhận diện khi tình cờ gặp.

**Tầng 3 — Vì sao nên dùng Temurin 25?** Miễn phí cho mọi mục đích (kể cả thương mại), cập nhật bảo mật dài hạn, và hầu hết tài liệu/tutorial cộng đồng đều viết theo nó. Nên tránh JDK 8/11 cũ — bạn sẽ bỏ lỡ records, sealed, virtual threads, pattern matching — những tính năng khóa học dùng xuyên suốt.

> **Tóm gọn 1 câu:** Chọn JDK giống chọn quán cơm quen — lấy bản LTS phổ biến (Temurin 25), đừng thử món lạ khi mới vào nghề.

## 2. Cài đặt theo hệ điều hành

Mục này là phần "tay nghề": cài JDK đúng cách cho máy bạn (Windows, macOS hay Linux) và cấu hình biến môi trường — làm sai chỗ này là mọi bài sau đều kẹt. Cứ đọc đúng mục hệ điều hành của mình, bỏ qua hai mục còn lại.

### 2.1. Windows

**Cách 1 — Installer (dễ nhất):**

1. Vào [adoptium.net](https://adoptium.net/) → tải **Temurin 25 (LTS)** cho Windows, chọn `.msi`.
2. Trong lúc cài, **bật 2 tùy chọn**: `Add to PATH` và `Set JAVA_HOME`.
3. Mở **PowerShell** kiểm tra:

> 📄 **Code ví dụ:** [`examples/01-2-1-windows.ps1`](examples/01-2-1-windows.ps1)

**Cách 2 — winget:**

> 📄 **Code ví dụ:** [`examples/02-2-1-windows.ps1`](examples/02-2-1-windows.ps1)

### 2.2. macOS

> 📄 **Code ví dụ:** [`examples/03-2-2-macos.sh`](examples/03-2-2-macos.sh)

macOS tự quản thư mục `/Library/Java/JavaVirtualMachines` — nếu có nhiều JDK, chuyển đổi bằng:

> 📄 **Code ví dụ:** [`examples/04-2-2-macos.sh`](examples/04-2-2-macos.sh)

### 2.3. Linux (Ubuntu/Debian)

> 📄 **Code ví dụ:** [`examples/05-2-3-linux-ubuntu-debian.sh`](examples/05-2-3-linux-ubuntu-debian.sh)

### 2.4. Cấu hình biến môi trường thủ công (nếu installer không làm)

**Windows:** Settings → System → Advanced system settings → Environment Variables:
- `JAVA_HOME` = `C:\Program Files\Eclipse Adoptium\jdk-25`
- Thêm `%JAVA_HOME%\bin` vào `Path`.

**macOS/Linux** — thêm vào `~/.zshrc` hoặc `~/.bashrc`:

> 📄 **Code ví dụ:** [`examples/06-2-4-cau-hinh-bien-moi-truong-thu.sh`](examples/06-2-4-cau-hinh-bien-moi-truong-thu.sh)

❌ **Pitfall:** Cài nhiều JDK mà `JAVA_HOME` trỏ về bản cũ → IDE dùng bản mới nhưng terminal build dùng bản cũ, lỗi "release version 25 not supported". Luôn kiểm tra bằng:

> 📄 **Code ví dụ:** [`examples/07-2-4-cau-hinh-bien-moi-truong-thu.sh`](examples/07-2-4-cau-hinh-bien-moi-truong-thu.sh)

> **Tóm gọn 1 câu:** Cài JDK theo đúng hệ điều hành của bạn và để `JAVA_HOME` trỏ đúng chỗ — biến môi trường chính là "sổ địa chỉ" để máy tìm thấy Java.

## 3. Cài IDE

Mục này chọn "xưởng làm việc" của bạn: IDE là nơi viết code, chạy code, gỡ lỗi mỗi ngày. Có 3 lựa chọn phổ biến — đọc để biết nên bắt đầu với cái nào và vì sao.

### 3.1. IntelliJ IDEA Community ✅ (khuyên dùng)

1. Tải tại [jetbrains.com/idea](https://www.jetbrains.com/idea/download/) — bản **Community** miễn phí.
2. New Project → chọn JDK 25 → ngôn ngữ Java → Create.
3. Phím tắt cần nhớ: `Shift + Shift` (tìm mọi thứ), `Alt + Enter` (sửa lỗi gợi ý), `Shift + F10` (chạy).

### 3.2. VS Code — nhẹ hơn

1. Cài **Extension Pack for Java** (Microsoft).
2. Mở thư mục project, VS Code tự nhận JDK 25.

### 3.3. Eclipse

Tải từ [eclipse.org](https://www.eclipse.org/downloads/) — nhiều repo Java cổ (như repo này) vẫn dùng cấu trúc Eclipse (.project/.settings), nên Eclipse mở rất tự nhiên.

> **Tóm gọn 1 câu:** IDE là bàn làm việc của bạn — IntelliJ Community là chỗ ngồi mặc định, VS Code cho ai thích nhẹ, Eclipse cho ai gặp repo cũ.

## 4. Hello World — chương trình đầu tiên

Mục này là nghi thức "khai trương": chạy được dòng chữ Hello World theo 4 cách khác nhau. Đọc để hiểu mỗi cách JVM làm gì — không chỉ bấm nút Run cho có.

### Cách 1: trong IDE

> 📄 **Code ví dụ:** [`examples/08-cach-1-trong-ide.java`](examples/08-cach-1-trong-ide.java)

Nhấn nút ▶ Run. Nếu gặp lỗi "class name must match file name" — tên file phải là `Main.java` đúng từng chữ.

### Cách 2: dòng lệnh thuần — hiểu JVM làm gì

> 📄 **Code ví dụ:** [`examples/09-cach-2-dong-lenh-thuan-hieu-jvm-lam.sh`](examples/09-cach-2-dong-lenh-thuan-hieu-jvm-lam.sh)

### Cách 3: Java 21+ — chạy 1 file không cần compile

> 📄 **Code ví dụ:** [`examples/10-cach-3-java-21-chay-1-file-khong.sh`](examples/10-cach-3-java-21-chay-1-file-khong.sh)

### Cách 4: jshell — thử nghiệm tức thì

> 📄 **Code ví dụ:** [`examples/11-cach-4-jshell-thu-nghiem-tuc-thi.sh`](examples/11-cach-4-jshell-thu-nghiem-tuc-thi.sh)

> **Tóm gọn 1 câu:** Chạy được Hello World theo 4 cách nghĩa là cả chuỗi "viết → biên dịch → JVM chạy" của bạn đã thông suốt từ IDE đến dòng lệnh.

## 5. Maven & Gradle — quản lý dự án và thư viện

Mục này giới thiệu "người quản gia" của dự án Java: build tool. Đọc để hiểu vì sao không ai tự tay tải file `.jar` nữa, và để chuẩn bị tinh thần trước khi sang Spring Boot.

**Tầng 1 — Trực giác:** Khi dự án cần thư viện ngoài (Jackson, JUnit, Spring...), bạn không tải file `.jar` thủ công. Build tool là **người quản gia**: khai báo cần gì, nó tải về đúng phiên bản, compile, test, đóng gói — giống như gửi danh sách đi chợ cho người giúp việc, bạn chỉ việc vào bếp nấu.

**Tầng 2 — So sánh:**

| Tiêu chí | Maven | Gradle |
|----------|-------|--------|
| File cấu hình | `pom.xml` (XML) | `build.gradle` (Groovy/Kotlin) |
| Phong cách | Quy ước chặt, dễ đọc | Linh hoạt, nhanh, hiện đại |
| Phổ biến | Doanh nghiệp, cũ hơn | Android, Spring Boot mới |
| Khóa học này | ✅ Giới thiệu | ✅ Cũng OK |

> **Cách đọc bảng này:** đọc theo từng hàng — mỗi hàng là một tiêu chí để chọn, cột nào thắng ở tiêu chí bạn quan tâm thì nghiêng về cột đó. Với người mới, khác biệt chưa đáng để đau đầu: cả hai đều xài được.

**Tầng 3 — Vì sao học sớm?** Từ phần API Frameworks (Spring Boot), mọi project đều sinh ra qua build tool. Nắm pom.xml/gradle cơ bản từ bây giờ giúp bạn không "sốc" khi sang Spring.

### Maven — pom.xml tối thiểu

> 📄 **Code ví dụ:** [`examples/12-maven-pom-xml-toi-thieu.xml`](examples/12-maven-pom-xml-toi-thieu.xml)

> 📄 **Code ví dụ:** [`examples/13-maven-pom-xml-toi-thieu.sh`](examples/13-maven-pom-xml-toi-thieu.sh)

### Tạo project Spring Boot nhanh (preview cho phần 5)

Truy cập [start.spring.io](https://start.spring.io/) → chọn Maven + Java 25 + dependencies → Download — bạn có bộ khung dự án hoàn chỉnh trong 30 giây, nhanh như gọi món có sẵn thay vì nấu từ đầu.

> **Tóm gọn 1 câu:** Build tool là người quản gia lo đi chợ, nấu nướng, dọn dẹp thay bạn — chỉ cần khai báo "cần gì" trong pom.xml hay build.gradle.

## 6. Kiểm tra cuối: checklist môi trường

Mục này là bản "kiểm kê hàng hóa" trước khi lên đường: một đoạn script chạy qua xem JDK, biến môi trường, IDE đã sẵn sàng chưa. Chạy xong biết ngay mình thiếu gì.

> 📄 **Code ví dụ:** [`examples/14-6-kiem-tra-cuoi-checklist-moi-truong.sh`](examples/14-6-kiem-tra-cuoi-checklist-moi-truong.sh)

Chạy được Hello World cả 4 cách (IDE, javac+java, single-file, jshell) → sẵn sàng vào bài [1_compiler](./1_basic-part-1/1_basic/1_compiler/README.md).

## 7. Bài tập

1. Cài JDK 25 (Temurin) và IntelliJ IDEA Community. Chụp màn hình `java -version` thành công.
2. Tạo project "hello-java25", chạy Hello World bằng cả 4 cách ở mục 4.
3. Cài Maven, tạo dự án bằng `mvn archetype:generate`, chạy `mvn compile`.
4. Thử cài 2 JDK (21 và 25), chuyển đổi bằng `JAVA_HOME` — ghi lại các bước.
5. Vào jshell, thử: khai báo biến, viết method, dùng `List.of()` và stream — làm quen trước bài Collections.

## 8. Tóm tắt

- Dùng **Java 25 LTS** qua **Temurin/Adoptium** — miễn phí, ổn định, chuẩn cộng đồng.
- Kiểm tra môi trường: `java -version` + `javac -version` — thiếu `javac` nghĩa là thiếu JDK.
- IDE: IntelliJ Community là lựa chọn mặc định; Eclipse/VS Code đều tốt.
- Chu trình cơ bản: `.java` → `javac` → `.class` (bytecode) → `java` chạy trên JVM.
- **Maven/Gradle** quản lý thư viện + build; sẽ dùng xuyên suốt từ phần Spring Boot.

> **Tóm gọn 1 câu:** Hôm nay bạn chỉ cần đạt một cột mốc — máy có JDK 25 chuẩn, IDE lên được màn hình Hello World; phần còn lại của khóa học sẽ tự đến.
