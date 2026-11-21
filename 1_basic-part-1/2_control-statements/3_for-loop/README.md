# Loop Control

Lặp (loop) là cách bảo máy tính **làm lại một việc nhiều lần** mà bạn không phải chép code. Mục này nói về các loại vòng lặp Java, rồi đi sâu vào `for` — vòng lặp "biết trước đi mấy vòng".

## Java Loops

Java programming language provides the following types of loops to handle the looping requirements:

Hãy hình dung 4 loại vòng lặp như **4 kiểu chạy bộ**:

`1	while loop`
Repeats a statement or group of statements while a given condition is true. It tests the condition before executing the loop body.

`2	for loop`
Execute a sequence of statements multiple times and abbreviates the code that manages the loop variable.

`3	do...while loop`
Like a while statement, except that it tests the condition at the end of the loop body.

`4	Enhanced for loop`
As of Java 5, the enhanced for loop was introduced. This is mainly used to traverse collection of elements including arrays.

- **while** — chạy theo "cảm giác": kiểm tra mỏi chưa, mỏi thì dừng (biết trước điều kiện, không biết trước số vòng).
- **for** — chạy theo "kế hoạch": biết trước chạy đúng 10 km, đồng hồ đếm từng vòng.
- **do...while** — "chạy thử trước rồi mới hỏi": làm ít nhất một lần rồi mới xét điều kiện.
- **enhanced for** — chạy "theo lộ trình có sẵn": đi qua từng điểm mốc (phần tử) của danh sách.

## Loop Control Statements

`1	break statement`
Terminates the loop or switch statement and transfers execution to the statement immediately following the loop or switch.

`2	continue statement`
Causes the loop to skip the remainder of its body and immediately retest its condition prior to reiterating.

Hai "công tắc khẩn cấp": `break` là **chuông báo bỏ cuộc** — thoát hẳn vòng lặp; `continue` là **bỏ qua một vòng** — nhảy ngay sang vòng kế tiếp. (Chi tiết ở bài [6_break-continue](../6_break-continue/README.md).)

## Java for Loop

Phần này đi vào vòng lặp chính của bài: `for` — gói cả 3 yếu tố (khởi tạo, điều kiện, bước nhảy) vào một dòng, như đồng hồ chạy bộ gắn sẵn trên tay.

### Flow Diagram

![alt text](image.png)

> 📄 **Code ví dụ:** [`examples/01-flow-diagram.java`](examples/01-flow-diagram.java)

Sơ đồ trên là "sơ đồ chân" của vòng lặp: **khởi tạo** chạy một lần duy nhất → **điều kiện** kiểm tra trước mỗi vòng → **thân lặp** → **bước nhảy** → quay lại kiểm tra. Chừng nào điều kiện còn đúng, vòng quay còn tiếp.

> **Tóm gọn 1 câu:** `for` gói "khởi tạo — điều kiện — bước nhảy" vào một dòng — vòng lặp của người biết trước đi bao nhiêu bước.

### Java Infinite for Loop

> 📄 **Code ví dụ:** [`examples/02-java-infinite-for-loop.java`](examples/02-java-infinite-for-loop.java)

Vòng lặp vô hạn như **đồng hồ kẹt kim giây** — quay mãi không dừng, chương trình "đứng hình". Hữu ích khi cố ý chờ sự kiện, nguy hiểm khi quên điều kiện dừng.

> **Tóm gọn 1 câu:** Vòng lặp vô hạn là xe đạp không phanh — chủ ý dùng thì hay, lỡ tay quên điều kiện dừng thì chương trình treo.

## Java for-each Loop

Phần này giới thiệu phiên bản "lười thông minh" của for: không cần đếm chỉ số, cứ **đi qua từng phần tử** một lượt.

### Syntax

> 📄 **Code ví dụ:** [`examples/03-syntax.java`](examples/03-syntax.java)

Ví von: for thường là người **đếm số nhà trên phố** (0, 1, 2...), for-each là người **gõ cửa từng nhà** — không cần biết phố có bao nhiêu nhà, cứ gặp nhà thì gõ.

> **Tóm gọn 1 câu:** for-each là "gõ cửa từng nhà" — bỏ qua chỉ số, chỉ quan tâm từng phần tử trong danh sách.
