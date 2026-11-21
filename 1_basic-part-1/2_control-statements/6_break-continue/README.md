# Loop Control

Vòng lặp đang quay đều thì cần những "công tắc khẩn cấp": bỏ cuộc hẳn, hay bỏ qua vòng này mà chạy tiếp. Bài này nói về `break` và `continue` — hai công tắc đó.

## Java Loops

Java programming language provides the following types of loops to handle the looping requirements:

`1	while loop`
Repeats a statement or group of statements while a given condition is true. It tests the condition before executing the loop body.

`2	for loop`
Execute a sequence of statements multiple times and abbreviates the code that manages the loop variable.

`3	do...while loop`
Like a while statement, except that it tests the condition at the end of the loop body.

`4	Enhanced for loop`
As of Java 5, the enhanced for loop was introduced. This is mainly used to traverse collection of elements including arrays.

Bốn vòng lặp như bốn kiểu chạy bộ: **while** chạy theo cảm giác mỏi (kiểm tra trước mỗi vòng), **for** chạy theo kế hoạch biết trước số vòng, **do...while** chạy thử trước rồi mới hỏi, **enhanced for** chạy theo lộ trình có sẵn từng điểm mốc.

## Loop Control Statements

`1	break statement`
Terminates the loop or switch statement and transfers execution to the statement immediately following the loop or switch.

`2	continue statement`
Causes the loop to skip the remainder of its body and immediately retest its condition prior to reiterating.

Hình dung `break` và `continue` qua **trạm soát vé rạp phim**: `continue` là bảo vệ nói "vé bạn hết hạn, mời sang hàng sau" — bỏ lượt này, lượt kế tiếp vẫn được soát; `break` là bảo vệ thả rèm — "rạp đóng cửa, cả hàng giải tán", không ai được soát nữa, dòng chạy nhảy thẳng tới câu lệnh sau vòng lặp.

## Java Break

> A while loop statement in Java programming language repeatedly executes a code block as long as a given condition is true.

Phần này đi sâu vào `break` — công tắc "thoát ngay lập tức" khỏi vòng lặp hoặc switch.

### break Statement

- When the break statement is encountered inside a loop, the loop is immediately terminated and the program control resumes at the next statement following the loop.
- It can be used to terminate a case in the switch statement

Hai việc `break` làm được: **bỏ cuộc giữa chừng** trong vòng lặp (đã tìm thấy thứ cần tìm — cứ tiếp tục lặp là phí thời gian), và **chốt một case** trong switch để không rơi xuống case bên dưới.

Ví von: `break` trong vòng lặp tìm kiếm giống như **dừng lật bài ngay khi tìm ra lá Át** — lật tiếp các lá sau chỉ tốn công vô ích.

### Flow Diagram

![alt text](image.png)

## Java Continue

- The continue statement can be used in any of the loop control structures. It causes the loop to immediately jump to the next iteration of the loop.

`continue` nhẹ tay hơn `break`: nó không bỏ cuộc, chỉ **bỏ dở vòng hiện tại** — phần code phía sau không chạy nữa, dòng chạy quay ngay về điều kiện để xét vòng kế tiếp. Ví von: trong ca trực rửa chén, gặp cái bát không rán được là để sang một bên và cầm cái tiếp theo — không bỏ cả bồn rửa.

> 📄 **Code ví dụ:** [`examples/01-java-continue.sh`](examples/01-java-continue.sh)

### Flow Diagram

![alt text](image-1.png)

> **Tóm gọn 1 câu:** `break` là thả rèm bỏ cuộc — thoát hẳn vòng lặp; `continue` là bỏ lượt — nhảy ngay sang vòng kế tiếp mà vòng lặp vẫn còn quay.
