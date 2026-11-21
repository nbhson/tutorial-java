# Input in Java

Chương trình của bạn muốn "nghe" người dùng gõ gì vào bàn phím thì cần một người trực nhật nghe — trong Java, người đó là lớp `Scanner`. Mục này nói về cách mời Scanner vào làm việc và các "tai nghe" (method) nó mang theo cho từng loại dữ liệu.

To take input from the user in Java, the `Scanner` class is used. The `Scanner` class a built-in class of `java.util package`.

> Java Scanner class provides many built-in methods to take different types of user inputs from the users.

Ví von: `Scanner` như **nhân viên thu ngân** — khách (người dùng) gõ gì, nó đọc và "quét" đúng theo loại hóa đơn bạn yêu cầu: muốn số nguyên thì gọi `nextInt()`, muốn dòng chữ thì gọi `nextLine()`. Đúng thu ngân, đúng loại vé thì tiền mới về đúng quầy.

Dưới đây là danh sách các method đọc input theo từng loại — đi từ chuỗi, số lớn, rồi đến các kiểu nguyên thủy quen thuộc.

**1	String next()**
This method finds and returns the next complete token from this scanner.

**2	BigDecimal nextBigDecimal()**
This method scans the next token of the input as a BigDecimal.

**3	BigInteger nextBigInteger()**
This method Scans the next token of the input as a BigInteger.

**4	boolean nextBoolean()**
This method scans the next token of the input into a boolean value and returns that value.

**5	byte nextByte()**
This method scans the next token of the input as a byte.

**6	double nextDouble()**
This method scans the next token of the input as a double.

**7	float nextFloat()**
This method scans the next token of the input as a float.

**8	int nextInt()**
This method scans the next token of the input as an int.

**9	String nextLine()**
This method advances this scanner past the current line and returns the input that was skipped.

**10	long nextLong()**
This method scans the next token of the input as a long.

**11	short nextShort()**
This method scans the next token of the input as a short.

Cách đọc nhanh: tên method tự khai báo loại dữ liệu nó trả về — `nextInt()` đọc int, `nextDouble()` đọc double; riêng hai "cặp đôi dễ nhầm" là `next()` (lấy **một từ**, dừng ở khoảng trắng) và `nextLine()` (lấy **cả dòng**, kể cả khoảng trắng). Hình dung: `next()` là cắt từng miếng bánh, `nextLine()` là lấy nguyên ổ.

> **Tóm gọn 1 câu:** `Scanner` là thu ngân đọc input của người dùng — chọn đúng `nextInt`/`nextLine`/... theo loại dữ liệu, và nhớ `next()` lấy một từ còn `nextLine()` lấy cả dòng.
