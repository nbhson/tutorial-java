# Multithreading (Đa luồng trong Java)

> Một chương trình đơn luồng chỉ làm được **một việc tại một thời điểm**. Đa luồng cho phép bạn tải file trong khi vẫn vẽ được giao diện, xử lý 10.000 request cùng lúc. Đây là chủ đề khó nhất của Java cơ bản — chúng ta sẽ đi từ Thread, Executor đến virtual threads (Java 21+).

## 1. Thread là gì? Vì sao cần đa luồng?

**Tầng 1 — Trực giác:** Một tiến trình (process) là một **nhà hàng**, còn thread là các **đầu bếp** trong nhà. Nhà có một đầu bếp (single thread) thì chế biến từng món lần lượt; nhiều đầu bếp (multithreading) thì nấu song song, dùng chung một bếp (bộ nhớ heap).

**Tầng 2 — Định nghĩa:** Thread là **đơn vị thực thi nhỏ nhất** của một tiến trình. Mỗi thread Java có:

- Call stack riêng (biến cục bộ, tham số).
- Chia sẻ chung heap với các thread khác (object, static field).

Đặc điểm quan trọng nhất: **chia sẻ heap** → trao đổi dữ liệu nhanh, nhưng cũng là nguồn của mọi vấn đề đồng bộ hóa (xem bài 5_synchronization).

**Tầng 3 — Vì sao phần cứng ép ta phải đa luồng?** CPU ngày nay có 8–16+ lõi vật lý. Chương trình đơn luồng chỉ dùng được 1 lõi, lãng phí phần còn lại. Ngoài ra với tác vụ **I/O** (đọc file, gọi API), thread phải ngồi đợi — trong lúc đợi có thể chuyển CPU cho thread khác làm việc thật.

| Loại tác vụ | Mục tiêu đa luồng | Giới hạn |
|-------------|-------------------|----------|
| CPU-bound (tính toán) | Số thread ≈ số lõi | Nhiều hơn không nhanh hơn |
| I/O-bound (mạng, file) | Nhiều thread hơn số lõi | Mỗi platform thread tốn ~1MB stack |

> **Cách đọc bảng này:** cột **Loại tác vụ** chia bài toán làm hai thế giới (tính toán vs chờ đợi), đọc ngang để thấy mục tiêu và giới hạn của mỗi thế giới — đây là bảng định hướng "bao nhiêu thread là đủ".

> **Tóm gọn 1 câu:** Thread là đầu bếp trong nhà hàng chung bếp — thêm đầu bếp để nấu song song, nhưng chỉ có ích khi việc của họ đúng "món" (CPU hay I/O).

## 2. Tạo Thread — 2 cách cơ bản

Mục này cho thấy hai cách "tuyển đầu bếp": kế thừa sẵn người làm (extends Thread) hay đăng ký dịch vụ (implements Runnable) — và vì sao cách 2 được khuyên dùng.

### Cách 1: extends Thread

> 📄 **Code ví dụ:** [`examples/01-cach-1-extends-thread.java`](examples/01-cach-1-extends-thread.java)

### Cách 2: implements Runnable ✅ (khuyên dùng)

> 📄 **Code ví dụ:** [`examples/02-cach-2-implements-runnable-khuyen-dung.java`](examples/02-cach-2-implements-runnable-khuyen-dung.java)

**Vì sao `Runnable` tốt hơn?**
- Java chỉ **đơn kế thừa** — dùng hết `extends Thread` thì không extends lớp khác được nữa.
- `Runnable` tách **công việc** khỏi **cơ chế thực thi** → dễ chuyển sang Executor, virtual threads.
- Nếu cần trả kết quả về, dùng `Callable<T>` (được ném checked exception).

❌ **Pitfall số 1 của người mới:** gọi `t.run()` thay vì `t.start()`:

> 📄 **Code ví dụ:** [`examples/03-cach-2-implements-runnable-khuyen-dung.java`](examples/03-cach-2-implements-runnable-khuyen-dung.java)

### Cách 3: Callable + Future — khi cần kết quả

> 📄 **Code ví dụ:** [`examples/04-cach-3-callable-future-khi-can-ket-qua.java`](examples/04-cach-3-callable-future-khi-can-ket-qua.java)

> **Tóm gọn 1 câu:** Dùng `Runnable` thay vì `extends Thread`, khởi động bằng `start()` chứ không phải `run()` — nhớ hai chữ đó là tránh được bẫy phổ biến nhất của người mới.

## 3. Vòng đời của Thread

Mục này vẽ "trạng thái tâm lý" của một thread: từ lúc sinh (NEW) đến lúc về hưu (TERMINATED), đi qua mấy giấc ngủ chờ nào.

