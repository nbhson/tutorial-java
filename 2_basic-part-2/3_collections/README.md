# Collections Framework (Bộ sưu tập trong Java)

> Trong thực tế, bạn gần như **không bao giờ** làm việc với một phần dữ liệu đơn lẻ. Bạn có một danh sách học sinh, một giỏ hàng, một bảng tra mã tỉnh → tên tỉnh. **Collections Framework** là bộ công cụ chuẩn của Java để lưu trữ và xử lý dữ liệu dạng "nhiều phần tử" — và là chủ đề bị hỏi nhiều nhất trong phỏng vấn.

## 1. Collections là gì? Vì sao cần nó?

**Tầng 1 — Trực giác:** Mảng (`int[]`) có kích thước **cố định**. Khai báo mảng 10 phần tử rồi cần phần tử thứ 11, bạn phải tạo mảng mới và copy toàn bộ dữ liệu. Collections giải quyết vấn đề đó: cấu trúc dữ liệu **tự giãn nở**, kèm sẵn hàng trăm phương thức hữu ích (thêm, xóa, tìm kiếm, sắp xếp...).

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

❌ **Pitfall:** Dùng `ArrayList` cho mọi thứ vì "quen tay". Khi cần kiểm tra "email này đã tồn tại chưa?" trên 1 triệu phần tử, `ArrayList.contains()` mất O(n) còn `HashSet.contains()` chỉ mất O(1).

✅ **Đúng:** Hãy chọn cấu trúc theo **thao tác dominant** của bài toán: đọc theo index nhiều → `ArrayList`; tra cứu nhiều → `Map`/`Set`.

## 2. Generics — bắt buộc phải nắm trước

Từ Java 5, Collections dùng **Generics** để ràng buộc kiểu phần tử lúc **biên dịch**:

```java
// Khong co generics: nhet gi vao cung duoc, lay ra phai ep kieu
List list = new ArrayList();
list.add("hello");
list.add(42);
String s = (String) list.get(1); // ClassCastException khi chay!

// Co generics: compiler chan ngay tu buoc bien dich
List<String> list2 = new ArrayList<>();
list2.add("hello");
// list2.add(42); // ← loi bien dich, catch ngay trong IDE
String s2 = list2.get(0); // khong can ep kieu
```

❌ **Pitfall:** Dùng raw type `List` (không có `<>`). Từ Java 7 trở đi hãy dùng **diamond operator**: `new ArrayList<>()`.

**Vì sao?** Generics giúp phần lớn lỗi sai kiểu bị phát hiện lúc compile thay vì lúc production nổ `ClassCastException` lúc 2h sáng.

## 3. List — danh sách có thứ tự

### 3.1. Các phương thức quan trọng

```java
import java.util.ArrayList;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        List<String> monAn = new ArrayList<>();

        // Them phan tu
        monAn.add("Pho");
        monAn.add("Bun bo");
        monAn.add("Com tam");

        // Truy cap theo index - dac quyen cua List
        System.out.println(monAn.get(0));      // Pho
        System.out.println(monAn.size());      // 3
        System.out.println(monAn.contains("Pho")); // true

        // Chen vao giua
        monAn.add(1, "Banh xeo");              // Pho, Banh xeo, Bun bo, Com tam

        // Thay the
        monAn.set(2, "Bun cha");               // thay Bun bo bang Bun cha

        // Xoa
        monAn.remove("Com tam");               // xoa theo gia tri
        monAn.remove(0);                       // xoa theo index

        // Duyet
        for (String m : monAn) {
            System.out.println(m);
        }

        // Duyet voi index
        for (int i = 0; i < monAn.size(); i++) {
            System.out.println(i + ": " + monAn.get(i));
        }
    }
}
```

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

**Tầng 3 — Vì sao ArrayList thắng gần như mọi trường hợp?**

Lý do là **cache locality**: CPU đọc bộ nhớ theo từng block. Với `ArrayList`, các phần tử nằm cạnh nhau nên khi duyệt, dữ liệu đã có sẵn trong CPU cache. Với `LinkedList`, mỗi node có thể nằm ở một chỗ khác nhau trong heap → mỗi lần "nhảy" qua con trỏ là một lần cache miss.

