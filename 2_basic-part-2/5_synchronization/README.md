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

## 2. volatile — nhẹ nhất nhưng hạn chế

```java
public class TaskRunner {
    // co volatile: moi lan doc deu lay gia tri moi nhat tu bo nho chinh
    private volatile boolean running = true;

    public void run() {
        while (running) {
            // lam viec...
        }
        System.out.println("Da thoat vong lap"); // co volatile thi moi duoc chay
    }

    public void stop() {
        running = false; // thread khac ghi, thread run nhin thay ngay
    }
}
```

**Volatile đảm bảo gì?**
- Mỗi lần **đọc** lấy giá trị mới nhất từ bộ nhớ chính (không dùng cache cũ).
- Mỗi lần **ghi** được đẩy ngay ra bộ nhớ chính, và hoạt động như một "hàng rào" bộ nhớ (memory barrier) — lệnh trước và sau nó không bị JVM/CPU xáo trộn thứ tự (reordering).

**Volatile KHÔNG đảm bảo gì?** Tính nguyên tử của thao tác ghép:

```java
volatile int count = 0;

void increment() {
    count++; // van SAI voi nhieu thread! doc → +1 → ghi van la 3 buoc
}
```

❌ **Pitfall:** Tưởng `volatile` = "thread-safe". Nó chỉ đúng khi: **một thread ghi, các thread khác chỉ đọc** (cờ điều khiển, cấu hình nóng). Nếu có nhiều thread cùng ghi, phải dùng synchronized / atomic.

✅ **Vì sao `volatile` vẫn cần?** Chi phí gần bằng 0 so với khóa, và nó giải quyết triệt để bài toán "vòng lặp không bao giờ thấy cờ dừng".

## 3. synchronized — khóa cổ điển

### 3.1. Ba cách dùng

```java
public class Counter {
    private int count = 0;
    private final Object lock = new Object();

    // Cach 1: synchronized method — khoa la this
    public synchronized void tang() {
        count++;
    }

    // Cach 2: synchronized static — khoa la class object (Counter.class)
    public static synchronized void reset() {
        // chi 1 thread bat ky trong toan bo JVM vao day
    }

    // Cach 3: synchronized block — khoa bat ky object nao ✅ linh hoat nhat
    public void tangNhanh() {
        // phan khong can khoa: lam binh thuong
        synchronized (lock) {
            count++;
        }
        // phan khong can khoa
    }
}
```

### 3.2. Cách hoạt động — monitor và intrinsic lock

**Tầng 1:** Mỗi object Java sinh ra đều có sẵn một **ổ khóa** (intrinsic lock / monitor) đi kèm. `synchronized(obj)` nghĩa là: "muốn vào đoạn code này, trước hết phải cầm được ổ khóa của obj".

**Tầng 2 — Quy tắc:**

- Thread giữ khóa → vào vùng code.
- Thread khác đến sau → bị **BLOCKED**, xếp hàng chờ.
- Ra khỏi vùng (`}` hoặc return/ném exception) → trả khóa, thread xếp hàng đầu tiên được vào.
- Cùng một thread được **tái nhập** (reentrant) — gọi đệ quy vào synchronized của chính nó không bị chết.

**Tầng 3 — Vì sao synchronized cũng giải quyết visibility?** Khi vào/ra synchronized block, JVM thực hiện memory barrier: mọi thay đổi trong block được "đẩy" ra bộ nhớ chính, và giá trị đọc trong block là mới nhất. Đó là lý do `synchronized` che phủ cả hai vấn đề.

### 3.3. Sửa lại Counter

```java
public class SafeCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int get() { // doc cung phai synchronized!
        return count;
    }
}

// Dang static: bien dem dung chung cho moi instance
public class GlobalCounter {
    private static int count = 0;

    public static void increment() {
        synchronized (GlobalCounter.class) {
            count++;
        }
    }
}
```

