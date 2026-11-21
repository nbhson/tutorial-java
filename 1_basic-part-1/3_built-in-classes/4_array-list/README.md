# Array List

Mảng thường có một nhược điểm chí mạng: **lúc tạo ra là chốt luôn kích thước**. Vậy nếu chưa biết trước sẽ có bao nhiêu phần tử? `ArrayList` là câu trả lời — mục này nói về "chiếc vali kéo dãn được" của Java.

## Introduce

Mục này trả lời câu hỏi ArrayList là gì và nó giải quyết nhược điểm nào của mảng thường — đọc để hiểu vì sao nó là "cây nhà lá vườn" trong code Java thực tế.

The Java ArrayList class provides resizable-array and implements the List interface. Following are the important points about ArrayList −

- It implements all optional list operations and it also permits all elements, includes null.

- It provides methods to manipulate the size of the array that is used internally to store the list.

> Standard Java arrays are of a fixed length. After arrays are created, they cannot grow or shrink, which means that you must know in advance how many elements an array will hold.

> Array lists are created with an initial size. When this size is exceeded, the collection is automatically enlarged. When objects are removed, the array may be shrunk.

Ví von: mảng thường là **kệ sách đóng đinh cố định** — làm 10 ô thì mãi mãi 10 ô, thừa thì phí, thiếu thì chịu. ArrayList là **kệ thông minh tự giãn**: đồ chất đầy gần hết thì tự chế thêm ô, bớt đồ thì thu gọn lại.

> 📄 **Code ví dụ:** [`examples/01-introduce.java`](examples/01-introduce.java)

> **Tóm gọn 1 câu:** ArrayList là mảng "tự giãn" — bạn không cần biết trước số phần tử, nó tự lo việc nở ra và thu lại.

## Class constructors

Mục này nói về 3 cách "đặt vali": để trống, đặt theo sức chứa ban đầu, hoặc chép sẵn từ một bộ sưu tập có trước.

1	ArrayList()
This constructor is used to create an empty list with an initial capacity sufficient to hold 10 elements.

2	ArrayList(Collection<? extends E> c)
This constructor is used to create a list containing the elements of the specified collection.

3   ArrayList(int initialCapacity)
This constructor is used to create an empty list with an initial capacity.

Nói theo cách đời thường: không tham số là **mua vali size mặc định** (chở được ~10 món); có `int initialCapacity` là **đặt vali theo cỡ riêng**; có `Collection` là **kế thừa đồ đạc từ vali cũ** — mở vali mới ra là đồ đã nằm sẵn bên trong.

## List of method

Mục này là bảng túi chuyền — các thao tác thêm, sửa, xóa, tìm trên ArrayList. Dùng như tra cứu khi code.

| Method | Làm gì |
|--------|--------|
| `add(value)` | thêm phần tử vào arrList |
| `set(index)` | update phần tử (replace value tren index do) |
| `get(index)` | lấy phần tử |
| `remove(index)` | xóa phần tử của list |
| `clear()` | xóa tất cả các phần tử ra khỏi arrList |
| `contains(value)` | kiểm tra phần tử có tồn tại trong list không |
| `hasNext()` | kiểm tra còn phần từ nào ở phía sau không |
| `addAll(arrList)` | thêm tất cả phần tử của một arrList vào một arrList khác |
| `retainAll(arrList)` | xóa những phần tử của listA không có trong listB |
| `removeAll` | xóa tất cả những phần tử của listA ra khỏi listB |
| `indexOf` | tìm phần tử đầu tiên xuất hiện trong list |
| `lastIndexOf` | tìm phần tử xuất hiện cuối cùng trong list |
| `toArray` | chuyển list thành mảng |

> **Cách đọc bảng này:** nhóm từ trên xuống — nhóm đầu (add/set/get/remove/clear) là **thao tác cơ bản hằng ngày**; nhóm giữa (contains/hasNext/addAll/retainAll/removeAll) là **so sánh và ghép nối** giữa các list; nhóm cuối (indexOf/lastIndexOf/toArray) là **tìm kiếm và chuyển đổi**. Đọc cột "Làm gì" là đủ nhớ khi cần.

## Sự khác nhau giữa Array và ArrayList

Mục này so sánh trực diện hai "nhân vật" của bài — đọc để biết chọn ai cho tình huống nào, thay vì dùng ArrayList bừa mọi nơi.

- Kích thước cố định: Mảng có kích thước cố định khi được khai báo, trong khi ArrayList có thể thay đổi kích thước của nó khi cần thiết.
- Kiểu dữ liệu: Mảng có thể chứa các phần tử cùng một kiểu dữ liệu cố định, trong khi ArrayList có thể chứa các phần tử của bất kỳ kiểu dữ liệu nào (với sự hỗ trợ của Generics).
- Thao tác với phần tử: ArrayList cung cấp các phương thức tiện ích để thêm, xóa, sắp xếp, tìm kiếm phần tử một cách dễ dàng hơn so với mảng.
- Tính linh hoạt: ArrayList linh hoạt hơn trong việc thêm/xóa phần tử mà không cần phải lo lắng về việc quản lý bộ nhớ như mảng.

Quy tắc chọn nhanh: biết chắc số lượng, cần tốc độ tối đa → **mảng**; số lượng thất thường, cần tiện ích → **ArrayList**. Giống như chọn hộp cơm dịp đi piknic: biết trước mấy người thì hộp chia ngăn, chưa biết thì mang hộp chứa được nhiều cỡ.

> **Tóm gọn 1 câu:** Array là kệ đóng đinh cố định, ArrayList là kệ tự giãn kèm bộ tiện ích — đổi lại một chút "chi phí" thì mua được sự linh hoạt.
