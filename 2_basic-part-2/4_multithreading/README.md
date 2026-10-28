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

## 2. Tạo Thread — 2 cách cơ bản

### Cách 1: extends Thread

```java
public class DownloadTask extends Thread {
    private final String fileName;

    public DownloadTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        // code trong run() chay tren thread moi
        for (int i = 1; i <= 3; i++) {
            System.out.println(fileName + " - da tai " + (i * 30)
                    + "% - " + Thread.currentThread().getName());
            try {
                Thread.sleep(100); // gia lap thoi gian tai
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DownloadTask t1 = new DownloadTask("video.mp4");
        DownloadTask t2 = new DownloadTask("music.mp3");

        t1.start(); // KHONG goi run() truc tiep!
        t2.start();

        System.out.println("Main tiep tuc chay trong khi download dang xay ra");
    }
}
```

### Cách 2: implements Runnable ✅ (khuyên dùng)

```java
public class DownloadTask implements Runnable {
    private final String fileName;

    public DownloadTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.println("Dang tai: " + fileName);
    }
}

// Java 8+ co the dung lambda
Runnable task = () -> System.out.println("Cong viec nhe");
Thread t = new Thread(task);
t.start();
```

**Vì sao `Runnable` tốt hơn?**
- Java chỉ **đơn kế thừa** — dùng hết `extends Thread` thì không extends lớp khác được nữa.
- `Runnable` tách **công việc** khỏi **cơ chế thực thi** → dễ chuyển sang Executor, virtual threads.
- Nếu cần trả kết quả về, dùng `Callable<T>` (được ném checked exception).

❌ **Pitfall số 1 của người mới:** gọi `t.run()` thay vì `t.start()`:

```java
t.run();   // ✗ chay NGAY TRONG main thread, khong ton tai thread moi!
t.start(); // ✅ tao thread moi, JVM goi run() tren thread do
```

### Cách 3: Callable + Future — khi cần kết quả

```java
import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Integer> tinhTong = () -> {
            Thread.sleep(500); // gia lap tinh toan nang
            return 1 + 2 + 3;
        };

        Future<Integer> future = executor.submit(tinhTong);

        // ... lam viec khac trong khi cho ...

        Integer ketQua = future.get(); // BLOCK cho den khi co ket qua
        System.out.println(ketQua);    // 6

        executor.shutdown();
    }
}
```

## 3. Vòng đời của Thread

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

```java
// cac thao tac dieu khien co ban
Thread t = new Thread(task);
t.start();          // khoi dong
t.join();           // main cho t chay xong
t.join(3000);       // cho toi da 3 giay
t.interrupt();      // bao huy (khong bat buoc dung ngay!)
t.setDaemon(true);  // thread phu, JVM thoat khong cho no
t.isAlive();        // kiem tra con song khong
```

❌ **Pitfall:** `Thread.stop()`, `Thread.suspend()` đã bị **deprecated** vì có thể làm hỏng trạng thái dữ liệu giữa chừng. Hủy thread đúng cách = dùng cờ hoặc interrupt:

```java
// ✅ huy thread an toan bang co
volatile boolean running = true; // volatile: cac thread thay thay doi ngay

public void run() {
    while (running) {
        // lam viec...
    }
}

public void stopTask() { running = false; }

// hoac voi interrupt:
public void run() {
    while (!Thread.currentThread().isInterrupted()) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // giu trang thai interrupt
            return; // thoat
        }
    }
}
```

## 4. Vấn đề lớn: race condition

Chia sẻ dữ liệu giữa các thread mà không bảo vệ → kết quả sai không đoán được:

```java
// ✗ code loi kinh dien
public class Counter {
    private int count = 0;

    public void increment() {
        count++; // KHONG nguyen tu! = doc → +1 → ghi lai (3 buoc)
    }

    public int get() { return count; }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) counter.increment();
        });

        t1.start(); t2.start();
        t1.join();  t2.join();

        System.out.println(counter.get()); // khong phai 20000! vi du: 17342
    }
}
```

**Tầng 3 — Vì sao sai?** Hai thread cùng đọc `count = 5`, cùng cộng thành 6, cùng ghi 6 → mất một lần tăng. Chạy đủ 20.000 lần thì nhiều khả năng bị "dẫm chân". Cách sửa bằng `synchronized`, `AtomicInteger`, `Lock` xem chi tiết ở bài **5_synchronization**.

## 5. Executor Framework — quản lý thread chuyên nghiệp

