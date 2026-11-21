// ❌ Không dùng Optional cho fields
public class User {
    private Optional<String> middleName;  // ❌ Không làm thế này!
    // Dùng null hoặc empty string
}

// ❌ Không dùng Optional cho method parameters
public void processUser(Optional<User> user) { ... }  // ❌
public void processUser(User user) { ... }            // ✅

// ❌ Không gọi get() trực tiếp mà không check
Optional<String> opt = ...;
String value = opt.get();  // ⚠️ NoSuchElementException!

// ❌ Không dùng Optional cho primitives
Optional<int> bad;  // ❌ Compile error
OptionalInt good;   // ✅ Dùng specialized versions

// ❌ Không dùng Optional cho collections
Optional<List<String>> list;  // ❌ Không cần thiết
List<String> list2 = List.of();  // ✅ Empty list thay thế
