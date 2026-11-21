# Collections Framework (Bộ sưu tập trong Java)

> Trong thực tế, bạn gần như **không bao giờ** làm việc với một phần dữ liệu đơn lẻ. Bạn có một danh sách học sinh, một giỏ hàng, một bảng tra mã tỉnh → tên tỉnh. **Collections Framework** là bộ công cụ chuẩn của Java để lưu trữ và xử lý dữ liệu dạng "nhiều phần tử" — và là chủ đề bị hỏi nhiều nhất trong phỏng vấn.

## 1. Collections là gì? Vì sao cần nó?

Mục này mở đường cho cả bài: vì sao mảng thường không đủ dùng, Collections Framework gồm những gì, và vì sao nó bị hỏi nhiều trong phỏng vấn.

**Tầng 1 — Trực giác:** Mảng (`int[]`) có kích thước **cố định**. Khai báo mảng 10 phần tử rồi cần phần tử thứ 11, bạn phải tạo mảng mới và copy toàn bộ dữ liệu — như mua hũ đựng gạo đúng 10 ký, muốn đổ thêm ký thứ 11 thì phải mua hũ mới và đổ gạo qua. Collections giải quyết vấn đề đó: cấu trúc dữ liệu **tự giãn nở**, kèm sẵn hàng trăm phương thức hữu ích (thêm, xóa, tìm kiếm, sắp xếp...).

**Tầng 2 — Định nghĩa:** Collections Framework là một kiến trúc thống nhất gồm 3 phần:

- **Interfaces** (giao diện): quy định *hành vi* — `Collection`, `List`, `Set`, `Map`, `Queue`.
- **Implementations** (lớp cài đặt): quy định *cách lưu trữ* — `ArrayList`, `LinkedList`, `HashSet`, `TreeSet`, `HashMap`, `TreeMap`...
- **Algorithms** (thuật toán): phương thức tĩnh trong class `Collections` — `sort`, `shuffle`, `reverse`...

```
Iterable
   └── Collection
         ├── List  (có thứ tự, cho phép trùng lặp)
         │     ├── ArrayList   ← mảng động, truy cập theo index nhanh
         │     ├── LinkedList  ← danh sách liên kết đôi, chèn/xóa nhanh
         │     └── Vector      ← legacy, synchronized (hầu như không dùng nữa)
         ├── Set   (KHÔNG cho phép trùng lặp)
         │     ├── HashSet       ← băm, nhanh nhất, không đảm bảo thứ tự
         │     ├── LinkedHashSet ← giữ thứ tự chèn
         │     └── TreeSet       ← tự sắp xếp theo comparator
         └── Queue (hàng đợi FIFO)
               ├── ArrayDeque
               └── PriorityQueue

Map (đứng riêng, KHÔNG kế thừa Collection)
  ├── HashMap       ← cặp key → value, băm
  ├── LinkedHashMap ← giữ thứ tự chèn
  └── TreeMap       ← sắp xếp theo key
```

**Tầng 3 — Vì sao tách thành List/Set/Map?** Vì mỗi bài toán cần hành vi khác nhau:

| Nhu cầu | Nên dùng | Ví dụ thực tế |
|---------|----------|---------------|
| Giữ thứ tự, cho trùng lặp | `List` | Danh sách bài hát theo thứ tự phát |
| Loại bỏ trùng lặp | `Set` | Danh sách email đã đăng ký |
| Tra cứu key → value | `Map` | Mã nhân viên → thông tin nhân viên |
| Xử lý theo FIFO/LIFO | `Queue`/`Deque` | Hàng đợi đơn hàng, undo/redo |

> **Cách đọc bảng này:** cột **Nhu cầu** là bài toán của bạn, đọc ngang sang phải để lấy "đáp án" — chọn cấu trúc nào và ví dụ đời thực tương ứng. Đây là bảng định hướng chọn, không phải bảng học thuộc.

❌ **Pitfall:** Dùng `ArrayList` cho mọi thứ vì "quen tay". Khi cần kiểm tra "email này đã tồn tại chưa?" trên 1 triệu phần tử, `ArrayList.contains()` mất O(n) còn `HashSet.contains()` chỉ mất O(1).

✅ **Đúng:** Hãy chọn cấu trúc theo **thao tác dominant** của bài toán: đọc theo index nhiều → `ArrayList`; tra cứu nhiều → `Map`/`Set`.

> **Tóm gọn 1 câu:** Collections là "hũ gạo tự giãn" thay mảng cứng nhắc — và chọn hũ nào (List/Set/Map/Queue) là tùy việc bạn tra cứu, lọc trùng hay xếp hàng.

