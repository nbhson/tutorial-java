// ❌ Eager initialization
private final ExpensiveObject expensive = new ExpensiveObject();

public ExpensiveObject getObject() {
    return expensive;  // Luôn tạo dù có dùng hay không
}

// ✅ Lazy initialization
private volatile ExpensiveObject expensive;  // volatile for thread-safety

public ExpensiveObject getObject() {
    if (expensive == null) {
        synchronized (this) {
            if (expensive == null) {
                expensive = new ExpensiveObject();
            }
        }
    }
    return expensive;
}

// ✅ LazyHolder pattern (thread-safe, lazy)
private static class Holder {
    static final ExpensiveObject INSTANCE = new ExpensiveObject();
}

public static ExpensiveObject getInstance() {
    return Holder.INSTANCE;
}
