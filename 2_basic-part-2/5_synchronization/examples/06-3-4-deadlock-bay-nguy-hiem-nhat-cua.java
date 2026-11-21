public class Account {
    private int balance;
    private final Object lock = new Object();

    public void transferTo(Account other, int amount) {
        synchronized (this.lock) {       // Thread A: khoa tai khoan nguoi gui
            synchronized (other.lock) {   // Thread B: da khoa tai khoan nguoi nhan
                if (balance >= amount) {
                    this.balance -= amount;
                    other.balance += amount;
                }
            }
        }
    }
}

// Thread A: chuyen 100 tu TK1 → TK2 (khoa TK1 roi khoa TK2)
// Thread B: chuyen 50  tu TK2 → TK1 (khoa TK2 roi khoa TK1)
// A khoa TK1, doi khoa TK2; B khoa TK2, doi khoa TK1 → DEADLOCK!