```java
// Do thuc te: duyet 1 trieu phan tu
// ArrayList:  ~2-5 ms
// LinkedList: ~10-20 ms (cham hon vi cache miss lien tuc)
```

✅ **Kết luận thực tế:** mặc định dùng `ArrayList`. Chỉ cân nhắc `LinkedList` khi làm queue/deque với thao tác chèn/xóa ở **đầu danh sách** liên tục — và ngay cả khi đó, `ArrayDeque` vẫn thường nhanh hơn.

❌ **Pitfall:** `ArrayList` chưa chỉ định dung lượng ban đầu mà thêm 1 triệu phần tử sẽ bị **resize nhiều lần** (mỗi lần copy toàn bộ mảng). Nếu biết trước số lượng, hãy set: `new ArrayList<>(1_000_000)`.

### 3.3. ArrayList vận hành bên trong như thế nào?

```java
// Gia dinh ArrayList co 3 truong:
// Object[] elementData; // mang luu du lieu thuc su
// int size;             // so phan tu dang giu
// int capacity;         // do dai hien tai cua elementData

add("A")  → elementData[0] = "A", size = 1
add("B")  → elementData[1] = "B", size = 2
add("C")  → size == capacity?
              - neu chua day: them binh thuong
              - neu day: tao mang moi capacity * 1.5, copy toan bo, roi them
```

Đó là lý do `add()` ở cuối là **O(1) trung bình** (amortized) dù thỉnh thoảng tốn O(n) khi resize.

## 4. Set — không cho phép trùng lặp

### 4.1. HashSet

```java
import java.util.HashSet;
import java.util.Set;

public class SetDemo {
    public static void main(String[] args) {
        Set<String> emails = new HashSet<>();
        emails.add("an@gmail.com");
        emails.add("binh@gmail.com");
        emails.add("an@gmail.com");         // bi tu choi, tra ve false

        System.out.println(emails.size());  // 2 - khong trung lap
        System.out.println(emails.contains("an@gmail.com")); // true, O(1)

        // Phep tap hop
        Set<String> nhom1 = Set.of("a", "b", "c");
        Set<String> nhom2 = new HashSet<>(Set.of("b", "c", "d"));

        Set<String> giao = new HashSet<>(nhom1);   // copy de tranh sua Set.of
        giao.retainAll(nhom2);                     // {b, c}

        Set<String> hop = new HashSet<>(nhom1);
        hop.addAll(nhom2);                         // {a, b, c, d}
    }
}
```

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

```java
class Student {
    String id;
    Student(String id) { this.id = id; }

    // CHI override equals - SAI
    @Override
    public boolean equals(Object o) {
        return o instanceof Student s && s.id.equals(this.id);
    }
    // Thieu hashCode() → hai Student cung id co the bam ra 2 bucket khac nhau
}

// ✅ dung: luon override cap doi
@Override
public int hashCode() {
    return id.hashCode();
}
```

❌ **Pitfall:** Mutate một object **sau khi** đã đưa vào `HashSet` (thay đổi field tham gia vào hashCode). Object vẫn nằm trong Set nhưng bạn không thể tìm ra nó nữa vì hash đã đổi.

✅ **Fix:** Dùng object **immutable** trong Set/Map (đây cũng là lý do `String` được thiết kế immutable).

### 4.3. TreeSet — có thứ tự

`TreeSet` dựa trên **cây đỏ-đen**, các phần tử luôn được duyệt theo thứ tự sắp xếp (O(log n) mỗi thao tác). Dùng khi bạn cần dữ liệu **luôn có thứ tự** hoặc cần các truy vấn khoảng (`first`, `last`, `ceiling`, `floor`).

```java
TreeSet<Integer> diem = new TreeSet<>();
diem.add(7); diem.add(9); diem.add(5);
System.out.println(diem);            // [5, 7, 9]
System.out.println(diem.first());    // 5
System.out.println(diem.last());     // 9
System.out.println(diem.ceiling(6)); // 7 - phan tu nho nhat >= 6
```

## 5. Map — cặp key → value

### 5.1. HashMap cơ bản

