# 0_setup — Cài đặt môi trường Java 25 từ con số 0

> Bài đầu tiên của toàn khóa học: cài **Java 25 LTS**, cấu hình biến môi trường, cài IDE, chạy chương trình **Hello World** đầu tiên — trên cả Windows, macOS và Linux.

## 1. Chọn JDK nào? Vì sao là Java 25?

**Tầng 1 — Trực giác:** JDK có nhiều "hãng nấu" (distributions) nhưng công thức giống nhau — chọn bản **LTS** (Long-Term Support) để ổn định, chọn nguồn phân phối phổ biến để dễ tìm tài liệu.

**Tầng 2 — Định nghĩa:**

| Khái niệm | Giải thích |
|-----------|------------|
| **LTS** | Phiên bản được hỗ trợ dài hạn (8 → 11 → 17 → 21 → **25**). Học và làm production nên dùng LTS. |
| **Temurin (Adoptium)** | Bản JDK mã nguồn mở phổ biến nhất, miễn phí, từ Eclipse Foundation — **khuyên dùng**. |
| **Oracle JDK** | Bản chính thức của Oracle, miễn phí cho mục đích phát triển. |
| **GraalVM** | JDK đặc biệt: biên dịch native image, khởi động siêu nhanh. |

**Tầng 3 — Vì sao nên dùng Temurin 25?** Miễn phí cho mọi mục đích (kể cả thương mại), cập nhật bảo mật dài hạn, và hầu hết tài liệu/tutorial cộng đồng đều viết theo nó. Nên tránh JDK 8/11 cũ — bạn sẽ bỏ lỡ records, sealed, virtual threads, pattern matching — những tính năng khóa học dùng xuyên suốt.

## 2. Cài đặt theo hệ điều hành

### 2.1. Windows

**Cách 1 — Installer (dễ nhất):**

1. Vào [adoptium.net](https://adoptium.net/) → tải **Temurin 25 (LTS)** cho Windows, chọn `.msi`.
2. Trong lúc cài, **bật 2 tùy chọn**: `Add to PATH` và `Set JAVA_HOME`.
3. Mở **PowerShell** kiểm tra:

```powershell
java -version    # openjdk version "25" ...
javac -version   # javac 25
```

**Cách 2 — winget:**

```powershell
winget install EclipseAdoptium.Temurin.25.JDK
```

### 2.2. macOS

```bash
# dung Homebrew (neu chua co: cai tai brew.sh)
brew install --cask temurin@25

# kiem tra
java -version
javac -version
```

macOS tự quản thư mục `/Library/Java/JavaVirtualMachines` — nếu có nhiều JDK, chuyển đổi bằng:

```bash
/usr/libexec/java_home -V          # liet ke cac JDK
export JAVA_HOME=$(/usr/libexec/java_home -v 25)
```

### 2.3. Linux (Ubuntu/Debian)

```bash
# them PPA Adoptium
sudo apt install -y wget apt-transport-https gpg
wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | \
  sudo gpg --dearmor -o /etc/apt/trusted.gpg.d/adoptium.gpg
echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/VERSION_CODENAME/{print$2}' /etc/os-release) main" | \
  sudo tee /etc/apt/sources.list.d/adoptium.list

sudo apt update
sudo apt install temurin-25-jdk
```

### 2.4. Cấu hình biến môi trường thủ công (nếu installer không làm)

**Windows:** Settings → System → Advanced system settings → Environment Variables:
- `JAVA_HOME` = `C:\Program Files\Eclipse Adoptium\jdk-25`
- Thêm `%JAVA_HOME%\bin` vào `Path`.

**macOS/Linux** — thêm vào `~/.zshrc` hoặc `~/.bashrc`:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 25)   # macOS
# export JAVA_HOME=/usr/lib/jvm/temurin-25-jdk     # Linux
export PATH=$JAVA_HOME/bin:$PATH
```

❌ **Pitfall:** Cài nhiều JDK mà `JAVA_HOME` trỏ về bản cũ → IDE dùng bản mới nhưng terminal build dùng bản cũ, lỗi "release version 25 not supported". Luôn kiểm tra bằng:

```bash
echo $JAVA_HOME
java -version
javac -version
```

## 3. Cài IDE

### 3.1. IntelliJ IDEA Community ✅ (khuyên dùng)

1. Tải tại [jetbrains.com/idea](https://www.jetbrains.com/idea/download/) — bản **Community** miễn phí.
2. New Project → chọn JDK 25 → ngôn ngữ Java → Create.
3. Phím tắt cần nhớ: `Shift + Shift` (tìm mọi thứ), `Alt + Enter` (sửa lỗi gợi ý), `Shift + F10` (chạy).

### 3.2. VS Code — nhẹ hơn

1. Cài **Extension Pack for Java** (Microsoft).
2. Mở thư mục project, VS Code tự nhận JDK 25.

### 3.3. Eclipse

Tải từ [eclipse.org](https://www.eclipse.org/downloads/) — nhiều repo Java cổ (như repo này) vẫn dùng cấu trúc Eclipse (.project/.settings), nên Eclipse mở rất tự nhiên.

## 4. Hello World — chương trình đầu tiên

### Cách 1: trong IDE

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Xin chao Java 25!");
    }
}
```

