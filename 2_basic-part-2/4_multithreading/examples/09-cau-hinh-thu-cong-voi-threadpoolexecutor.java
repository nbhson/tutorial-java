// ✅ trong production nen tu cau hinh thay vi dung Executors factory
ExecutorService pool = new ThreadPoolExecutor(
    4,                        // corePoolSize: so thread giu lai
    16,                       // maximumPoolSize: gioi han toi da
    60L, TimeUnit.SECONDS,    // thread du thua ton tai 60s roi bi gom
    new ArrayBlockingQueue<>(100),       // hang doi gioi han 100 viec
    new ThreadPoolExecutor.AbortPolicy() // queue day → nem RejectedExecutionException
);