**Tầng 1 — Trực giác:** Tạo Thread thủ công giống **mua xe mới mỗi lần đi chợ**: tốn kém, không kiểm soát được số lượng. Executor là **bãi taxi**: một đội xe (pool) sẵn sàng, có nhu cầu thì điều xe, đi xong trả lại đội.

**Tầng 2 — Cách dùng:**

```java
import java.util.concurrent.*;

public class ExecutorDemo {
    public static void main(String[] args) throws Exception {
        // 1. pool co dinh 4 thread
        ExecutorService pool = Executors.newFixedThreadPool(4);

        // 2. noi cac cong viec vao
        for (int i = 1; i <= 10; i++) {
            int jobId = i;
            pool.submit(() -> {
                System.out.println("Job " + jobId + " chay tren "
                        + Thread.currentThread().getName());
            });
        }

        // 3. tat pool dung cach
        pool.shutdown();
        pool.awaitTermination(30, TimeUnit.SECONDS);
    }
}
```

### 4 loại pool phổ biến

| Loại | Đặc điểm | Khi nào dùng |
|------|----------|--------------|
| `newFixedThreadPool(n)` | Đúng n thread, hàng đợi vô hạn | Workload CPU-bound, ổn định |
| `newCachedThreadPool` | Tự tạo/tái dùng, không giới hạn | Nhiều tác vụ ngắn, không đều |
| `newSingleThreadExecutor` | 1 thread, chạy tuần tự | Cần đảm bảo thứ tự |
| `newScheduledThreadPool(n)` | Chạy theo lịch / lặp lại | Cron job, heartbeat |

### Cấu hình thủ công với ThreadPoolExecutor

```java
// ✅ trong production nen tu cau hinh thay vi dung Executors factory
ExecutorService pool = new ThreadPoolExecutor(
    4,                        // corePoolSize: so thread giu lai
    16,                       // maximumPoolSize: gioi han toi da
    60L, TimeUnit.SECONDS,    // thread du thua ton tai 60s roi bi gom
    new ArrayBlockingQueue<>(100),       // hang doi gioi han 100 viec
    new ThreadPoolExecutor.AbortPolicy() // queue day → nem RejectedExecutionException
);
```

❌ **Pitfall:** `newFixedThreadPool` dùng **hàng đợi vô hạn** (`LinkedBlockingQueue`) — nếu tác vụ đến nhanh hơn xử lý, queue phình to → **OutOfMemoryError**. Trong service thật, luôn giới hạn queue và định nghĩa chính sách từ chối rõ ràng.

## 6. CompletableFuture — bất đồng bộ hiện đại

Java 8 giới thiệu `CompletableFuture` — cho phép **xâu chuỗi** các bước bất đồng bộ, giống Promise trong JavaScript:

```java
import java.util.concurrent.CompletableFuture;

public class AsyncDemo {
    public static void main(String[] args) {
        // chay bat dong bo, roi bien doi ket qua
        CompletableFuture
            .supplyAsync(() -> "du lieu thoi")   // chay tren pool ForkJoin
            .thenApply(String::toUpperCase)      // bien doi ket qua
            .thenApply(s -> s + "!")
            .thenAccept(System.out::println);    // tieu thu ket qua

        // ket hop 2 bat dong bo doc lap
        CompletableFuture<Integer> giaGoc = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Integer> thue = CompletableFuture.supplyAsync(() -> 10);

        CompletableFuture<Integer> tong = giaGoc.thenCombine(thue, Integer::sum);
        System.out.println(tong.join()); // 110

        // dung 3 request song song, cho tat ca xong
        CompletableFuture.allOf(
            CompletableFuture.runAsync(AsyncDemo::goiApiA),
            CompletableFuture.runAsync(AsyncDemo::goiApiB),
            CompletableFuture.runAsync(AsyncDemo::goiApiC)
        ).join();
    }

    static void goiApiA() { /* ... */ }
    static void goiApiB() { /* ... */ }
    static void goiApiC() { /* ... */ }
}
```

| Phương thức | Ý nghĩa |
|-------------|---------|
| `supplyAsync` / `runAsync` | Bắt đầu tác vụ có / không trả kết quả |
| `thenApply` | Biến đổi kết quả (như `map`) |
| `thenCompose` | Chuỗi future lồng nhau (như `flatMap`) |
| `thenCombine` | Ghép kết quả 2 future độc lập |
| `thenAccept` / `thenRun` | Tiêu thụ kết quả |
| `exceptionally` / `handle` | Xử lý lỗi trong chuỗi |
| `allOf` / `anyOf` | Chờ tất cả / bất kỳ |