```java
import java.util.HashMap;
import java.util.Map;

public class MapDemo {
    public static void main(String[] args) {
        Map<String, Integer> tuoi = new HashMap<>();

        // Them / cap nhat
        tuoi.put("An", 25);
        tuoi.put("Binh", 30);
        tuoi.put("An", 26);              // key trung → ghi de, tra ve 25 (gia tri cu)

        // Doc
        System.out.println(tuoi.get("An"));         // 26
        System.out.println(tuoi.get("Chi"));        // null - khong co key nay

        // Doc an toan
        System.out.println(tuoi.getOrDefault("Chi", 0)); // 0
        System.out.println(tuoi.containsKey("Binh"));    // true

        // Cap nhat gia tri dung dang
        tuoi.putIfAbsent("Chi", 28);     // chi them khi chua co
        tuoi.computeIfAbsent("Dung", k -> 40); // tinh gia tri khi thieu

        // Xoa
        tuoi.remove("Binh");

        // Duyet - cach chuan
        for (Map.Entry<String, Integer> e : tuoi.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }

        // Duyet rieng key / rieng value
        for (String ten : tuoi.keySet())   { System.out.println(ten); }
        for (int t : tuoi.values())        { System.out.println(t); }

        // Duyet voi forEach (Java 8+)
        tuoi.forEach((ten, t) -> System.out.println(ten + " = " + t));
    }
}
```

❌ **Pitfall:** `get()` trả về `null` cho key không tồn tại → nếu value là primitive được autobox (`Map<String, Integer>`), unboxing `null` thành `int` gây **NullPointerException**:

```java
Map<String, Integer> diem = new HashMap<>();
int x = diem.get("khong co");       // NPE! get tra null, unboxing no
int y = diem.getOrDefault("khong co", 0); // ✅ an toan
```

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

### 5.3. LinkedHashMap & TreeMap

```java
// LinkedHashMap: giu dung thu tu chen → danh cho cache LRU don gian
Map<String, Integer> cache = new LinkedHashMap<>(16, 0.75f, true); // true = access-order
cache.put("a", 1);
cache.put("b", 2);
cache.get("a");            // "a" duoc dung → duoi len gan day
System.out.println(cache.keySet()); // [b, a]

// TreeMap: sap xep theo key, hon hop bien doi
Map<String, Integer> diemSo = new TreeMap<>();
diemSo.put("An", 8); diemSo.put("Binh", 9);
System.out.println(diemSo);   // {An=8, Binh=9} - theo thu tu alphabet
```

## 6. Đếm tần suất — ví dụ kinh điển

Đếm số lần xuất hiện của mỗi từ là ví dụ kết hợp `Map` + các phương thức hữu ích:

```java
import java.util.*;

public class WordCount {
    public static void main(String[] args) {
        String text = "an bun pho an com bun an";
        String[] words = text.split(" ");

        // Cach 1: du loi
        Map<String, Integer> dem1 = new HashMap<>();
        for (String w : words) {
            if (dem1.containsKey(w)) {
                dem1.put(w, dem1.get(w) + 1);
            } else {
                dem1.put(w, 1);
            }
        }

        // Cach 2: gon hon - merge
        Map<String, Integer> dem2 = new HashMap<>();
        for (String w : words) {
            dem2.merge(w, 1, Integer::sum); // neu co roi cong them 1, chua co thi 1
        }

        System.out.println(dem2); // {an=3, bun=2, pho=1, com=1}
    }
}
```

**Vì sao cách 2 tốt hơn?** `merge()` gói gọn logic "nếu có → X, nếu không → Y" trong **một thao tác nguyên tử** — ít bug hơn, và là nền tảng để chuyển sang code song song về sau.

## 7. Stream API — xử lý dữ liệu khai báo

**Tầng 1 — Trực giác:** Thay vì viết vòng lặp "bước 1, bước 2, bước 3", bạn mô tả **dữ liệu biến đổi thành gì** — giống viết SQL hơn là viết assembly.

**Tầng 2 — Cú pháp:** pipeline gồm 3 phần:

```java
source ──▶ intermediate ops ──▶ terminal op
            (lazy, xích dài tùy ý)  (kích hoạt chạy thật)
```