```
        start()                chay xong run()
NEW ──────────▶ RUNNABLE ──────────────────▶ TERMINATED
                 ▲   │
   sleep()/wait  │   │ CPU chuyen xu
                 │   ▼
            TIMED_WAITING / WAITING / BLOCKED
                 │
                 └── notify()/timeout/acquire lock ──▶ quay ve RUNNABLE
```

| Trạng thái | Ý nghĩa |
|------------|---------|
| `NEW` | Đã tạo object, chưa `start()` |
| `RUNNABLE` | Đang chạy hoặc chờ CPU chỉ định |
| `BLOCKED` | Chờ chiếm một lock bị giữ bởi thread khác |
| `WAITING` | Chờ vô hạn (`wait()`, `join()`) |
| `TIMED_WAITING` | Chờ có thời hạn (`sleep(1000)`, `wait(1000)`) |
| `TERMINATED` | `run()` đã kết thúc |

> **Cách đọc bảng này:** mỗi dòng là một "trạng thái tâm sự" của thread, đọc theo thứ tự từ NEW xuống TERMINATED là đi hết vòng đời; cột **Ý nghĩa** nói rõ thread đang chờ điều gì khi rơi vào trạng thái đó.

> 📄 **Code ví dụ:** [`examples/05-3-vong-oi-cua-thread.java`](examples/05-3-vong-oi-cua-thread.java)

❌ **Pitfall:** `Thread.stop()`, `Thread.suspend()` đã bị **deprecated** vì có thể làm hỏng trạng thái dữ liệu giữa chừng. Hủy thread đúng cách = dùng cờ hoặc interrupt:

> 📄 **Code ví dụ:** [`examples/06-3-vong-oi-cua-thread.java`](examples/06-3-vong-oi-cua-thread.java)

> **Tóm gọn 1 câu:** Thread sinh ra ở NEW, sống ở RUNNABLE, thỉnh thoảng ngủ (WAITING/BLOCKED), chết ở TERMINATED — và cách duy nhất để "cho nghỉ việc" đúng luật là interrupt, không phải `stop()`.

## 4. Vấn đề lớn: race condition

Mục này dựng lên "hiện tượng" trước khi bài 5_synchronization đưa ra "phương thuốc": nhiều thread cùng sửa một dữ liệu thì kết quả sai kiểu nào.

Chia sẻ dữ liệu giữa các thread mà không bảo vệ → kết quả sai không đoán được:

> 📄 **Code ví dụ:** [`examples/07-4-van-e-lon-race-condition.java`](examples/07-4-van-e-lon-race-condition.java)

**Tầng 3 — Vì sao sai?** Hai thread cùng đọc `count = 5`, cùng cộng thành 6, cùng ghi 6 → mất một lần tăng. Chạy đủ 20.000 lần thì nhiều khả năng bị "dẫm chân". Cách sửa bằng `synchronized`, `AtomicInteger`, `Lock` xem chi tiết ở bài **5_synchronization**.

## 5. Executor Framework — quản lý thread chuyên nghiệp

**Tầng 1 — Trực giác:** Tạo Thread thủ công giống **mua xe mới mỗi lần đi chợ**: tốn kém, không kiểm soát được số lượng. Executor là **bãi taxi**: một đội xe (pool) sẵn sàng, có nhu cầu thì điều xe, đi xong trả lại đội.

**Tầng 2 — Cách dùng:**

> 📄 **Code ví dụ:** [`examples/08-5-executor-framework-quan-ly-thread-chuyen-nghiep.java`](examples/08-5-executor-framework-quan-ly-thread-chuyen-nghiep.java)

### 4 loại pool phổ biến

| Loại | Đặc điểm | Khi nào dùng |
|------|----------|--------------|
| `newFixedThreadPool(n)` | Đúng n thread, hàng đợi vô hạn | Workload CPU-bound, ổn định |
| `newCachedThreadPool` | Tự tạo/tái dùng, không giới hạn | Nhiều tác vụ ngắn, không đều |
| `newSingleThreadExecutor` | 1 thread, chạy tuần tự | Cần đảm bảo thứ tự |
| `newScheduledThreadPool(n)` | Chạy theo lịch / lặp lại | Cron job, heartbeat |

> **Cách đọc bảng này:** cột **Loại** là 4 phương thức dựng sẵn của `Executors`, đọc ngang để biết tính cách từng loại pool và tình huống nào nên cài nó; hầu hết service thật dùng `newFixedThreadPool` hoặc tự cấu hình như mục dưới.

### Cấu hình thủ công với ThreadPoolExecutor

> 📄 **Code ví dụ:** [`examples/09-cau-hinh-thu-cong-voi-threadpoolexecutor.java`](examples/09-cau-hinh-thu-cong-voi-threadpoolexecutor.java)

