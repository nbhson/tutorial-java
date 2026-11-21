private String lock = "KHOA";          // ✗
public void doSomething() {
    synchronized (lock) { /* ... */ }  // neu ai do gan lock = "KHOA KHAC" → khoa vo hieu hoa
}
private final Object lock = new Object(); // ✅ final, khong ai doi duoc