```java
import java.util.*;
import java.util.stream.*;

public class StreamDemo {
    record SanPham(String ten, String loai, int gia) {}

    public static void main(String[] args) {
        List<SanPham> ds = List.of(
            new SanPham("Laptop", "Dien tu", 20000),
            new SanPham("Chuot", "Dien tu", 300),
            new SanPham("Ban phim", "Dien tu", 700),
            new SanPham("Ghe", "Noi that", 1500)
        );

        // 1. filter + map + collect: loc dien tu gia < 1000, lay ten
        List<String> re = ds.stream()
                .filter(p -> p.loai().equals("Dien tu"))
                .filter(p -> p.gia() < 1000)
                .map(SanPham::ten)
                .toList();                          // [Chuot, Ban phim]

        // 2. sorted + limit: top 2 san pham re nhat
        List<SanPham> top2 = ds.stream()
                .sorted(Comparator.comparingInt(SanPham::gia))
                .limit(2)
                .toList();

        // 3. reduce: tong gia tri kho hang
        int tong = ds.stream()
                .mapToInt(SanPham::gia)
                .sum();                             // IntStream co sum san

        // 4. group: nhom theo loai
        Map<String, List<SanPham>> theoLoai = ds.stream()
                .collect(Collectors.groupingBy(SanPham::loai));

        // 5. group + dem
        Map<String, Long> soLuong = ds.stream()
                .collect(Collectors.groupingBy(SanPham::loai, Collectors.counting()));

        // 6. group + trung binh
        Map<String, Double> giaTB = ds.stream()
                .collect(Collectors.groupingBy(SanPham::loai,
                         Collectors.averagingInt(SanPham::gia)));

        // 7. anyMatch / allMatch / noneMatch
        boolean coDat = ds.stream().anyMatch(p -> p.gia() > 15000);
        boolean tatCaDuong = ds.stream().allMatch(p -> p.gia() > 0);

        // 8. min / max
        Optional<SanPham> datNhat = ds.stream()
                .max(Comparator.comparingInt(SanPham::gia));

        // 9. flatMap: lam phang danh sach cua danh sach
        List<List<Integer>> nhomDiem = List.of(List.of(8, 9), List.of(5, 7));
        List<Integer> tatCaDiem = nhomDiem.stream()
                .flatMap(List::stream)
                .toList();                          // [8, 9, 5, 7]

        // 10. toMap
        Map<String, Integer> giaTheoTen = ds.stream()
                .collect(Collectors.toMap(SanPham::ten, SanPham::gia));
    }
}
```

**Tầng 3 — Vì sao Stream "lazy"?** Các operation trung gian (`filter`, `map`, `sorted`) chỉ **ghi lại kế hoạch**. Chỉ khi gọi terminal operation (`toList`, `forEach`, `sum`) pipeline mới chạy. Nhờ đó Java có thể **gộp nhiều bước qua từng phần tử một** thay vì chạy từng bước qua cả collection, và có thể short-circuit (`anyMatch` dừng ngay khi thấy phần tử thỏa).

❌ **Pitfall 1:** Tái sử dụng stream đã tiêu:

```java
Stream<SanPham> s = ds.stream();
s.filter(p -> p.gia() > 100).toList();
s.count(); // IllegalStateException: stream has already been operated upon
```

❌ **Pitfall 2:** Lạm dụng stream cho code có side-effect (thay đổi biến bên ngoài). Stream sinh ra để **biến đổi dữ liệu**, không phải để thay vòng lặp mutation.

✅ **Nguyên tắc chọn:** vòng lặp đơn giản 3-4 dòng → `for` dễ đọc hơn. Biến đổi nhiều tầng (lọc → biến đổi → gộp) → Stream sáng giá hơn.

## 8. Bảng chọn cấu trúc dữ liệu

| Tình huống | Lựa chọn | Lý do |
|------------|----------|-------|
| Danh sách đọc theo index | `ArrayList` | get O(1), cache-friendly |
| Kiểm tra tồn tại nhanh | `HashSet` | contains O(1) |
| Tra cứu key → value | `HashMap` | get/put O(1) |
| Dữ liệu cần sắp xếp luôn | `TreeMap`/`TreeSet` | duyệt có thứ tự, truy vấn khoảng |
| Hàng đợi / stack | `ArrayDeque` | nhanh nhất cho cả 2 đầu |
| Thread-safe | `ConcurrentHashMap`, `CopyOnWriteArrayList` | không khóa toàn cục |
| Bất biến, truyền vào API | `List.of()`, `Map.of()` | không lo bị sửa ngoài ý muốn |

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
