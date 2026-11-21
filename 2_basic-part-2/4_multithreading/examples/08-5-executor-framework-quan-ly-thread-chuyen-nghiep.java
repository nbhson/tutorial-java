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
