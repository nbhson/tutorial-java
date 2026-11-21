// ❌ Cached objects không được clean up
Map<String, ExpensiveObject> cache = new HashMap<>();

public ExpensiveObject getCached(String key) {
    return cache.computeIfAbsent(key, k -> new ExpensiveObject());
    // ⚠️ Cache grow forever, never evicted!
}

// ✅ Dùng WeakHashMap cho caches
Map<String, ExpensiveObject> cache = new WeakHashMap<>();
// Objects bị removed khi không còn referenced ở đâu khác

// ✅ Dùng Caffeine (best cache library)
Cache<String, ExpensiveObject> cache = Caffeine.newBuilder()
    .maximumSize(1000)
    .expireAfterWrite(Duration.ofMinutes(10))
    .build();

// ✅ Dùng LRU cache
Map<String, ExpensiveObject> cache = new LinkedHashMap<>(16, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_SIZE;
    }
};