❌ **Pitfall 1:** synchronized phương thức ghi mà quên synchronized phương thức đọc. Thread đọc có thể thấy giá trị "nửa vời" hoặc cũ vì không có barrier.

❌ **Pitfall 2:** Khóa trên object **đổi được tham chiếu**:

```java
private String lock = "KHOA";          // ✗
public void doSomething() {
    synchronized (lock) { /* ... */ }  // neu ai do gan lock = "KHOA KHAC" → khoa vo hieu hoa
}
private final Object lock = new Object(); // ✅ final, khong ai doi duoc
```

❌ **Pitfall 3:** Dùng chuỗi literal làm khóa: `synchronized ("myLock")` — string literal được **intern** và dùng chung toàn JVM → khóa "rỉ" ra ngoài phạm vi mong muốn.

### 3.4. Deadlock — bẫy nguy hiểm nhất của synchronized

Deadlock xảy ra khi tối thiểu 2 thread giữ khóa mà cần thêm khóa mà thread kia đang giữ → cả hai chờ vĩnh viễn.

```java
public class Account {
    private int balance;
    private final Object lock = new Object();

    public void transferTo(Account other, int amount) {
        synchronized (this.lock) {       // Thread A: khoa tai khoan nguoi gui
            synchronized (other.lock) {   // Thread B: da khoa tai khoan nguoi nhan
                if (balance >= amount) {
                    this.balance -= amount;
                    other.balance += amount;
                }
            }
        }
    }
}

// Thread A: chuyen 100 tu TK1 → TK2 (khoa TK1 roi khoa TK2)
// Thread B: chuyen 50  tu TK2 → TK1 (khoa TK2 roi khoa TK1)
// A khoa TK1, doi khoa TK2; B khoa TK2, doi khoa TK1 → DEADLOCK!
```

✅ **Nguyên tắc ngăn deadlock (4 điều kiện, phá bỏ 1):**

```java
// Cach 1: tranh — khóa theo thứ tự cố định (so sánh ID)
public void transferTo(Account other, int amount) {
    Account first = this.id < other.id ? this : other;
    Account second = this.id < other.id ? other : this;

    synchronized (first.lock) {
        synchronized (second.lock) {
            if (balance >= amount) {
                this.balance -= amount;
                other.balance += amount;
            }
        }
    }
}

// Cach 2: cho phep huy — khoa tryLock, neu khong lay duoc thi rollback
public void transferTo(Account other, int amount) {
    while (!this.lock.tryLock()) { Thread.yield(); }
    try {
        while (!other.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
            other.lock.unlock();
            this.lock.unlock();
            Thread.sleep(10); // cho 10ms roi thu lai
            this.lock.lock();
        }
        // lam viec...
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    } finally {
        if (other.lock.isHeldByCurrentThread()) other.lock.unlock();
        if (this.lock.isHeldByCurrentThread())  this.lock.unlock();
    }
}
```

## 4. Atomic classes — CAS thay vì khóa

### 4.1. AtomicInteger / AtomicLong

```java
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();   // count++
    }

    public int get() {
        return count.get();
    }

    // cac phep tinh nguyen tu khac
    public void add(int delta) { count.addAndGet(delta); }
    public void decrement()   { count.decrementAndGet(); }
    public int  getAndSet(int val) { return count.getAndSet(val); }
    public boolean compareAndSet(int expect, int update) {
        return count.compareAndSet(expect, update); // CAS thu cong
    }
}
```

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

```java
// AtomicInteger duoi cap goi:
do {
    int old = value.get();          // 1. doc gia tri hien tai
    int next = old + 1;             // 2. tinh gia tri moi
} while (!value.compareAndSet(old, next)); // 3. thu ghi, neu day thi lap lai
```

**Tầng 3 — Vì sao nhanh hơn synchronized?**