## 2. Generics — bắt buộc phải nắm trước

Mục này giải thích chữ `<...>` bạn sẽ thấy khắp nơi trong Collections: generics là "khuôn" ràng buộc kiểu phần tử ngay lúc biên dịch.

Từ Java 5, Collections dùng **Generics** để ràng buộc kiểu phần tử lúc **biên dịch**:

> 📄 **Code ví dụ:** [`examples/01-2-generics-bat-buoc-phai-nam-truoc.java`](examples/01-2-generics-bat-buoc-phai-nam-truoc.java)

❌ **Pitfall:** Dùng raw type `List` (không có `<>`). Từ Java 7 trở đi hãy dùng **diamond operator**: `new ArrayList<>()`.

**Vì sao?** Generics giúp phần lớn lỗi sai kiểu bị phát hiện lúc compile thay vì lúc production nổ `ClassCastException` lúc 2h sáng.

> **Tóm gọn 1 câu:** Generics là "khuôn cam" cho collections: nhét trái táo vào khuôn cam là compiler chặn ngay, khỏi đợi khuôn nổ giữa đêm.

## 3. List — danh sách có thứ tự

Mục này nói về cấu trúc được dùng nhiều nhất: List — giữ đúng thứ tự bạn thêm vào và cho phép phần tử trùng nhau; trọng tâm là chọn giữa `ArrayList` và `LinkedList`.

### 3.1. Các phương thức quan trọng

> 📄 **Code ví dụ:** [`examples/02-3-1-cac-phuong-thuc-quan-trong.java`](examples/02-3-1-cac-phuong-thuc-quan-trong.java)

### 3.2. ArrayList vs LinkedList — câu hỏi phỏng vấn kinh điển

**Tầng 1 — Trực giác:**
- `ArrayList` = một **mảng động**: các phần tử nằm **liên tiếp** trong bộ nhớ.
- `LinkedList` = **chuỗi toa tàu**: mỗi phần tử (node) giữ tham chiếu tới node trước và node sau.

**Tầng 2 — So sánh độ phức tạp:**

| Thao tác | ArrayList | LinkedList |
|----------|-----------|------------|
| `get(index)` | **O(1)** ✅ | O(n) — phải đi từ đầu/cuối |
| `add(e)` (cuối) | O(1) trung bình | O(1) |
| `add(index, e)` (giữa) | O(n) — phải dồn chỗ | O(n) tìm node + O(1) nối |
| `remove(index)` | O(n) — dồn chỗ | O(n) tìm node + O(1) gỡ |
| Bộ nhớ | Chặt chẽ | Mỗi node tốn thêm 2 con trỏ |

> **Cách đọc bảng này:** đọc ngang từng dòng là so độ nhanh giữa hai bên cho cùng một thao tác — in đậm O(1) ✅ là điểm thắng. Kết luận nằm ở dòng `get(index)` và dòng cuối (bộ nhớ): ArrayList thắng ở đọc, LinkedList tốn thêm bộ nhớ mà chẳng nhanh hơn ở thao tác phổ biến.

**Tầng 3 — Vì sao ArrayList thắng gần như mọi trường hợp?**

Lý do là **cache locality**: CPU đọc bộ nhớ theo từng block. Với `ArrayList`, các phần tử nằm cạnh nhau nên khi duyệt, dữ liệu đã có sẵn trong CPU cache. Với `LinkedList`, mỗi node có thể nằm ở một chỗ khác nhau trong heap → mỗi lần "nhảy" qua con trỏ là một lần cache miss.

> 📄 **Code ví dụ:** [`examples/03-3-2-arraylist-vs-linkedlist-cau-hoi-phong.java`](examples/03-3-2-arraylist-vs-linkedlist-cau-hoi-phong.java)

✅ **Kết luận thực tế:** mặc định dùng `ArrayList`. Chỉ cân nhắc `LinkedList` khi làm queue/deque với thao tác chèn/xóa ở **đầu danh sách** liên tục — và ngay cả khi đó, `ArrayDeque` vẫn thường nhanh hơn.

❌ **Pitfall:** `ArrayList` chưa chỉ định dung lượng ban đầu mà thêm 1 triệu phần tử sẽ bị **resize nhiều lần** (mỗi lần copy toàn bộ mảng). Nếu biết trước số lượng, hãy set: `new ArrayList<>(1_000_000)`.

### 3.3. ArrayList vận hành bên trong như thế nào?

