# Date trong Java

> Java cung cấp lớp `Date` trong gói `java.util` — lớp này đóng gói thông tin **ngày và thời điểm hiện tại**.

Lưu ý trước khi dùng: `java.util.Date` là lớp cũ (từ Java 1.0), nhiều phương thức của nó đã bị đánh dấu deprecated. Trong dự án thực tế hiện nay, bạn nên ưu tiên `java.time` (LocalDate, LocalDateTime, Instant...). Tuy nhiên, `Date` vẫn gặp rất nhiều trong code cũ, nên bạn cần biết cách dùng cơ bản sau đây.

Ví von: `java.util.Date` như **đồng hồ cơ của ông nội** — hơi lỗi thời so với đồng hồ thông minh (`java.time`), nhưng nhiều nhà vẫn còn treo nó, nên bạn phải biết xem giờ bằng nó.

## Các constructor

Mục này nói về hai cách "lên dây cót" một đối tượng Date: lấy giờ hiện tại, hoặc đặt từ một mốc mili giây cho trước.

`Date( )` — khởi tạo đối tượng với **ngày giờ hiện tại**.

`Date(long millisec)` — nhận tham số là số mili giây đã trôi qua kể từ 00:00 ngày 01/01/1970 (GMT).

Con số 01/01/1970 chính là "mốc xuất phát" của thế giới máy tính (epoch) — giống mọi cuộc đua đều tính từ vạch xuất phát, Java tính thời gian từ mốc đó bằng số mili giây đã chạy được.

> 📄 **Code ví dụ:** [`examples/01-cac-constructor.java`](examples/01-cac-constructor.java)

> **Tóm gọn 1 câu:** `Date` có 2 cửa vào: cửa "bây giờ" (`Date()`) và cửa "đếm từ 1970" (`Date(long)`).

## Các phương thức của lớp Date

Mục này là bảng "túi chép sơ đồ đồng hồ": liệt kê các thao tác so sánh, chuyển đổi, đọc-đặt thời gian của Date. Dùng như tra cứu, không cần học thuộc.

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 1 | `boolean after(Date when)` | Kiểm tra thời điểm này có **sau** thời điểm chỉ định không |
| 2 | `boolean before(Date when)` | Kiểm tra thời điểm này có **trước** thời điểm chỉ định không |
| 3 | `Object clone()` | Trả về một bản sao của đối tượng này |
| 4 | `int compareTo(Date anotherDate)` | So sánh hai đối tượng Date theo thứ tự |
| 5 | `boolean equals(Object obj)` | So sánh hai ngày có bằng nhau không |
| 6 | `static Date from(Instant instant)` | Tạo đối tượng `Date` từ một đối tượng `Instant` |
| 7 | `long getTime()` | Trả về số mili giây kể từ 01/01/1970 00:00:00 GMT mà đối tượng này biểu diễn |
| 8 | `int hashCode()` | Trả về mã băm (hash code) của đối tượng |
| 9 | `void setTime(long time)` | Đặt thời điểm của đối tượng = `time` mili giây sau 01/01/1970 00:00:00 GMT |
| 10 | `Instant toInstant()` | Chuyển đối tượng `Date` này thành `Instant` |
| 11 | `String toString()` | Chuyển đối tượng `Date` này thành chuỗi |

> **Cách đọc bảng này:** quét theo cột Mô tả từ trên xuống — nhóm đầu (after, before, compareTo, equals) để **so sánh** hai mốc thời gian, nhóm giữa (getTime/setTime/from/toInstant) để **chuyển đổi** qua lại với mili giây và Instant, nhóm cuối là các method tiện ích. Cần thao tác gì với ngày thì tra đúng dòng đó.

> **Tóm gọn 1 câu:** Bảng method của Date xoay quanh 3 việc: so sánh thời điểm, đọc/đặt giờ theo mili giây, và chuyển đổi qua lại với `Instant`.

## Các ký tự định dạng của SimpleDateFormat

Mục này là "từ điển mẫu" khi bạn muốn in ngày giờ theo kiểu riêng — mỗi ký tự đứng trong mẫu là một mảnh ghép của chuỗi kết quả.

> **Cách đọc bảng này:** cột Ký tự là chữ bạn đặt vào chuỗi pattern (ví dụ `"dd/MM/yyyy"`), cột Ví dụ cho thấy nó in ra gì. Chú ý các cặp giống nhau nhưng khác hoa-thường (`m` phút vs `M` tháng, `d` ngày vs `D` ngày trong năm) — đây là chỗ hay gõ nhầm nhất.

