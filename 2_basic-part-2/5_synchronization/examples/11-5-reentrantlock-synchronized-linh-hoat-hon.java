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
