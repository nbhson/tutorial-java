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