| Ký tự | Ý nghĩa | Ví dụ |
|-------|---------|-------|
| `G` | Kỷ nguyên (Era designator) | AD |
| `y` | Năm, 4 chữ số | 2001 |
| `M` | Tháng trong năm | July hoặc 07 |
| `d` | Ngày trong tháng | 10 |
| `h` | Giờ theo A.M./P.M. (1~12) | 12 |
| `H` | Giờ trong ngày (0~23) | 22 |
| `m` | Phút trong giờ | 30 |
| `s` | Giây trong phút | 55 |
| `S` | Mili giây | 234 |
| `E` | Ngày trong tuần | Tuesday |
| `D` | Ngày trong năm | 360 |
| `F` | Ngày trong tuần của tháng | 2 (thứ tư thứ hai của tháng 7) |
| `w` | Tuần trong năm | 40 |
| `W` | Tuần trong tháng | 1 |
| `a` | Ký hiệu A.M./P.M. | PM |
| `k` | Giờ trong ngày (1~24) | 24 |
| `K` | Giờ theo A.M./P.M. (0~11) | 10 |
| `z` | Múi giờ | Eastern Standard Time |
| `'` | Escape cho văn bản | Delimiter |
| `"` | Dấu nháy đơn | ` |

Ví von: các ký tự này giống **khuôn bánh** — trộn đúng ký tự vào khuôn (`"dd/MM/yyyy"`), thì dữ liệu thô bị "đổ" ra thành hình dáng bạn muốn. Đổi ký tự trong khuôn là đổi hình bánh.

## Các ký tự chuyển đổi ngày và giờ (printf/Formatter)

Mục này giới thiệu bộ ký tự khác, dùng với `printf`/`Formatter` thay cho SimpleDateFormat — cùng mục đích in ngày giờ, khác "khuôn".

> **Cách đọc bảng này:** đọc tương tự bảng trên — cột Ký tự dùng trong chuỗi format của printf (ví dụ `%tY-%tm-%td`), cột Ví dụ là kết quả in. Nếu thấy choáng vì dài: 90% trường hợp thực tế chỉ cần `F` (ngày ISO), `T` (giờ 24 số) và `D` (kiểu Mỹ); phần còn lại để dành khi tra.

| Ký tự | Ý nghĩa | Ví dụ |
|-------|---------|-------|
| `c` | Ngày và giờ đầy đủ | Mon May 04 09:51:52 CDT 2009 |
| `F` | Ngày chuẩn ISO 8601 | 2004-02-09 |
| `D` | Ngày kiểu Mỹ (tháng/ngày/năm) | 02/09/2004 |
| `T` | Giờ 24 số | 18:05:19 |
| `r` | Giờ 12 số | 06:05:19 pm |
| `R` | Giờ 24 số, không giây | 18:05 |
| `Y` | Năm 4 chữ số (có số 0 đệm) | 2004 |
| `y` | Hai chữ số cuối của năm | 04 |
| `C` | Hai chữ số đầu của năm | 20 |
| `B` | Tên tháng đầy đủ | February |
| `b` | Tên tháng viết tắt | Feb |
| `m` | Tháng 2 chữ số (có số 0 đệm) | 02 |
| `d` | Ngày 2 chữ số (có số 0 đệm) | 03 |
| `e` | Ngày 2 chữ số (không số 0 đệm) | 9 |
| `A` | Tên ngày trong tuần đầy đủ | Monday |
| `a` | Tên ngày trong tuần viết tắt | Mon |
| `j` | Ngày trong năm, 3 chữ số | 069 |
| `H` | Giờ 2 chữ số, 00–23 | 18 |
| `k` | Giờ 2 chữ số, 0–23 | 18 |
| `I` | Giờ 2 chữ số, 01–12 | 06 |
| `l` | Giờ 2 chữ số, 1–12 | 6 |
| `M` | Phút 2 chữ số (có số 0 đệm) | 05 |
| `S` | Giây 2 chữ số (có số 0 đệm) | 19 |
| `L` | Mili giây 3 chữ số | 047 |
| `N` | Nanosecond 9 chữ số | 047000000 |
| `P` | Ký hiệu sáng/chiều viết hoa | PM |
| `p` | Ký hiệu sáng/chiều viết thường | pm |
| `z` | Chênh lệch múi giờ theo RFC 822 | -0800 |
| `Z` | Múi giờ | PST |
| `s` | Số giây kể từ 1970-01-01 00:00:00 GMT | 1078884319 |
| `Q` | Số mili giây kể từ 1970-01-01 00:00:00 GMT | 1078884319047 |

> **Tóm gọn 1 câu:** Hai bộ ký tự định dạng là hai "khuôn in" ngày giờ — SimpleDateFormat một khuôn, printf một khuôn, nhớ `M`/`m` khác nhau là tránh được nửa số lỗi.
