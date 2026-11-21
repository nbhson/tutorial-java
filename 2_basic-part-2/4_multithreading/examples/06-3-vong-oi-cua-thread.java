// ✅ huy thread an toan bang co
volatile boolean running = true; // volatile: cac thread thay thay doi ngay

public void run() {
    while (running) {
        // lam viec...
    }
}

public void stopTask() { running = false; }

// hoac voi interrupt:
public void run() {
    while (!Thread.currentThread().isInterrupted()) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // giu trang thai interrupt
            return; // thoat
        }
    }
}