Nhấn nút ▶ Run. Nếu gặp lỗi "class name must match file name" — tên file phải là `Main.java` đúng từng chữ.

### Cách 2: dòng lệnh thuần — hiểu JVM làm gì

```bash
# 1. tao file
mkdir hello && cd hello
# viet file Main.java voi noi dung o tren

# 2. bien dich: javac tao ra Main.class (bytecode)
javac Main.java

# 3. chay: JVM doc bytecode — KHONG goi .class
java Main
# → Xin chao Java 25!
```

### Cách 3: Java 21+ — chạy 1 file không cần compile

```bash
java Main.java    # JVM tu bien dich roi chay ngay
```

### Cách 4: jshell — thử nghiệm tức thì

```bash
jshell
jshell> System.out.println("Xin chao!")
Xin chao!
jshell> /exit
```

## 5. Maven & Gradle — quản lý dự án và thư viện

**Tầng 1 — Trực giác:** Khi dự án cần thư viện ngoài (Jackson, JUnit, Spring...), bạn không tải file `.jar` thủ công. Build tool là **người quản gia**: khai báo cần gì, nó tải về đúng phiên bản, compile, test, đóng gói.

**Tầng 2 — So sánh:**

| Tiêu chí | Maven | Gradle |
|----------|-------|--------|
| File cấu hình | `pom.xml` (XML) | `build.gradle` (Groovy/Kotlin) |
| Phong cách | Quy ước chặt, dễ đọc | Linh hoạt, nhanh, hiện đại |
| Phổ biến | Doanh nghiệp, cũ hơn | Android, Spring Boot mới |
| Khóa học này | ✅ Giới thiệu | ✅ Cũng OK |

**Tầng 3 — Vì sao học sớm?** Từ phần API Frameworks (Spring Boot), mọi project đều sinh ra qua build tool. Nắm pom.xml/gradle cơ bản từ bây giờ giúp bạn không "sốc" khi sang Spring.

### Maven — pom.xml tối thiểu

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>vn.hocjava</groupId>
    <artifactId>hello-java25</artifactId>
    <version>1.0.0</version>

    <properties>
        <maven.compiler.release>25</maven.compiler.release>
    </properties>

    <dependencies>
        <!-- them thu vien tai day, vi du JUnit -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

```bash
mvn compile   # bien dich
mvn test      # chay test
mvn package   # dong goi .jar
```

### Tạo project Spring Boot nhanh (preview cho phần 5)

Truy cập [start.spring.io](https://start.spring.io/) → chọn Maven + Java 25 + dependencies → Download — bạn có bộ khung dự án hoàn chỉnh trong 30 giây.

## 6. Kiểm tra cuối: checklist môi trường

```bash
java -version      # ✅ 25.x
javac -version     # ✅ 25
jshell --version   # ✅ 25
mvn -version       # ✅ (sau khi cai Maven, tuy chon o giai doan dau)
```

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
