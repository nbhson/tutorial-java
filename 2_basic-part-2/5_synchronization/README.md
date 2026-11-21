# Synchronization (Đồng bộ hóa trong Java)

> Ở bài Multithreading bạn đã thấy counter bị mất bước tăng vì nhiều thread "dẫm chân" nhau. Bài này trả lời câu hỏi: **làm sao để nhiều thread dùng chung dữ liệu mà không hỏng?** Chúng ta đi từ `volatile` và `synchronized` cổ điển đến `Lock` và `Atomic`.

## 1. Vấn đề gốc: hai vấn đề song song

**Tầng 1 — Trực giác:** Hai nhân viên cùng sửa **một** bảng tính, nhưng mỗi người có **bản photo** riêng và cập nhật lại bất chợt. Nếu không có quy tắc "ai sửa phải báo mọi người", họ làm việc trên dữ liệu cũ.

**Tầng 2 — Định nghĩa:** Trong Java, mỗi thread có thể giữ giá trị biến trong **CPU cache/register** của nó. Thread A ghi `flag = true` vào cache của mình; thread B vẫn đọc `flag = false` từ cache của nó. Đây là vấn đề **visibility** (khả năng nhìn thấy).

**Tầng 3 — Hai vấn đề phải giải quyết cùng lúc:**

1. **Visibility** — thay đổi của thread này chưa "hiện" ra cho thread khác.
2. **Atomicity** — thao tác gồm nhiều bước (`count++` = đọc → cộng → ghi) bị chen ngang giữa chừng.

Tổng quan công cụ giải quyết:

| Công cụ | Visibility | Atomicity | Chi phí |
|---------|:---:|:---:|---------|
| `volatile` | ✅ | ❌ | Gần như 0 |
| `synchronized` | ✅ | ✅ | Khóa, có blocking |
| `AtomicInteger`... | ✅ | ✅ | CAS, không blocking |
| `ReentrantLock` | ✅ | ✅ | Blocking, nhưng linh hoạt |

> **Cách đọc bảng này:** đọc ngang từng dòng là một công cụ, so 2 cột giữa (Visibility/Atomicity) để biết nó chữa được vấn đề nào trong 2 vấn đề ở trên, cột **Chi phí** là cái giá phải trả — đây là bản đồ tóm tắt cả bài.

> **Tóm gọn 1 câu:** Đồng bộ hóa = giải bài "dữ liệu chung giữa nhiều thread": phải chữa cả visibility lẫn atomicity, mỗi công cụ dưới đây chữa một mức khác nhau với chi phí khác nhau.

## 2. volatile — nhẹ nhất nhưng hạn chế

Mục này nói về công cụ nhẹ nhất trong bảng: chỉ chữa visibility, không chữa atomicity — biết lúc nào đủ dùng, lúc nào là ảo tưởng an toàn.

> 📄 **Code ví dụ:** [`examples/01-2-volatile-nhe-nhat-nhung-han-che.java`](examples/01-2-volatile-nhe-nhat-nhung-han-che.java)

**Volatile đảm bảo gì?**
- Mỗi lần **đọc** lấy giá trị mới nhất từ bộ nhớ chính (không dùng cache cũ).
- Mỗi lần **ghi** được đẩy ngay ra bộ nhớ chính, và hoạt động như một "hàng rào" bộ nhớ (memory barrier) — lệnh trước và sau nó không bị JVM/CPU xáo trộn thứ tự (reordering).

**Volatile KHÔNG đảm bảo gì?** Tính nguyên tử của thao tác ghép:

> 📄 **Code ví dụ:** [`examples/02-2-volatile-nhe-nhat-nhung-han-che.java`](examples/02-2-volatile-nhe-nhat-nhung-han-che.java)

❌ **Pitfall:** Tưởng `volatile` = "thread-safe". Nó chỉ đúng khi: **một thread ghi, các thread khác chỉ đọc** (cờ điều khiển, cấu hình nóng). Nếu có nhiều thread cùng ghi, phải dùng synchronized / atomic.

✅ **Vì sao `volatile` vẫn cần?** Chi phí gần bằng 0 so với khóa, và nó giải quyết triệt để bài toán "vòng lặp không bao giờ thấy cờ dừng".