> 📄 **Code ví dụ:** [`examples/04-3-3-arraylist-van-hanh-ben-trong-nhu.java`](examples/04-3-3-arraylist-van-hanh-ben-trong-nhu.java)

Đó là lý do `add()` ở cuối là **O(1) trung bình** (amortized) dù thỉnh thoảng tốn O(n) khi resize.

> **Tóm gọn 1 câu:** List giữ thứ tự và cho trùng lặp — mặc định cứ dùng `ArrayList` (mảng động + cache-friendly), `LinkedList` chỉ xuất hiện trong câu hỏi phỏng vấn nhiều hơn trong code thật.

## 4. Set — không cho phép trùng lặp

Mục này nói về cấu trúc "không nhận đồ trùng": Set từ chối phần tử đã có, và để hiểu nó từ chối thế nào bạn phải biết chút về băm (hash).

### 4.1. HashSet

> 📄 **Code ví dụ:** [`examples/05-4-1-hashset.java`](examples/05-4-1-hashset.java)

### 4.2. HashSet hoạt động bên trong — băm là gì?

**Tầng 1:** `HashSet` thực chất là một `HashMap` ngụy trang: phần tử được lưu làm **key**, còn value là một object placeholder chung.

**Tầng 2 — Quy trình `add(x)`:**

1. Gọi `x.hashCode()` → một số int.
2. Băm số đó để tính **bucket** (ô) trong mảng nội bộ: `index = hash & (capacity - 1)`.
3. Bucket trống → chèn vào, xong.
4. Bucket có sẵn phần tử → so sánh bằng `equals()`:
   - bằng nhau → **từ chối** (đây chính là cơ chế chống trùng lặp);
   - khác nhau → hai phần tử "va chạm" (collision), cùng bucket thành một danh sách liên kết; từ Java 8, khi bucket dài quá 8 node nó biến thành **cây đỏ-đen** (O(log n)).

**Tầng 3 — Vì sao bắt buộc phải override `hashCode()` cùng `equals()`?**

Quy ước của Java: hai object bằng nhau theo `equals()` **phải** có cùng `hashCode()`. Nếu bạn chỉ override `equals()`, hai object "bằng nhau" có thể rơi vào hai bucket khác nhau → `contains()` trả về false dù phần tử đã được thêm.

> 📄 **Code ví dụ:** [`examples/06-4-2-hashset-hoat-ong-ben-trong-bam.java`](examples/06-4-2-hashset-hoat-ong-ben-trong-bam.java)

❌ **Pitfall:** Mutate một object **sau khi** đã đưa vào `HashSet` (thay đổi field tham gia vào hashCode). Object vẫn nằm trong Set nhưng bạn không thể tìm ra nó nữa vì hash đã đổi.

✅ **Fix:** Dùng object **immutable** trong Set/Map (đây cũng là lý do `String` được thiết kế immutable).

> **Tóm gọn 1 câu:** Set từ chối trùng nhờ hash + equals — override `equals()` mà quên `hashCode()` là Set "nhận người mà không nhớ mặt".

### 4.3. TreeSet — có thứ tự

`TreeSet` dựa trên **cây đỏ-đen**, các phần tử luôn được duyệt theo thứ tự sắp xếp (O(log n) mỗi thao tác) — như giá sách đã xếp sẵn theo alphabet. Dùng khi bạn cần dữ liệu **luôn có thứ tự** hoặc cần các truy vấn khoảng (`first`, `last`, `ceiling`, `floor`).

> 📄 **Code ví dụ:** [`examples/07-4-3-treeset-co-thu-tu.java`](examples/07-4-3-treeset-co-thu-tu.java)

> **Tóm gọn 1 câu:** HashSet nhanh nhưng lộn xộn, TreeSet chậm hơn một chút (O(log n)) nhưng trả về dữ liệu đã xếp sẵn — đổi tốc độ lấy trật tự.

## 5. Map — cặp key → value

Mục này nói về cấu trúc "cuốn từ điển": tra từ (key) là ra nghĩa (value) ngay, không cần lật từng trang.

### 5.1. HashMap cơ bản

> 📄 **Code ví dụ:** [`examples/08-5-1-hashmap-co-ban.java`](examples/08-5-1-hashmap-co-ban.java)

❌ **Pitfall:** `get()` trả về `null` cho key không tồn tại → nếu value là primitive được autobox (`Map<String, Integer>`), unboxing `null` thành `int` gây **NullPointerException**:

> 📄 **Code ví dụ:** [`examples/09-5-1-hashmap-co-ban.java`](examples/09-5-1-hashmap-co-ban.java)

### 5.2. HashMap hoạt động bên trong (đề phỏng vấn số 1)

