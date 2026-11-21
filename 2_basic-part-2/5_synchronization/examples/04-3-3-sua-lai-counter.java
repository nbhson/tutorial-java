public class SafeCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int get() { // doc cung phai synchronized!
        return count;
    }
}

// Dang static: bien dem dung chung cho moi instance
public class GlobalCounter {
    private static int count = 0;

    public static void increment() {
        synchronized (GlobalCounter.class) {
            count++;
        }
    }
}
