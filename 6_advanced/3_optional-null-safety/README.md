# Optional và Null Safety

## Vấn đề NullPointerException

**NullPointerException (NPE)** là một trong những lỗi phổ biến nhất trong Java. Tony Hoare, người phát minh null reference, gọi đây là ["billion-dollar mistake"](https://en.wikipedia.org/wiki/Tony_Hoare#Apologies_and_retractions).

### Vấn đề với null

```java
// ❌ Code cũ dễ bị NPE
public String getCity(User user) {
    return user.getAddress().getCity().toUpperCase();
    // NPE nếu user = null
    // NPE nếu address = null
    // NPE nếu city = null
}

// ✅ Cách cũ: Kiểm tra null thủ công (verbose)
public String getCity(User user) {
    if (user == null) {
        return "UNKNOWN";
    }
    Address address = user.getAddress();
    if (address == null) {
        return "UNKNOWN";
    }
    String city = address.getCity();
    return city != null ? city.toUpperCase() : "UNKNOWN";
}
```

## Optional<T> là gì?

**Optional<T>** là một container có thể chứa hoặc không chứa một giá trị. Nó được giới thiệu từ Java 8 để giải quyết vấn đề null checks.

> **Yêu cầu**: Java 8+ (nên dùng Java 17+ với các cải tiến mới)

### Tạo Optional

```java
import java.util.Optional;

// 1. Optional.of - giá trị không được null (NPE nếu null)
Optional<String> opt1 = Optional.of("Hello");

// 2. Optional.empty - không có giá trị
Optional<String> opt2 = Optional.empty();

// 3. Optional.ofNullable - chấp nhận cả null
String maybeNull = null;
Optional<String> opt3 = Optional.ofNullable(maybeNull);  // Optional.empty
```

### Các Methods cơ bản

```java
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
```

## Optional Chaining

### Nối Optional

```java
// Ví dụ: Tìm user theo email, rồi lấy city
public record Address(String city) {}
public record User(String name, Address address) {}

public class OptionalChaining {

    public static String getUserCity(User user) {
        return Optional.ofNullable(user)
            .map(User::address)      // Optional<Address>
            .map(Address::city)      // Optional<String>
            .map(String::toUpperCase)
            .orElse("UNKNOWN");
    }
}
```

### Optional trong Streams

```java
List<Optional<String>> optionals = List.of(
    Optional.of("Java"),
    Optional.empty(),
    Optional.of("Spring")
);

// flatMap để "làm phẳng" optionals
List<String> values = optionals.stream()
    .flatMap(Optional::stream)  // Java 9+
    .toList();  // [Java, Spring]

// Tìm phần tử với Stream + Optional
Optional<User> admin = users.stream()
    .filter(u -> u.role().equals("ADMIN"))
    .findFirst();

admin.ifPresentOrElse(
    u -> System.out.println("Found: " + u.name()),
    () -> System.out.println("Not found")
);
```

## Ví dụ thực tế

### Repository với Optional

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndStatus(String username, String status);
}

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ============ Tìm user theo ID ============
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    // ============ Tìm hoặc tạo mới ============
    public User findOrCreateUser(String email) {
        return userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                return userRepository.save(newUser);
            });
    }

    // ============ Chain operations ============
    public String getUserCity(Long userId) {
        return userRepository.findById(userId)
            .map(User::getAddress)
            .map(Address::city)
            .map(String::toUpperCase)
            .orElse("UNKNOWN");
    }

    // ============ Xử lý nhiều Optional ============
    public String getContactInfo(Long userId) {
        return userRepository.findById(userId)
            .flatMap(this::findPrimaryContact)  // Optional<Contact>
            .map(Contact::getPhone)
            .orElse("No contact info");
    }

    private Optional<Contact> findPrimaryContact(User user) {
        return user.getContacts().stream()
            .filter(Contact::isPrimary)
            .findFirst();
    }
}
```

## Best Practices

### ✅ Nên làm

```java
// 1. Dùng Optional làm return type
public Optional<User> findUserByEmail(String email) { ... }

// 2. Dùng orElse/orElseGet thay vì get()
String city = user.flatMap(User::getAddress)
                  .map(Address::getCity)
                  .orElse("Unknown");

// 3. Dùng ifPresent thay vì isPresent + get
optionalUser.ifPresent(this::sendWelcomeEmail);

// 4. orElseThrow cho bắt buộc
User user = userRepository.findById(id)
    .orElseThrow(() -> new UserNotFoundException(id));

// 5. Dùng map/flatMap để chain operations
Optional<String> result = Optional.ofNullable(input)
    .map(String::trim)
    .filter(s -> !s.isEmpty())
    .map(String::toUpperCase);
```

### ❌ Không nên làm

```java
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
```

## So sánh với các kỹ thuật khác

| Kỹ thuật | Ưu điểm | Nhược điểm |
|----------|---------|-----------|
| `null` check | Nhanh, đơn giản | Dễ quên, verbose |
| `Optional` | Type-safe, chainable | Overhead nhỏ |
| `@NonNull` annotation | Compile-time warning | Cần IDE/library support |
| Objects.requireNonNull() | Fail-fast | Chỉ xử lý parameter |

## Tiện ích khác

```java
// Objects.requireNonNull
public void process(String input) {
    Objects.requireNonNull(input, "Input không được null");
    // Throws NullPointerException với message
}

// StringUtils (Apache Commons)
StringUtils.isBlank(str);   // null, "", "  " → true
StringUtils.defaultString(null, "default");

// Java 25: Scoped Values (alternative cho ThreadLocal)
// Không còn null issues trong multithreaded contexts
```

## Tài liệu tham khảo

- [Optional Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Optional.html)
- [Baeldung Java Optional](https://www.baeldung.com/java-optional)
- [Effective Java - Item 55](https://www.oreilly.com/library/view/effective-java-3rd/9780134686097/)

---

**Tiếp theo**: [4. Record và Sealed Classes](../4_record-sealed/)