❌ **Pitfall:** `newFixedThreadPool` dùng **hàng đợi vô hạn** (`LinkedBlockingQueue`) — nếu tác vụ đến nhanh hơn xử lý, queue phình to → **OutOfMemoryError**. Trong service thật, luôn giới hạn queue và định nghĩa chính sách từ chối rõ ràng.

> **Tóm gọn 1 câu:** Đừng tự mua xe mỗi lần đi chợ — dùng Executor (bãi taxi có đội xe sẵn), và nhớ pool nào cũng phải có giới hạn queue nếu không OOM chờ sẵn.

## 6. CompletableFuture — bất đồng bộ hiện đại

Mục này nói về cách xâu chuỗi các việc bất đồng bộ thành "dây chuyền có cam kết trả kết quả" thay vì ngồi chờ từng việc một.

Java 8 giới thiệu `CompletableFuture` — cho phép **xâu chuỗi** các bước bất đồng bộ, giống Promise trong JavaScript:

> 📄 **Code ví dụ:** [`examples/10-6-completablefuture-bat-ong-bo-hien-ai.java`](examples/10-6-completablefuture-bat-ong-bo-hien-ai.java)

| Phương thức | Ý nghĩa |
|-------------|---------|
| `supplyAsync` / `runAsync` | Bắt đầu tác vụ có / không trả kết quả |
| `thenApply` | Biến đổi kết quả (như `map`) |
| `thenCompose` | Chuỗi future lồng nhau (như `flatMap`) |
| `thenCombine` | Ghép kết quả 2 future độc lập |
| `thenAccept` / `thenRun` | Tiêu thụ kết quả |
| `exceptionally` / `handle` | Xử lý lỗi trong chuỗi |
| `allOf` / `anyOf` | Chờ tất cả / bất kỳ |

> **Cách đọc bảng này:** cột **Phương thức** là các mắt xích trong dây chuyền CompletableFuture, đọc từ trên xuống theo trình tự một pipeline điển hình: khởi động → biến đổi → gộp → xử lý lỗi → chờ kết quả.

❌ **Pitfall:** `thenApply` chạy trên **thread nào hoàn thành tác vụ trước đó** (có thể là main thread). Muốn ép chạy trên pool riêng: `thenApplyAsync(fn, executor)`.

> **Tóm gọn 1 câu:** CompletableFuture là "biên lai cam kết" của việc bất đồng bộ — xâu chuỗi được, gộp được, bắt lỗi được, nhưng cẩn thận nó chạy trên thread nào.

## 7. Virtual Threads — bước nhảy vọt của Java 21

Mục này nói về "nhân viên thời vụ" phiên bản mới của Java: thuê hàng triệu cái mà không lo nhà kho (stack) đầy — giới hạn duy nhất là bạn phải biết khi nào chúng có ích.

**Tầng 1 — Trực giác:** Platform thread truyền thống đắt đỏ: mỗi cái chiếm ~1MB stack và ánh xạ 1:1 với thread của OS → chỉ có thể có vài nghìn. Virtual thread là thread do **JVM tự quản**: hàng **triệu** thread vẫn chạy thoải mái, vì khi thread "ngủ" chờ I/O, JVM giải phóng thread nền để chạy việc khác.

**Tầng 2 — Cách tạo:**

> 📄 **Code ví dụ:** [`examples/11-7-virtual-threads-buoc-nhay-vot-cua-java.java`](examples/11-7-virtual-threads-buoc-nhay-vot-cua-java.java)

**Tầng 3 — So sánh trực diện:**

| Tiêu chí | Platform thread | Virtual thread |
|----------|-----------------|----------------|
| Chi phí tạo | ~1MB stack, tốn ms | Rẻ như object, ~KB |
| Số lượng khả thi | Hàng nghìn | Hàng triệu |
| Khi bị block I/O | Giữ nguyên tài nguyên | JVM trả platform thread về pool |
| Nên dùng cho | Tác vụ CPU-bound | Tác vụ I/O-bound (API, DB, file) |

> **Cách đọc bảng này:** so sánh hai cột platform vs virtual theo từng tiêu chí — đọc cột phải (virtual) sẽ thấy toàn bộ ưu điểm chỉ phát huy ở tác vụ I/O-bound, đúng theo dòng cuối bảng.

**Vì sao đây là cuộc cách mạng?** Trước đây, để phục vụ 100.000 kết nối đồng thời với I/O-bound workload, bạn phải viết code **bất đồng bộ** kiểu `CompletableFuture` — khó đọc, khó debug ("callback hell"). Với virtual thread, bạn viết code **đồng bộ thường thường** (`Thread.sleep`, `socket.read()`) mà vẫn scale như code bất đồng bộ. Spring Boot 3.2+ bật virtual threads cho web server chỉ bằng một dòng cấu hình:

> 📄 **Code ví dụ:** [`examples/12-7-virtual-threads-buoc-nhay-vot-cua-java.properties`](examples/12-7-virtual-threads-buoc-nhay-vot-cua-java.properties)

