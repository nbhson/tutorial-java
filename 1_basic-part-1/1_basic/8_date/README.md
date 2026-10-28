# Date trong Java

> Java cung cấp lớp `Date` trong gói `java.util` — lớp này đóng gói thông tin **ngày và thời điểm hiện tại**.

Lưu ý trước khi dùng: `java.util.Date` là lớp cũ (từ Java 1.0), nhiều phương thức của nó đã bị đánh dấu deprecated. Trong dự án thực tế hiện nay, bạn nên ưu tiên `java.time` (LocalDate, LocalDateTime, Instant...). Tuy nhiên, `Date` vẫn gặp rất nhiều trong code cũ, nên bạn cần biết cách dùng cơ bản sau đây.

## Các constructor

`Date( )` — khởi tạo đối tượng với **ngày giờ hiện tại**.

`Date(long millisec)` — nhận tham số là số mili giây đã trôi qua kể từ 00:00 ngày 01/01/1970 (GMT).

```java
import java.util.Date;

public class DateDemo {
   public static void main(String args[]) {
      Date date = new Date();          // thời điểm hiện tại
      System.out.println(date.toString());

      Date d2 = new Date(0L);          // 01/01/1970 00:00:00 GMT
      System.out.println(d2);
   }
}
```

## Các phương thức của lớp Date

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

## Các ký tự định dạng của SimpleDateFormat

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

## Các ký tự chuyển đổi ngày và giờ (printf/Formatter)

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
