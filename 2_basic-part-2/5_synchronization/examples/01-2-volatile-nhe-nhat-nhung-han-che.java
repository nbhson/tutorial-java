public class TaskRunner {
    // co volatile: moi lan doc deu lay gia tri moi nhat tu bo nho chinh
    private volatile boolean running = true;

    public void run() {
        while (running) {
            // lam viec...
        }
        System.out.println("Da thoat vong lap"); // co volatile thi moi duoc chay
    }

    public void stop() {
        running = false; // thread khac ghi, thread run nhin thay ngay
    }
}
