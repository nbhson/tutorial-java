// ✗ code loi kinh dien
public class Counter {
    private int count = 0;

    public void increment() {
        count++; // KHONG nguyen tu! = doc → +1 → ghi lai (3 buoc)
    }

    public int get() { return count; }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) counter.increment();
        });

        t1.start(); t2.start();
        t1.join();  t2.join();

        System.out.println(counter.get()); // khong phai 20000! vi du: 17342
    }
}
