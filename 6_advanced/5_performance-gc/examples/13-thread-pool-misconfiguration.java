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
