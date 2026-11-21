Optional<String> opt = Optional.of("Java");

// isPresent / isEmpty
if (opt.isPresent()) { System.out.println("Có giá trị"); }
if (opt.isEmpty())  { System.out.println("Không có giá trị"); }

// get - Lấy giá trị (⚠️ Không nên dùng trực tiếp!)
String value = opt.get();  // NoSuchElementException nếu empty

// ============ CÁCH AN TOÀN ============

// orElse: Giá trị mặc định
String result = opt.orElse("Default");

// orElseGet: Giá trị mặc định (lazy - chỉ chạy khi cần)
String result2 = opt.orElseGet(() -> "Computed Default");

// orElseThrow: Ném exception
String result3 = opt.orElseThrow(() -> new RuntimeException("Không tìm thấy!"));

// ifPresent: Xử lý khi có giá trị
opt.ifPresent(v -> System.out.println("Giá trị: " + v));

// ifPresentOrElse (Java 9+): Xử lý cả 2 trường hợp
opt.ifPresentOrElse(
    value -> System.out.println("Có: " + value),
    () -> System.out.println("Không có giá trị")
);

// map: Chuyển đổi giá trị bên trong
Optional<Integer> length = opt.map(String::length);  // Optional[4]

// filter: Lọc giá trị
Optional<String> filtered = opt.filter(s -> s.length() > 2);

// or (Java 9+): Fallback với Optional khác
Optional<String> fallback = opt.or(() -> Optional.of("Default"));

// orElseGet với side effect
String result4 = opt.orElseGet(() -> {
    System.out.println("Computing default...");
    return "Default Value";
});
