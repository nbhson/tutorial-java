# JDK, JRE, JVM và cách khởi động với Java 25

> Trước khi viết dòng code Java đầu tiên, bạn cần hiểu ba chữ viết tắt làm nền tảng của cả hệ sinh thái: **JVM**, **JRE**, **JDK** — và biết dùng `jshell`, công cụ thử nghiệm code tức thì có sẵn trong JDK.

## 1. Ba tầng: JDK ⊃ JRE ⊃ JVM

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

## 2. Bytecode — "vùng đất trung gian" của Java

Chu trình của một chương trình Java:

```
Hello.java  --javac-->  Hello.class (bytecode)  --JVM-->  chạy trên OS bất kỳ
   (mã nguồn)              (trung gian)              (Windows, macOS, Linux...)
```

**Vì sao thiết kế này?** "Write once, run anywhere": bạn biên dịch **một lần** ra `.class`, file đó chạy trên JVM của Windows, macOS hay Linux đều được. JVM dịch bytecode thành lệnh máy gốc khi chạy (qua JIT compiler — Just-In-Time, càng chạy càng tối ưu).

## 3. JDK 25 — phiên bản LTS hiện tại

Java phát hành mỗi 6 tháng một phiên bản; mỗi 2 năm có một bản **LTS** (Long-Term Support): 8 → 11 → 17 → 21 → **25**. Cho học tập và dự án production, hãy dùng Java 25 LTS (9/2025).

```bash
# Kiem tra da cai chua
java -version    # java version "25" ...
javac -version   # javac 25
```

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

## 4. jshell — REPL của Java

**Tầng 1 — Trực giác:** `jshell` giống **máy tính bỏ túi**: gõ một dòng Java, thấy kết quả ngay — không cần tạo project, class, `main`.

**Tầng 2 — Cách dùng:**

```bash
$ jshell
|  Welcome to JShell -- Version 25
|  For an introduction type: /help intro

jshell> 2 + 3
$1 ==> 5

jshell> "Hello".toUpperCase()
$2 ==> "HELLO"

jshell> int[] so = {5, 3, 8}
so ==> int[3] { 5, 3, 8 }

jshell> java.util.Arrays.sort(so)

jshell> so
so ==> int[3] { 3, 5, 8 }

jshell> int tong(int a, int b) {
   ...>     return a + b;
   ...> }
|  created method tong(int,int)

jshell> tong(10, 20)
$3 ==> 30

jshell> /exit
```

**Lệnh jshell hữu ích:**

| Lệnh | Ý nghĩa |
|------|---------|
| `/vars` | Xem các biến đã khai báo |
| `/methods` | Xem các method đã định nghĩa |
| `/list` | Xem lại code đã gõ |
| `/open file.jsh` | Chạy code từ file |
| `/exit` | Thoát |

**Vì sao học jshell sớm?** Khi học cú pháp mới (Stream, regex, Optional...), bạn có thể thử nghiệm trong 10 giây thay vì tạo project Eclipse/IntelliJ mất 2 phút. Thói quen "thử trong jshell trước khi viết vào code" tăng tốc việc học đáng kể.

❌ **Pitfall:** jshell cho phép khai báo không kiểu (`var x = 5`, thậm chí chỉ `5+3`), và không bắt buộc class. Đừng mang thói quen đó về file `.java` thật.

## 5. Chuẩn bị môi trường — tóm tắt nhanh

Chi tiết cài đặt từng bước (Windows/macOS/Linux) xem bài [0_setup](../../../0_setup/README.md). Tóm tắt:

1. Tải JDK 25 từ [Adoptium](https://adoptium.net/) (Temurin — bản miễn phí, phổ biến nhất) hoặc Oracle JDK.
2. Cài đặt, xác nhận `java -version` và `javac -version` hoạt động.
3. Chuẩn bị IDE: **IntelliJ IDEA Community** (khuyên dùng) hoặc **Eclipse** / **VS Code** + Extension Pack for Java.

```bash
# macOS — dung Homebrew
brew install --cask temurin@25

# Windows — tai installer tu adoptium.net hoac dung winget
winget install EclipseAdoptium.Temurin.25.JDK

# Ubuntu/Debian
sudo apt install temurin-25-jdk   # sau khi them PPA cua Adoptium
```

## 6. Compile & chạy bằng tay (hiểu bản chất trước khi dùng IDE)

```bash
# Buoc 1: bien dich — javac tao ra Hello.class (bytecode)
javac Hello.java

# Buoc 2: chay — JVM doc bytecode, khong co .java
java Hello
```

```java
// Hello.java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Xin chao Java 25!");
    }
}
```

Từ **Java 21**, bạn còn có thể chạy **một file duy nhất** không cần biên dịch trước:

```bash
java Hello.java   # JVM tu compile roi chay — tuyet voi cho hoc tap
```

Và từ Java 25, **Compact Source Files** cho phép viết chậm chương trình đầu tiên không cần class:

```java
// Hello.java — Java 25: implicit classes & instance main
void main() {
    IO.println("Xin chao Java 25!");
}
```

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
