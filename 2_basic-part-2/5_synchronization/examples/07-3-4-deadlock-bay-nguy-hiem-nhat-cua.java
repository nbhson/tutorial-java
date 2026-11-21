// Cach 1: tranh — khóa theo thứ tự cố định (so sánh ID)
public void transferTo(Account other, int amount) {
    Account first = this.id < other.id ? this : other;
    Account second = this.id < other.id ? other : this;

    synchronized (first.lock) {
        synchronized (second.lock) {
            if (balance >= amount) {
                this.balance -= amount;
                other.balance += amount;
            }
        }
    }
}

// Cach 2: cho phep huy — khoa tryLock, neu khong lay duoc thi rollback
public void transferTo(Account other, int amount) {
    while (!this.lock.tryLock()) { Thread.yield(); }
    try {
        while (!other.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
            other.lock.unlock();
            this.lock.unlock();
            Thread.sleep(10); // cho 10ms roi thu lai
            this.lock.lock();
        }
        // lam viec...
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    } finally {
        if (other.lock.isHeldByCurrentThread()) other.lock.unlock();
        if (this.lock.isHeldByCurrentThread())  this.lock.unlock();
    }
}
