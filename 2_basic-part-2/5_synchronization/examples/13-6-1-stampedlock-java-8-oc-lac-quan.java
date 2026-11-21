import java.util.concurrent.locks.StampedLock;

public class Point {
    private int x, y;
    private final StampedLock sl = new StampedLock();

    public void move(int dx, int dy) {
        long stamp = sl.writeLock();  // khoa ghi (exclusive)
        try {
            x += dx; y += dy;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    public double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead(); // doc khong khoa
        int lx = x; int ly = y;             // doc nhanh
        if (!sl.validate(stamp)) {          // kiem tra co ai ghi khong
            stamp = sl.readLock();            // fall back sang read lock
            try {
                lx = x; ly = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(lx * lx + ly * ly);
    }
}
