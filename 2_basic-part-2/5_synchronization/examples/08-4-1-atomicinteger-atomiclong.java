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
