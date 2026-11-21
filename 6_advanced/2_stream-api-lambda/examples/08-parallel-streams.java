// Sequential stream (1 thread)
long count1 = numbers.stream()
    .filter(n -> n % 2 == 0)
    .count();

// Parallel stream (nhiều threads)
long count2 = numbers.parallelStream()
    .filter(n -> n % 2 == 0)
    .count();

// Khi nào dùng parallel?
// ✅ Dataset lớn (hàng trăm nghìn+), CPU-bound tasks
// ❌ Dataset nhỏ, I/O-bound, cần ordering
