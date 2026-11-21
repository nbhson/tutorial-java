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