**Tầng 1 — Trực giác:** HashMap giống một **tủ áo có nhiều ngăn (bucket)**. Khi cầm một chiếc áo (key), bạn băm nó ra số ngăn, treo vào đúng ngăn đó. Muốn lấy lại, băm lần nữa là biết ngay vị trí — không phải lục toàn bộ tủ.

**Tầng 2 — Cơ chế chi tiết:**

```
put(key, value):
1. hash = key.hashCode() ^ (h >>> 16)   // hash tron de giam collision
2. index = hash & (capacity - 1)         // chon bucket, capacity la luy thua cua 2
3. bucket trong → Node(key, value) chen thang
4. bucket co san:
     - cung key (equals) → thay value cu
     - khac key → collision, noi them vao danh sach trong bucket
5. neu so luong phan tu > capacity * loadFactor (0.75) → resize gap doi (rehash)
```

**Các thông số quan trọng:**

- **Initial capacity** mặc định: 16.
- **Load factor** mặc định: 0.75 — khi 75% chỗ đầy thì resize để giữ hiệu năng.
- Từ Java 8: bucket quá 8 node → chuyển thành **cây đỏ-đen**, worst-case từ O(n) xuống O(log n).

**Tầng 3 — Vì sao capacity luôn là lũy thừa của 2?** Vì `hash & (capacity - 1)` với lũy thừa 2 tương đương phép chia lấy dư nhưng **nhanh hơn nhiều** (thao tác bit thay vì division).

❌ **Pitfall:** Cần nhiều thread cùng dùng map → dùng `HashMap` sẽ **hỏng dữ liệu** hoặc treo. Cần đồng thời (concurrency):
- `Hashtable` — legacy, synchronize từng phương thức, chậm;
- ✅ `ConcurrentHashMap` — khóa theo từng bucket, song song tốt hơn nhiều.

- `Hashtable` — legacy, synchronize từng phương thức, chậm;
- ✅ `ConcurrentHashMap` — khóa theo từng bucket, song song tốt hơn nhiều.

> **Tóm gọn 1 câu:** HashMap = tủ áo có ngăn: băm key ra số ngăn, tra cứu O(1) — nhưng phải override đủ `equals`/`hashCode` và đừng dùng nó cho nhiều thread.

### 5.3. LinkedHashMap & TreeMap

> 📄 **Code ví dụ:** [`examples/10-5-3-linkedhashmap-treemap.java`](examples/10-5-3-linkedhashmap-treemap.java)

## 6. Đếm tần suất — ví dụ kinh điển

Mục này đưa bạn thấy Map giải bài toán thật: đếm mỗi từ xuất hiện mấy lần — và vì sao cách gọn hơn lại an toàn hơn.

Đếm số lần xuất hiện của mỗi từ là ví dụ kết hợp `Map` + các phương thức hữu ích:

> 📄 **Code ví dụ:** [`examples/11-6-em-tan-suat-vi-du-kinh-ien.java`](examples/11-6-em-tan-suat-vi-du-kinh-ien.java)

**Vì sao cách 2 tốt hơn?** `merge()` gói gọn logic "nếu có → X, nếu không → Y" trong **một thao tác nguyên tử** — ít bug hơn, và là nền tảng để chuyển sang code song song về sau.

> **Tóm gọn 1 câu:** Đếm tần suất là bài tập nhập môn của Map — và `merge()` là phiên bản "gói sẵn if-else" ít bug hơn cả.

## 7. Stream API — xử lý dữ liệu khai báo

Mục này giới thiệu cách xử lý collection kiểu "mô tả kết quả mong muốn" thay vì viết vòng lặp từng bước.

**Tầng 1 — Trực giác:** Thay vì viết vòng lặp "bước 1, bước 2, bước 3", bạn mô tả **dữ liệu biến đổi thành gì** — giống viết SQL hơn là viết assembly.

**Tầng 2 — Cú pháp:** pipeline gồm 3 phần:

> 📄 **Code ví dụ:** [`examples/12-7-stream-api-xu-ly-du-lieu-khai.java`](examples/12-7-stream-api-xu-ly-du-lieu-khai.java)

> 📄 **Code ví dụ:** [`examples/13-7-stream-api-xu-ly-du-lieu-khai.java`](examples/13-7-stream-api-xu-ly-du-lieu-khai.java)

