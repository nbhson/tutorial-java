// Chọn đúng Collection cho use case

// ArrayList: Default, fast iteration, slow insert/remove ở giữa
// LinkedList: Fast insert/remove, slow random access

// HashMap: O(1) average cho get/put, không ordered
// TreeMap: O(log n), sorted by key
// LinkedHashMap: O(1), insertion order

// HashSet vs EnumSet
// EnumSet rất nhanh, chỉ dùng cho Enums
Set<Status> statuses = EnumSet.noneOf(Status.class);

// Set.of() vs new HashSet<>() (Java 9+)
Set<String> set1 = Set.of("a", "b", "c");  // Immutable, faster
Set<String> set2 = new HashSet<>(List.of("a", "b", "c"));  // Mutable
