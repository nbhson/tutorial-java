# Performance và Garbage Collection

## JVM Architecture - Tổng quan

Trước khi tối ưu performance, cần hiểu JVM hoạt động như thế nào.

```
┌──────────────────────────────────────────────────────┐
│                      JVM Process                      │
│  ┌─────────────────────────────────────────────────┐  │
│  │                  Class Loader                    │  │
│  │  (Loading → Linking → Initialization)            │  │
│  └─────────────────────────────────────────────────┘  │
│  ┌─────────────────────────────────────────────────┐  │
│  │                  Execution Engine                │  │
│  │  ┌─────────────┐  ┌────────────────────────────┐ │  │
│  │  │  Interpreter│  │    JIT Compiler            │ │  │
│  │  │  (bytecode) │  │    (compile to native)     │ │  │
│  │  └─────────────┘  └────────────────────────────┘ │  │
│  └─────────────────────────────────────────────────┘  │
│  ┌─────────────────────────────────────────────────┐  │
│  │                    Runtime Memory               │  │
│  │  ┌──────────┐ ┌──────────┐ ┌─────────────────┐  │  │
│  │  │  Heap    │ │  Stack   │ │  MetaSpace/Code  │  │  │
│  │  │  (Objects│ │ (threads │ │  (class definitions)│ │  │
│  │  │  live)   │ │  frames) │ │                  │  │  │
│  │  └──────────┘ └──────────┘ └─────────────────┘  │  │
│  └─────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────┘
```

## Memory Areas

### Heap vs Stack

```java
public class MemoryDemo {
    
    // Instance variables - stored in HEAP
    private String name;    // Reference (4/8 bytes) → points to object in Heap
    
    public void calculate() {
        // Local variables - stored in STACK
        int x = 10;         // Primitive: value stored directly
        String s = "Hello"; // Reference: points to String in Heap
        
        // Object created inside method → stored in Heap
        List<String> list = new ArrayList<>();
        list.add("Item");
    }
}
```

| Memory Area | Purpose | Size | GC? |
|-------------|---------|------|-----|
| **Heap** | Objects, arrays | Configurable (-Xms, -Xmx) | ✅ Yes |
| **Stack** | Method frames, local vars | Small per thread | ❌ Auto clear |
| **Metaspace** | Class definitions | Dynamic | ✅ Yes (Java 8+) |
| **Code Cache** | JIT compiled code | Fixed | ✅ Yes |

## Garbage Collection

### GC Algorithm cơ bản

```
Young Generation (Eden + Survivor)     Old Generation
┌────────────────────────────┐
│  ┌────┐  ┌────┐             │
│  │Eden│  │S0  │ S1│         │    ┌────────────────┐
│  └────┘  └────┘  └──┘      │    │                │
│     │        │             │    │                │
│     ▼        ▼             │    │                │
│  [Minor GC]                │    │   [Major GC]   │
│  Quick, frequent            │    │   Slow, rare   │
└────────────────────────────┘    └────────────────┘
```

### Các GC Algorithms

| Algorithm | Mô tả | Phù hợp |
|-----------|-------|---------|
| **Serial GC** | Single thread, stop-the-world | Single CPU, small apps |
| **Parallel GC** | Multi-thread, throughput focus | Throughput apps |
| **CMS** | Concurrent, low pause | Low-latency (deprecated) |
| **G1 GC** | Region-based, balanced | Default từ Java 9 |
| **ZGC** | Ultra-low latency (<1ms) | Ultra-low latency |
| **Shenandoah** | Concurrent, same as ZGC | Red Hat environments |

### Cấu hình G1 GC (Recommended)

```bash
# Cấu hình JVM cho production
java -Xms4g -Xmx4g \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -XX:+ParallelRefProcEnabled \
     -XX:G1HeapRegionSize=4m \
     -XX:InitiatingHeapOccupancyPercent=45 \
     -jar myapp.jar
```

### ZGC (Ultra-low latency)

```bash
# Java 17+ với ZGC
java -Xms8g -Xmx8g \
     -XX:+UseZGC \
     -XX:+ZGenerational \
     -jar myapp.jar
```

## Performance Best Practices

### 1. Object Creation

```java
// ❌ Tạo object không cần thiết
for (String s : list) {
    String upper = new String(s.toUpperCase());  // Unnecessary new object
    process(upper);
}

// ✅ Tái sử dụng/Caching
String upper = s.toUpperCase();  // Không tạo String mới
```

### 2. String Operations

```java
// ❌ String concatenation trong loop
String result = "";
for (String s : list) {
    result += s + ",";  // Tạo nhiều String objects
}

// ✅ StringBuilder
StringBuilder sb = new StringBuilder();
for (String s : list) {
    if (sb.length() > 0) sb.append(",");
    sb.append(s);
}
String result = sb.toString();

// ✅ Java 9+: interned strings được optimize
// Nhưng vẫn nên dùng StringBuilder cho loop dài

// ✅ String.join (Java 8+)
String result = String.join(",", list);

// ✅ Collectors.joining (Stream)
String result = list.stream().collect(Collectors.joining(","));
```

### 3. Collection Selection

```java
// Chọn đúng Collection cho use case

// ArrayList: Default, fast iteration, slow insert/remove ở giữa
// LinkedList: Fast insert/remove, slow random access

// HashMap: O(1) average cho get/put, không ordered
// TreeMap: O(log n), sorted by key
// LinkedHashMap: O(1), insertion order

// HashSet vs EnumSet
// EnumSet rất nhanh, chỉ dùng cho Enums
Set<Status> statuses = EnumSet.noneOf(Status.class);

// Set.of() vs new HashSet<>() (Java 9+)
Set<String> set1 = Set.of("a", "b", "c");  // Immutable, faster
Set<String> set2 = new HashSet<>(List.of("a", "b", "c"));  // Mutable
```