> **Tóm gọn 1 câu:** `volatile` là "loa phát thanh" bảo mọi thread nghe tin mới nhất — nhưng chỉ loa được, không khoá được cửa, nên đừng dùng nó cho dữ liệu nhiều người cùng ghi.

## 3. synchronized — khóa cổ điển

Mục này nói về công cụ khóa trăm năm không lỗi thời: một ổ khóa gắn sẵn trên mỗi object, ai giữ khóa mới được vào phòng.

### 3.1. Ba cách dùng

> 📄 **Code ví dụ:** [`examples/03-3-1-ba-cach-dung.java`](examples/03-3-1-ba-cach-dung.java)

### 3.2. Cách hoạt động — monitor và intrinsic lock

**Tầng 1:** Mỗi object Java sinh ra đều có sẵn một **ổ khóa** (intrinsic lock / monitor) đi kèm. `synchronized(obj)` nghĩa là: "muốn vào đoạn code này, trước hết phải cầm được ổ khóa của obj".

**Tầng 2 — Quy tắc:**

- Thread giữ khóa → vào vùng code.
- Thread khác đến sau → bị **BLOCKED**, xếp hàng chờ.
- Ra khỏi vùng (`}` hoặc return/ném exception) → trả khóa, thread xếp hàng đầu tiên được vào.
- Cùng một thread được **tái nhập** (reentrant) — gọi đệ quy vào synchronized của chính nó không bị chết.

**Tầng 3 — Vì sao synchronized cũng giải quyết visibility?** Khi vào/ra synchronized block, JVM thực hiện memory barrier: mọi thay đổi trong block được "đẩy" ra bộ nhớ chính, và giá trị đọc trong block là mới nhất. Đó là lý do `synchronized` che phủ cả hai vấn đề.

### 3.3. Sửa lại Counter

> 📄 **Code ví dụ:** [`examples/04-3-3-sua-lai-counter.java`](examples/04-3-3-sua-lai-counter.java)

❌ **Pitfall 1:** synchronized phương thức ghi mà quên synchronized phương thức đọc. Thread đọc có thể thấy giá trị "nửa vời" hoặc cũ vì không có barrier.

❌ **Pitfall 2:** Khóa trên object **đổi được tham chiếu**:

> 📄 **Code ví dụ:** [`examples/05-3-3-sua-lai-counter.java`](examples/05-3-3-sua-lai-counter.java)

❌ **Pitfall 3:** Dùng chuỗi literal làm khóa: `synchronized ("myLock")` — string literal được **intern** và dùng chung toàn JVM → khóa "rỉ" ra ngoài phạm vi mong muốn.

### 3.4. Deadlock — bẫy nguy hiểm nhất của synchronized

Deadlock xảy ra khi tối thiểu 2 thread giữ khóa mà cần thêm khóa mà thread kia đang giữ → cả hai chờ vĩnh viễn.

> 📄 **Code ví dụ:** [`examples/06-3-4-deadlock-bay-nguy-hiem-nhat-cua.java`](examples/06-3-4-deadlock-bay-nguy-hiem-nhat-cua.java)

✅ **Nguyên tắc ngăn deadlock (4 điều kiện, phá bỏ 1):**

> 📄 **Code ví dụ:** [`examples/07-3-4-deadlock-bay-nguy-hiem-nhat-cua.java`](examples/07-3-4-deadlock-bay-nguy-hiem-nhat-cua.java)

> **Tóm gọn 1 câu:** `synchronized` là ổ khóa gắn sẵn trên mọi object — đơn giản, tự giải phóng, chữa đủ visibility + atomicity, nhưng nhớ chọn đúng vật giữ khóa và cảnh giác deadlock.

## 4. Atomic classes — CAS thay vì khóa

Mục này giới thiệu lớp "không khóa mà vẫn an toàn": dùng phép so sánh-và-đổi nguyên tử ở cấp CPU thay vì xếp hàng xin khóa.

### 4.1. AtomicInteger / AtomicLong

> 📄 **Code ví dụ:** [`examples/08-4-1-atomicinteger-atomiclong.java`](examples/08-4-1-atomicinteger-atomiclong.java)

### 4.2. Nguyên lý hoạt động — Compare-And-Swap (CAS)

**Tầng 1:** CAS là một phép toán nguyên tử ở cấp CPU. Nó thực hiện 3 bước trong một lệnh không thể chia cắt:

```
CAS(register, expected, newValue):
  if (register == expected) {
      register = newValue;
      return true;
  } else {
      return false;
  }
```

**Tầng 2 — Quy trình `incrementAndGet()`:**

> 📄 **Code ví dụ:** [`examples/09-4-2-nguyen-ly-hoat-ong-compare-and.java`](examples/09-4-2-nguyen-ly-hoat-ong-compare-and.java)

**Tầng 3 — Vì sao nhanh hơn synchronized?**

- `synchronized`: thread không có khóa → **BLOCKED** → ngủ → thức dậy khi có khóa → có overhead context-switch rất lớn (microseconds).
- `AtomicInteger` (CAS): thread không đổi được giá trị → lập tức thử lại ngay, **không ngủ**, rất nhanh. Chỉ khi có quá nhiều thread cạnh tranh thì tỷ lệ thử lại (retry loop) tăng → hiệu năng giảm.

❌ **Pitfall:** AtomicInteger không đủ cho các thao tác phức tạp hơn 1 giá trị:

> 📄 **Code ví dụ:** [`examples/10-4-2-nguyen-ly-hoat-ong-compare-and.java`](examples/10-4-2-nguyen-ly-hoat-ong-compare-and.java)

> **Tóm gọn 1 câu:** Atomic class = "soi-hồ-sơ-xong-nhảy-vào-làm" (CAS) thay vì xếp hàng xin khóa — nhanh khi ít người tranh chấp, và chỉ giải quyết được thao tác trên một giá trị.

## 5. ReentrantLock — synchronized linh hoạt hơn

Mục này giới thiệu bản khóa "tùy chỉnh cao cấp" của synchronized: thêm được timeout, fairness và kiểm tra trạng thái — đổi lại bạn phải tự chìa khóa đúng chỗ.

> 📄 **Code ví dụ:** [`examples/11-5-reentrantlock-synchronized-linh-hoat-hon.java`](examples/11-5-reentrantlock-synchronized-linh-hoat-hon.java)

**ReentrantLock vs synchronized:**

| Tiêu chí | synchronized | ReentrantLock |
|----------|-------------|----------------|
| Syntax | Tự động giải phóng khi ra khỏi block | Phải `unlock()` thủ công trong finally |
| Trả lời thời gian chờ | ❌ Không | ✅ `tryLock(5, TimeUnit.SECONDS)` |
| Công bằng (fairness) | Không đảm bảo thứ tự | ✅ `new ReentrantLock(true)` — queue FIFO |
| Đọc trạng thái khóa | ❌ Không | ✅ `hasQueuedThreads()`, `isHeldByCurrentThread()` |
| Lock có thể reentrant | ✅ | ✅ |

> **Cách đọc bảng này:** đọc ngang từng tiêu chí để thấy ReentrantLock có gì mà synchronized không có (timeout, fairness, đọc trạng thái) — và dòng đầu nhắc bạn cái giá: phải tự `unlock()` trong finally.

❌ **Pitfall:** Quên `unlock()` trong `finally` → nếu có exception giữa chừng, khóa không bao giờ được giải phóng → chương trình treo vĩnh viễn.

✅ **Quy tắc:** Dùng `synchronized` khi code đơn giản, không cần timeout hay fairness. Chuyển sang `ReentrantLock` khi cần timeout, kiểm tra trạng thái khóa, hoặc cần fairness nghiêm ngặt.

> **Tóm gọn 1 câu:** ReentrantLock là "ổ khóa cao cấp có đồng hồ hẹn giờ và hàng ưu tiên" — nhiều tính năng hơn synchronized nhưng bẻ khóa (unlock) phải bằng tay trong `finally`.

## 6. ReadWriteLock — tối ưu cho đọc nhiều ghi ít

Mục này nói về khóa thông minh: cho phép nhiều người **đọc cùng lúc** (đọc không hỏng dữ liệu), chỉ cô lập duy nhất khi có người **ghi**.

> 📄 **Code ví dụ:** [`examples/12-6-readwritelock-toi-uu-cho-oc-nhieu-ghi.java`](examples/12-6-readwritelock-toi-uu-cho-oc-nhieu-ghi.java)

### 6.1. StampedLock (Java 8+) — đọc lạc quan

`StampedLock` cho phép đọc không khóa trước (optimistic read), kiểm tra sau:

> 📄 **Code ví dụ:** [`examples/13-6-1-stampedlock-java-8-oc-lac-quan.java`](examples/13-6-1-stampedlock-java-8-oc-lac-quan.java)

**Khi nào dùng?** Dữ liệu ít khi ghi, phần lớn thời gian chỉ đọc. StampedLock cho hiệu năng cao nhất trong trường hợp này vì hầu hết các lần đọc không khóa gì cả.

❌ **Pitfall:** StampedLock **không reentrant** — nếu cùng một thread gọi `writeLock()` 2 lần liên tiếp sẽ bị deadlock. Cũng KHÔNG dùng được với virtual threads (Java 21+) vì nó dùng `synchronized` bên trong.

## 7. Bảng chọn công cụ đồng bộ hóa

| Tình huống | Lựa chọn đúng | Lý do |
|------------|---------------|-------|
| Cờ dừng / cấu hình chỉ ghi 1 lần | `volatile` | Nhẹ nhất, đủ cho visibility |
| Counter đơn giản (`count++`) | `AtomicInteger` | CAS nhanh hơn khóa |
| Nhiều biến phụ thuộc nhau | `synchronized` block | Bao trùm cả visibility lẫn atomicity |
| Cần timeout / fairness | `ReentrantLock` | Tính năng mà synchronized không có |
| Đọc nhiều, ghi ít | `ReadWriteLock` | Nhiều đọc đồng thời |
| Đọc lạc quan (ít ghi) | `StampedLock` | Overhead thấp nhất |
| Trao đổi message giữa thread | `BlockingQueue` | Đồng bộ hóa tự động producer/consumer |

> **Cách đọc bảng này:** cột **Tình huống** là bài toán đồng bộ bạn đang gặp, đọc ngang để lấy công cụ đúng và lý do — dùng bảng này như "menu chọn món" sau khi đã đọc qua các mục 2–6.

## 8. Bài tập

1. Viết `BankAccount` có `transfer(Account other, int amount)` và `getBalance()`. Đảm bảo không deadlock khi nhiều cặp chuyển tiền chạy song song.
2. Tái hiện race condition, sau đó sửa bằng: (a) `AtomicInteger`, (b) `synchronized`, (c) `ReentrantLock`. Đo thời gian chạy 100.000 lần tăng với 10 thread.
3. Viết producer-consumer: 1 producer sinh số ngẫu nhiên, 3 consumer chia nhau xử lý, dùng `BlockingQueue`.
4. Viết cache đơn giản dùng `ConcurrentHashMap` với `computeIfAbsent` — đảm bảo chỉ tính giá trị 1 lần dù 10 thread cùng hỏi key chưa có.
5. Cài đặt bounded buffer (vòng tròn) dùng `synchronized` với `wait()`/`notifyAll()`, so sánh với phiên bản `BlockingQueue`.
6. Triển khai một semaphore cho phép tối đa N thread cùng truy cập tài nguyên (dùng `ReentrantLock` + condition variable).

## 9. Tóm tắt

- `volatile` = barrier bộ nhớ; đọc/ghi nguyên tử riêng lẻ; **không** đủ cho thao tác ghép (`count++`).
- `synchronized` = khóa cổ điển, giải quyết cả visibility và atomicity; đơn giản nhưng không có timeout/fairness.
- **Deadlock** = A giữ X đợi Y, B giữ Y đợi X. Phòng bằng: thứ tự khóa cố định hoặc tryLock.
- `AtomicInteger`... = CAS, không blocking, rất nhanh khi cạnh tranh thấp; không đủ cho logic phức tạp.
- `ReentrantLock` = linh hoạt hơn synchronized (timeout, fairness, đọc trạng thái); nhớ unlock trong finally.
- `ReadWriteLock` = nhiều reader đồng thời, writer riêng biệt.
- `StampedLock` = đọc lạc quan, hiệu năng cao cho read-heavy workloads.
- Luôn **synchronize cả get lẫn set** khi dùng shared mutable state.

> **Tóm gọn 1 câu:** Synchronization là nghệ thuật dùng chung một bảng tính giữa nhiều nhân viên: chọn loa (`volatile`), ổ khóa (`synchronized`/`ReentrantLock`), hay máy tính cá nhân tự kiểm tra (`Atomic`) theo đúng bài toán và mức tranh chấp.
