# Performance và Garbage Collection

## JVM Architecture - Tổng quan

Mục này mở nắp "cỗ máy" JVM để xem bên trong có gì: từ nạp class, chạy bytecode, đến vùng nhớ — nền tảng cần có trước khi bàn tối ưu.

Trước khi tối ưu performance, cần hiểu JVM hoạt động như thế nào. Hãy hình dung JVM như một **nhà hàng lớn**: Class Loader là bộ phận "nhận đơn món", Execution Engine là "bếp" (JIT là bếp trưởng tinh nhuệ học nhanh món hot), Runtime Memory là "kho nguyên liệu" — Heap là kho lớn, Stack là bàn làm việc của từng đầu bếp.

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

Mục này chi tiết hóa "kho nguyên liệu": từng vùng nhớ dùng để làm gì, dung lượng ra sao, và ai dọn dẹp.

### Heap vs Stack

> 📄 **Code ví dụ:** [`examples/01-heap-vs-stack.java`](examples/01-heap-vs-stack.java)

| Memory Area | Purpose | Size | GC? |
|-------------|---------|------|-----|
| **Heap** | Objects, arrays | Configurable (-Xms, -Xmx) | ✅ Yes |
| **Stack** | Method frames, local vars | Small per thread | ❌ Auto clear |
| **Metaspace** | Class definitions | Dynamic | ✅ Yes (Java 8+) |
| **Code Cache** | JIT compiled code | Fixed | ✅ Yes |

> **Cách đọc bảng này:** mỗi dòng là một vùng nhớ trong JVM — cột "GC?" là quan trọng nhất: vùng nào có GC thì rõ ràng, vùng nào "Auto clear" thì tự dọn theo vòng đời method; khi chỉnh `-Xmx` bạn đang chỉnh đúng dòng đầu tiên.

## Garbage Collection

Mục này nói về "đội dọn dẹp" của JVM: họ làm việc thế nào, có mấy loại đội, và chọn đội nào cho ứng dụng của bạn. Hãy hình dung GC như **đội dọn phòng khách sạn**: quét nhanh phòng nhỏ nhiều lần trong ngày (Minor GC), còn dọn sâu cả tầng thì ít nhưng chậm hơn (Major GC).

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

> **Cách đọc bảng này:** chọn theo cột "Phù hợp" trước — đó là câu trả lời cho "ứng dụng của mình thuộc dạng nào"; G1 là lựa chọn an toàn mặc định, chỉ tính đến ZGC/Shenandoah khi bạn thật sự bị chặn bởi độ trễ.

### Cấu hình G1 GC (Recommended)

> 📄 **Code ví dụ:** [`examples/02-cau-hinh-g1-gc-recommended.sh`](examples/02-cau-hinh-g1-gc-recommended.sh)

### ZGC (Ultra-low latency)

> 📄 **Code ví dụ:** [`examples/03-zgc-ultra-low-latency.sh`](examples/03-zgc-ultra-low-latency.sh)

## Performance Best Practices

Mục này là "sáu bài tiết kiệm" cho code chạy nhanh hơn: mỗi mục một sai lầm phổ biến kèm cách làm đúng.

### 1. Object Creation

> 📄 **Code ví dụ:** [`examples/04-1-object-creation.java`](examples/04-1-object-creation.java)

### 2. String Operations

> 📄 **Code ví dụ:** [`examples/05-2-string-operations.java`](examples/05-2-string-operations.java)

### 3. Collection Selection

> 📄 **Code ví dụ:** [`examples/06-3-collection-selection.java`](examples/06-3-collection-selection.java)

### 4. Streams Performance

> 📄 **Code ví dụ:** [`examples/07-4-streams-performance.java`](examples/07-4-streams-performance.java)

### 5. Avoiding Boxing/Unboxing

> 📄 **Code ví dụ:** [`examples/08-5-avoiding-boxing-unboxing.java`](examples/08-5-avoiding-boxing-unboxing.java)

### 6. Lazy Initialization

> 📄 **Code ví dụ:** [`examples/09-6-lazy-initialization.java`](examples/09-6-lazy-initialization.java)

## Profiling Tools

Mục này giới thiệu "máy đo nhịp tim" của ứng dụng: đo được thì sửa đúng chỗ, đo không được thì tối ưu bằng cảm tính.

### Built-in Tools

> 📄 **Code ví dụ:** [`examples/10-built-in-tools.sh`](examples/10-built-in-tools.sh)

### Flight Recorder (JDK Mission Control)

> 📄 **Code ví dụ:** [`examples/11-flight-recorder-jdk-mission-control.sh`](examples/11-flight-recorder-jdk-mission-control.sh)

## Common Performance Issues

Mục này là "hồ sơ án tích" hai tội danh kinh điển: rò rỉ bộ nhớ và cấu hình pool sai — đều nằm ngoài tầm mắt nhưng giết ứng dụng từ từ.

### Memory Leaks

> 📄 **Code ví dụ:** [`examples/12-memory-leaks.java`](examples/12-memory-leaks.java)

### Thread Pool Misconfiguration

> 📄 **Code ví dụ:** [`examples/13-thread-pool-misconfiguration.java`](examples/13-thread-pool-misconfiguration.java)

## Quick Reference - JVM Flags

Mục này là "tờ Cheat sheet" cầm tay: các cờ JVM hay dùng để tra nhanh khi cần chỉnh cấu hình.

> 📄 **Code ví dụ:** [`examples/14-quick-reference-jvm-flags.sh`](examples/14-quick-reference-jvm-flags.sh)

> **Tóm gọn 1 câu:** Tối ưu JVM là "hiểu nhà hàng rồi mới chỉnh bếp" — biết vùng nhớ, chọn đúng đội GC, và đo bằng profiler trước khi sửa.

## Tài liệu tham khảo

- [JVM Performance Tuning Guide](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/performance-enhancements.html)
- [G1 Garbage Collector](https://www.oracle.com/java/technologies/javase/gc-tuning-1-paradigm.html)
- [Baeldung JVM Performance](https://www.baeldung.com/jvm-performance-tuning)
- [Java Performance by Scott Oaks](https://www.oreilly.com/library/view/java-performance-2nd/9781492056113/)

---

**Về trang chủ**: [Java Nâng Cao](../README.md)