❌ **Pitfall:** `thenApply` chạy trên **thread nào hoàn thành tác vụ trước đó** (có thể là main thread). Muốn ép chạy trên pool riêng: `thenApplyAsync(fn, executor)`.

## 7. Virtual Threads — bước nhảy vọt của Java 21

**Tầng 1 — Trực giác:** Platform thread truyền thống đắt đỏ: mỗi cái chiếm ~1MB stack và ánh xạ 1:1 với thread của OS → chỉ có thể có vài nghìn. Virtual thread là thread do **JVM tự quản**: hàng **triệu** thread vẫn chạy thoải mái, vì khi thread "ngủ" chờ I/O, JVM giải phóng thread nền để chạy việc khác.

**Tầng 2 — Cách tạo:**

```java
// Java 21+
// Cach 1: tao va chay ngay
Thread.startVirtualThread(() -> System.out.println("Xin chao tu virtual thread"));

// Cach 2: dung builder
Thread vThread = Thread.ofVirtual().name("vt-1").start(() -> {
    System.out.println(Thread.currentThread().getName());
});

// Cach 3: executor — thay the cho thread pool truyen thong
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10_000; i++) {
        int id = i;
        executor.submit(() -> {
            Thread.sleep(1000); // cho I/O - khong ton platform thread
            System.out.println("Task " + id + " xong");
            return null;
        });
    }
} // try-with-resources tu dong shutdown va cho
```

**Tầng 3 — So sánh trực diện:**

| Tiêu chí | Platform thread | Virtual thread |
|----------|-----------------|----------------|
| Chi phí tạo | ~1MB stack, tốn ms | Rẻ như object, ~KB |
| Số lượng khả thi | Hàng nghìn | Hàng triệu |
| Khi bị block I/O | Giữ nguyên tài nguyên | JVM trả platform thread về pool |
| Nên dùng cho | Tác vụ CPU-bound | Tác vụ I/O-bound (API, DB, file) |

**Vì sao đây là cuộc cách mạng?** Trước đây, để phục vụ 100.000 kết nối đồng thời với I/O-bound workload, bạn phải viết code **bất đồng bộ** kiểu `CompletableFuture` — khó đọc, khó debug ("callback hell"). Với virtual thread, bạn viết code **đồng bộ thường thường** (`Thread.sleep`, `socket.read()`) mà vẫn scale như code bất đồng bộ. Spring Boot 3.2+ bật virtual threads cho web server chỉ bằng một dòng cấu hình:

```properties
# application.properties - Spring Boot 3.2+
spring.threads.virtual.enabled=true
```

❌ **Pitfall:** Virtual thread KHÔNG giúp tác vụ CPU-bound nhanh hơn — bạn vẫn chỉ có số lõi CPU là bao nhiêu. Virtual threads chỉ giải quyết bài toán **đợi** (waiting).

❌ **Pitfall:** "Pinning" — nếu bên trong virtual thread bạn dùng `synchronized` trên block chậm hoặc chạy native code, JVM không thể trả platform thread khi block → giảm khả năng mở rộng. Java 24 đã khắc phục phần lớn trường hợp `synchronized`; với các phiên bản cũ, thay `synchronized` bằng `ReentrantLock` trong code chạy trên virtual threads.

✅ **Quy tắc vàng:** *Cần nhiều virtual thread mới có ích.* Virtual thread chạy đúng 1 tác vụ dài → vô dụng. Số lượng chính là sức mạnh.

## 8. Synchronous collections trong đa luồng

```java
import java.util.concurrent.*;

// Hang doi giua producer va consumer — khong can tu viet wait/notify
BlockingQueue<String> hangDoi = new ArrayBlockingQueue<>(100);

new Thread(() -> {           // producer
    try {
        hangDoi.put("don hang 1"); // block neu queue day
    } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}).start();

new Thread(() -> {           // consumer
    try {
        String don = hangDoi.take(); // block neu queue trong
        System.out.println("Xu ly: " + don);
    } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}).start();

// Collection thread-safe:
ConcurrentHashMap<String, Integer> dem = new ConcurrentHashMap<>();
dem.merge("an", 1, Integer::sum);  // nguyen tu, an toan giua cac thread

CopyOnWriteArrayList<String> listeners = new CopyOnWriteArrayList<>();
listeners.add("a"); // moi lan ghi tao ban sao — tot cho doc nhieu ghi it
```

## 9. Chiến lược chọn công cụ

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