❌ **Pitfall:** Virtual thread KHÔNG giúp tác vụ CPU-bound nhanh hơn — bạn vẫn chỉ có số lõi CPU là bao nhiêu. Virtual threads chỉ giải quyết bài toán **đợi** (waiting).

❌ **Pitfall:** "Pinning" — nếu bên trong virtual thread bạn dùng `synchronized` trên block chậm hoặc chạy native code, JVM không thể trả platform thread khi block → giảm khả năng mở rộng. Java 24 đã khắc phục phần lớn trường hợp `synchronized`; với các phiên bản cũ, thay `synchronized` bằng `ReentrantLock` trong code chạy trên virtual threads.

✅ **Quy tắc vàng:** *Cần nhiều virtual thread mới có ích.* Virtual thread chạy đúng 1 tác vụ dài → vô dụng. Số lượng chính là sức mạnh.

> **Tóm gọn 1 câu:** Virtual thread = thuê hàng triệu "nhân viên thời vụ" rẻ bèo để đứng chờ I/O — nhưng chỉ có giá trị khi bạn thuê cả đội, thuê một đứa thì phí.

## 8. Synchronous collections trong đa luồng

Mục này nhanh gọn giới thiệu các collection "an toàn cho nhiều người cùng đụng vào".

> 📄 **Code ví dụ:** [`examples/13-8-synchronous-collections-trong-a-luong.java`](examples/13-8-synchronous-collections-trong-a-luong.java)

> **Tóm gọn 1 câu:** Khi nhiều thread cùng dùng một collection, đừng dùng bản thường + tự khóa — hãy lấy bản "an toàn sẵn" như `ConcurrentHashMap` hay `BlockingQueue`.

## 9. Chiến lược chọn công cụ

Mục này là "sơ đồ chỉ đường" cuối bài: nghe bài toán, đi theo nhánh, chọn đúng công cụ.

```
Cần chạy song song một nhóm tác vụ I/O?
├── Java 21+ → Executors.newVirtualThreadPerTaskExecutor() ✅
└── Java cũ  → ThreadPoolExecutor có cấu hình rõ ràng

Cần tính toán CPU song song?
└── ForkJoinPool / parallelStream (xem bài Stream API)

Cần chuỗi bước bất đồng bộ phức tạp?
└── CompletableFuture (chạy trên virtual thread pool càng tốt)

Vẫn dùng Thread trực tiếp?
└── Chỉ cho tác vụ background đơn giản, dài hạn (daemon thread)
```

## 10. Bài tập

1. Tạo 2 thread in chẵn và lẻ xen kẽ từ 1–20 (dùng `wait()`/`notifyAll()`).
2. Viết chương trình tải "10 file" song song bằng `ExecutorService`, in ra thứ tự hoàn thành — chạy 3 lần, giải thích vì sao thứ tự khác nhau.
3. Dùng `CompletableFuture` gọi 3 "API" giả lập (mỗi cái ngủ 1s) và tổng hợp kết quả — đo tổng thời gian, chứng minh nó ≈ 1s chứ không phải 3s.
4. Tái hiện race condition với counter không đồng bộ, sau đó sửa bằng cả 3 cách: `synchronized`, `AtomicInteger`, `ReentrantLock`. Đo thời gian từng cách.
5. Viết producer-consumer với `BlockingQueue`: 1 producer sinh số ngẫu nhiên, 3 consumer tính tổng.
6. Với Java 21+, viết chương trình tạo 100.000 virtual thread, mỗi thread ngủ 1s. Đo thời gian và bộ nhớ, so sánh với platform thread (thử với 10.000 platform thread và ghi nhận hiện tượng).

## 11. Tóm tắt

- Thread = luồng thực thi chia sẻ heap; tạo bằng `Runnable` + lambda, khởi động bằng `start()`.
- Race condition sinh ra từ việc chia sẻ trạng thái; bài 5_synchronization sẽ xử lý triệt để.
- **Executor Framework** thay cho việc tự quản Thread: pool cố định, scheduled, cấu hình thủ công.
- `CompletableFuture` = chuỗi bất đồng bộ có thể compose, xử lý lỗi, gộp kết quả.
- **Virtual threads (Java 21+)** = thread nhẹ hàng triệu cái, viết code đồng bộ mà scale bất đồng bộ; chỉ có lợi cho I/O-bound.
- Đừng quên `shutdown()` executor và xử lý `InterruptedException` đúng cách (restore interrupt flag).

> **Tóm gọn 1 câu:** Đa luồng là thuê thêm đầu bếp cho nhà hàng: chọn đúng loại nhân công (platform/virtual), quản bằng Executor, và xử lý dứt điểm chuyện dẫm chân bằng bài synchronization kế tiếp.
