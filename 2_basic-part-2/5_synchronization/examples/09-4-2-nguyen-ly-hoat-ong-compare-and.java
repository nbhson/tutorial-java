// AtomicInteger duoi cap goi:
do {
    int old = value.get();          // 1. doc gia tri hien tai
    int next = old + 1;             // 2. tinh gia tri moi
} while (!value.compareAndSet(old, next)); // 3. thu ghi, neu day thi lap lai