- `synchronized`: thread không có khóa → **BLOCKED** → ngủ → thức dậy khi có khóa → có overhead context-switch rất lớn (microseconds).
- `AtomicInteger` (CAS): thread không đổi được giá trị → lập tức thử lại ngay, **không ngủ**, rất nhanh. Chỉ khi có quá nhiều thread cạnh tranh thì tỷ lệ thử lại (retry loop) tăng → hiệu năng giảm.

❌ **Pitfall:** AtomicInteger không đủ cho các thao tác phức tạp hơn 1 giá trị:

```java
// ✗ atomic nhung van loi!
if (counter.get() < MAX) {
    counter.increment(); // giua 2 dong, thread khac da tang len
}

// ✅ phai synchronized cho ca logic
synchronized (lock) {
    if (count < MAX) count.increment();
}
```

## 5. ReentrantLock — synchronized linh hoạt hơn

```java
import java.util.concurrent.locks.*;

public class CounterWithLock {
    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock(); // phai trong finally, dam bao giai phong khoa
        }
    }

    // Cac tinh nang dac biet cua ReentrantLock:
    public void tryIncrement() {
        if (lock.tryLock()) {     // tra ve false neu dang bi khoa
            try {
                count++;
            } finally {
                lock.unlock();
            }
        } else {
            // xu ly khi khong lay duoc khoa
        }
    }

    public void incrementWithTimeout() throws InterruptedException {
        // cho toi da 5 giay de lay khoa
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }
    }

    public boolean hasQueuedThreads() {
        return lock.hasQueuedThreads(); // co thread nao dang cho khoa nay
    }
}
```

**ReentrantLock vs synchronized:**

| Tiêu chí | synchronized | ReentrantLock |
|----------|-------------|----------------|
| Syntax | Tự động giải phóng khi ra khỏi block | Phải `unlock()` thủ công trong finally |
| Trả lời thời gian chờ | ❌ Không | ✅ `tryLock(5, TimeUnit.SECONDS)` |
| Công bằng (fairness) | Không đảm bảo thứ tự | ✅ `new ReentrantLock(true)` — queue FIFO |
| Đọc trạng thái khóa | ❌ Không | ✅ `hasQueuedThreads()`, `isHeldByCurrentThread()` |
| Lock có thể reentrant | ✅ | ✅ |

❌ **Pitfall:** Quên `unlock()` trong `finally` → nếu có exception giữa chừng, khóa không bao giờ được giải phóng → chương trình treo vĩnh viễn.

✅ **Quy tắc:** Dùng `synchronized` khi code đơn giản, không cần timeout hay fairness. Chuyển sang `ReentrantLock` khi cần timeout, kiểm tra trạng thái khóa, hoặc cần fairness nghiêm ngặt.

## 6. ReadWriteLock — tối ưu cho đọc nhiều ghi ít

```java
import java.util.concurrent.locks.*;

public class CacheDemo {
    private final Map<String, String> cache = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public String get(String key) {
        rwLock.readLock().lock();
        try {
            return cache.get(key); // nhieu thread doc cung luc duoc
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void put(String key, String value) {
        rwLock.writeLock().lock();
        try {
            cache.put(key, value);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
```

### 6.1. StampedLock (Java 8+) — đọc lạc quan

`StampedLock` cho phép đọc không khóa trước (optimistic read), kiểm tra sau:

```java
import java.util.concurrent.locks.StampedLock;

public class Point {
    private int x, y;
    private final StampedLock sl = new StampedLock();

    public void move(int dx, int dy) {
        long stamp = sl.writeLock();  // khoa ghi (exclusive)
        try {
            x += dx; y += dy;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    public double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead(); // doc khong khoa
        int lx = x; int ly = y;             // doc nhanh
        if (!sl.validate(stamp)) {          // kiem tra co ai ghi khong
            stamp = sl.readLock();            // fall back sang read lock
            try {
                lx = x; ly = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(lx * lx + ly * ly);
    }
}
```

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