### 4. Streams Performance

```java
// ❌ Sequential stream cho CPU-intensive
list.stream()
    .map(HeavyCalculation::process)
    .collect(toList());

// ✅ Parallel stream cho large datasets
list.parallelStream()
    .map(HeavyCalculation::process)
    .collect(toList());

// ✅ Hoặc
list.stream()
    .parallel()
    .map(HeavyCalculation::process)
    .collect(toList());

// ⚠️ Chỉ dùng parallel khi:
// - Dataset lớn (100k+ elements)
// - Operations CPU-intensive
// - Không cần order preservation
```

### 5. Avoiding Boxing/Unboxing

```java
// ❌ Primitive boxing trong loop
int sum = 0;
for (Integer i : intList) {  // Boxing: int → Integer
    sum += i;  // Unboxing: Integer → int
}

// ✅ Primitive streams
int sum = intList.stream()
    .mapToInt(Integer::intValue)  // Giữ nguyên primitive
    .sum();

// ✅ Hoặc sum trực tiếp
long sum = IntStream.range(1, 1000).sum();
```

### 6. Lazy Initialization

```java
// ❌ Eager initialization
private final ExpensiveObject expensive = new ExpensiveObject();

public ExpensiveObject getObject() {
    return expensive;  // Luôn tạo dù có dùng hay không
}

// ✅ Lazy initialization
private volatile ExpensiveObject expensive;  // volatile for thread-safety

public ExpensiveObject getObject() {
    if (expensive == null) {
        synchronized (this) {
            if (expensive == null) {
                expensive = new ExpensiveObject();
            }
        }
    }
    return expensive;
}

// ✅ LazyHolder pattern (thread-safe, lazy)
private static class Holder {
    static final ExpensiveObject INSTANCE = new ExpensiveObject();
}

public static ExpensiveObject getInstance() {
    return Holder.INSTANCE;
}
```

## Profiling Tools

### Built-in Tools

```bash
# JConsole - GUI monitoring
jconsole

# VisualVM - Profiler (cần JDK)
jvisualvm

# JShell - Interactive shell
jshell

# Print GC details
java -Xlog:gc*:file=gc.log:time:filecount=5,filesize=10m myapp.jar

# Analyze GC logs
jstat -gcutil <pid> 1000  # GC statistics every 1s
```

### Flight Recorder (JDK Mission Control)

```bash
# Start with JFR enabled
java -XX:StartFlightRecording=dumponexit=true,filename=recording.jfr \
     -jar myapp.jar

# Analyze
jfr print --events CPULoad,GC ./recording.jfr
```

## Common Performance Issues

### Memory Leaks

```java
// ❌ Cached objects không được clean up
Map<String, ExpensiveObject> cache = new HashMap<>();

public ExpensiveObject getCached(String key) {
    return cache.computeIfAbsent(key, k -> new ExpensiveObject());
    // ⚠️ Cache grow forever, never evicted!
}

// ✅ Dùng WeakHashMap cho caches
Map<String, ExpensiveObject> cache = new WeakHashMap<>();
// Objects bị removed khi không còn referenced ở đâu khác

// ✅ Dùng Caffeine (best cache library)
Cache<String, ExpensiveObject> cache = Caffeine.newBuilder()
    .maximumSize(1000)
    .expireAfterWrite(Duration.ofMinutes(10))
    .build();

// ✅ Dùng LRU cache
Map<String, ExpensiveObject> cache = new LinkedHashMap<>(16, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_SIZE;
    }
};
```

### Thread Pool Misconfiguration

```java
// ❌ Tạo thread pool không có bounds
ExecutorService executor = Executors.newCachedThreadPool();
// ⚠️ Có thể tạo vô số threads → OOM!

// ✅ Fixed thread pool
ExecutorService executor = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors() * 2
);

// ✅ Virtual Threads (Java 21+)
try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
    // Tạo hàng triệu virtual threads!
    for (int i = 0; i < 1_000_000; i++) {
        executor.submit(() -> process(task));
    }
}
```

## Quick Reference - JVM Flags

```bash
# Memory
-Xms512m              # Initial heap size
-Xmx4g                # Maximum heap size
-Xss2m                # Stack size per thread
-XX:MetaspaceSize=256m # Metaspace size
-XX:MaxMetaspaceSize=512m

# GC
-XX:+UseG1GC          # Use G1 garbage collector
-XX:+UseZGC           # Use ZGC (Java 11+)
-XX:+UseSerialGC      # Use Serial GC
-XX:MaxGCPauseMillis=200  # Target max GC pause

# JIT
-XX:+TieredCompilation    # Faster startup, better long-run perf
-XX:CompileThreshold=1000 # Methods compiled after 1000 invocations

# Logging
-Xlog:gc*:file=gc.log    # GC logging
-XX:+PrintGCDetails       # Detailed GC logs (legacy)
```

## Tài liệu tham khảo

- [JVM Performance Tuning Guide](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/performance-enhancements.html)
- [G1 Garbage Collector](https://www.oracle.com/java/technologies/javase/gc-tuning-1-paradigm.html)
- [Baeldung JVM Performance](https://www.baeldung.com/jvm-performance-tuning)
- [Java Performance by Scott Oaks](https://www.oreilly.com/library/view/java-performance-2nd/9781492056113/)

---

**Về trang chủ**: [Java Nâng Cao](../README.md)