**Tầng 3 — Vì sao Stream "lazy"?** Các operation trung gian (`filter`, `map`, `sorted`) chỉ **ghi lại kế hoạch**. Chỉ khi gọi terminal operation (`toList`, `forEach`, `sum`) pipeline mới chạy. Nhờ đó Java có thể **gộp nhiều bước qua từng phần tử một** thay vì chạy từng bước qua cả collection, và có thể short-circuit (`anyMatch` dừng ngay khi thấy phần tử thỏa).

❌ **Pitfall 1:** Tái sử dụng stream đã tiêu:

> 📄 **Code ví dụ:** [`examples/14-7-stream-api-xu-ly-du-lieu-khai.java`](examples/14-7-stream-api-xu-ly-du-lieu-khai.java)

❌ **Pitfall 2:** Lạm dụng stream cho code có side-effect (thay đổi biến bên ngoài). Stream sinh ra để **biến đổi dữ liệu**, không phải để thay vòng lặp mutation.

✅ **Nguyên tắc chọn:** vòng lặp đơn giản 3-4 dòng → `for` dễ đọc hơn. Biến đổi nhiều tầng (lọc → biến đổi → gộp) → Stream sáng giá hơn.

> **Tóm gọn 1 câu:** Stream là "viết lệnh nhà bếp kiểu đặt món" — mô tả lọc-nấu-xếp ra đĩa là xong; chạy lazy và chỉ tiêu khi bạn gọi món cuối (terminal operation).

## 8. Bảng chọn cấu trúc dữ liệu

Mục này là "bảng giá" tổng của cả bài: đối chiếu tình huống thật với lựa chọn cấu trúc phù hợp.

| Tình huống | Lựa chọn | Lý do |
|------------|----------|-------|
| Danh sách đọc theo index | `ArrayList` | get O(1), cache-friendly |
| Kiểm tra tồn tại nhanh | `HashSet` | contains O(1) |
| Tra cứu key → value | `HashMap` | get/put O(1) |
| Dữ liệu cần sắp xếp luôn | `TreeMap`/`TreeSet` | duyệt có thứ tự, truy vấn khoảng |
| Hàng đợi / stack | `ArrayDeque` | nhanh nhất cho cả 2 đầu |
| Thread-safe | `ConcurrentHashMap`, `CopyOnWriteArrayList` | không khóa toàn cục |
| Bất biến, truyền vào API | `List.of()`, `Map.of()` | không lo bị sửa ngoài ý muốn |

> **Cách đọc bảng này:** cột **Tình huống** mô tả bài toán bạn đang có, đọc ngang sang phải để lấy cấu trúc nên dùng kèm lý do hiệu năng. Khi phân vân giữa hai lựa chọn, so cột **Lý do** của hai dòng đó.

## 9. Bài tập

1. Cho `List<Integer> numbers`, dùng Stream tìm các số chẵn, bình phương, rồi sắp xếp giảm dần.
2. Viết hàm nhận danh sách sinh viên (tên, điểm) → trả về `Map<String, Double>` điểm trung bình theo xếp loại (Giỏi/Khá/Trung bình).
3. Cho một đoạn văn, tìm **từ xuất hiện nhiều nhất** bằng `HashMap` + `merge`, không dùng thư viện ngoài.
4. Dùng `Set` kiểm tra một chuỗi có phải là pangram (chứa đủ 26 chữ cái) hay không.
5. Cài đặt một cache đơn giản bằng `LinkedHashMap` với giới hạn 100 phần tử, tự động xóa phần tử cũ nhất khi đầy (gợi ý: override `removeEldestEntry`).
6. So sánh thời gian `contains()` của `ArrayList` và `HashSet` với 1 triệu số nguyên, giải thích kết quả bằng lý thuyết độ phức tạp.

## 10. Tóm tắt

- **List** = thứ tự + trùng lặp được; **Set** = không trùng; **Map** = key → value.
- Mặc định dùng `ArrayList` + `HashMap`; chọn cấu trúc theo thao tác dominant.
- Luôn override **cả** `equals()` lẫn `hashCode()` khi dùng object làm phần tử Set/key Map.
- `HashMap` = mảng bucket + băm + cây đỏ-đen (Java 8+); hiểu cơ chế giúp trả lời phỏng vấn và tránh bug hashcode.
- Stream API = pipeline lazy: `filter`/`map`/`collect` + `groupingBy`, giúp code khai báo, ngắn gọn.
- Generics luôn có, diamond operator luôn dùng, raw type là nợ kỹ thuật.

> **Tóm gọn 1 câu:** Collections Framework là bộ "hũ - khuôn - từ điển" của Java: chọn hũ theo thao tác dominant, gắn khuôn bằng generics, và mặc định dùng `ArrayList` + `HashMap` cho tới khi có lý do đổi.
