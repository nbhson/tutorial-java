# Record, Sealed Classes và Pattern Matching

## Giới thiệu

Java 16-21 giới thiệu các tính năng ngôn ngữ hiện đại giúp code ngắn gọn, an toàn và dễ đọc hơn: **Records** (Java 16), **Sealed Classes** (Java 17), và **Pattern Matching** (Java 16-21).

> **Yêu cầu**: Java 17+ (LTS). Một số tính năng cần Java 21.

---

## 1. Record (Java 16+)

**Record** là một cách ngắn gọn để tạo **immutable data class** - class chỉ chứa dữ liệu, tự động generate constructor, getters, equals, hashCode, toString.

### So sánh Class truyền thống vs Record

```java
// ============ TRƯỚC: Traditional Class (30+ dòng code) ============
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{x=" + x + ", y=" + y + "}";
    }
}

// ============ SAU: Record (1 dòng!) ============
public record Point(int x, int y) {}
// Tự động có: constructor, x(), y(), equals(), hashCode(), toString()
```

### Sử dụng Record

```java
public record Task(
    Long id,
    String title,
    String description,
    boolean completed,
    LocalDateTime createdAt
) {
    // Compact Constructor - validation
    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title không được để trống");
        }
    }

    // Optional: Custom methods
    public boolean isDone() {
        return completed;
    }

    // Optional: Static factory methods
    public static Task createNew(String title, String description) {
        return new Task(null, title, description, false, LocalDateTime.now());
    }

    // Optional: "With" methods - tạo copy với giá trị mới
    public Task withCompleted(boolean newCompleted) {
        return new Task(id, title, description, newCompleted, createdAt);
    }
}

// Sử dụng
Task task = new Task(1L, "Học Java", "Học Record", false, LocalDateTime.now());

// Accessor methods (không có "get" prefix)
String title = task.title();     // Không phải getTitle()
boolean done = task.completed(); // Không phải isCompleted()

// Record là immutable - không có setters!
// task.setTitle("New");  // ❌ Compile error

// Tạo copy với thay đổi
Task completedTask = task.withCompleted(true);
```

### Record trong Spring Boot (DTOs)

```java
// Request DTO
public record CreateUserRequest(
    @NotBlank String name,
    @Email String email,
    @Size(min = 8) String password
) {}

// Response DTO
public record UserResponse(
    Long id,
    String name,
    String email,
    String createdAt
) {
    // Static factory từ Entity
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt().toString()
        );
    }
}

// Controller
@PostMapping("/api/users")
public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
    User user = userService.create(request);
    return ResponseEntity.status(201).body(UserResponse.from(user));
}
```

### Compact Constructor với validation

```java
public record Range(int min, int max) {
    public Task {
        if (min > max) {
            throw new IllegalArgumentException("min phải <= max");
        }
    }
}
```

---

## 2. Sealed Classes (Java 17+)

**Sealed Classes** cho phép kiểm soát **chính xác** class nào được phép extend/implement.

### Code ví dụ

```java
// Sealed interface - chỉ các class được phép implement
public sealed interface Shape 
    permits Circle, Rectangle, Triangle {
    double area();
}

// Các permitted subclasses
public record Circle(double radius) implements Shape {
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

public record Rectangle(double width, double height) implements Shape {
    @Override
    public double area() {
        return width * height;
    }
}

public record Triangle(double base, double height) implements Shape {
    @Override
    public double area() {
        return 0.5 * width * height;
    }
}

// Lưu ý: Circle, Rectangle, Triangle phải là final, sealed, hoặc non-sealed
// final: không cho kế thừa nữa
// sealed: tiếp tục hạn chế
// non-sealed: mở lại cho mọi class
```

### Pattern Matching với Sealed Classes

```java
public class ShapeCalculator {

    // Pattern matching cho switch (Java 21)
    public static double area(Shape shape) {
        return switch (shape) {
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
            case Triangle t -> 0.5 * t.base() * t.height();
            // Không cần default! Compiler biết đã cover hết
        };
    }

    // instanceof pattern matching (Java 16+)
    public static String describe(Shape shape) {
        if (shape instanceof Circle c) {
            return "Circle with radius " + c.radius();
        } else if (shape instanceof Rectangle r) {
            return "Rectangle " + r.width() + "x" + r.height();
        }
        return "Unknown shape";
    }
}
```

### Lợi ích của Sealed Classes

1. **Type safety**: Compiler biết chính xác các subclasses
2. **Exhaustive switch**: Không cần default branch
3. **Domain modeling**: Mô hình hóa chính xác business rules
4. **API design**: Kiểm soát API surface

---

## 3. Pattern Matching

### instanceof Pattern Matching (Java 16+)

```java
// ============ TRƯỚC Java 16 ============
if (obj instanceof String) {
    String str = (String) obj;
    System.out.println(str.length());
}

// ============ SAU Java 16 ============
if (obj instanceof String str) {
    System.out.println(str.length());  // str đã được cast
}

// Với conditions
if (obj instanceof String str && str.length() > 5) {
    System.out.println("Long string: " + str);
}
```

### Switch Expressions (Java 14+)

```java
// Traditional switch (verbose)
String result;
switch (day) {
    case MONDAY:
    case TUESDAY:
        result = "Weekday";
        break;
    case SATURDAY:
    case SUNDAY:
        result = "Weekend";
        break;
    default:
        result = "Unknown";
}

// Modern switch expression (Java 14+)
String result = switch (day) {
    case MONDAY, TUESDAY -> "Weekday";
    case SATURDAY, SUNDAY -> "Weekend";
    default -> "Other";
};
```

### Pattern Matching for Switch (Java 21)

```java
public class PatternMatchingDemo {

    // Switch với pattern matching
    public static String process(Object obj) {
        return switch (obj) {
            case Integer i when i > 0 -> "Positive number: " + i;
            case Integer i -> "Non-positive number: " + i;
            case String s -> "String: " + s.toUpperCase();
            case List<?> list -> "List with " + list.size() + " elements";
            case null -> "Null value";
            default -> "Unknown type";
        };
    }

    // Record patterns (Java 21)
    public record Point(int x, int y) {}

    public static int sumCoordinates(Object obj) {
        return switch (obj) {
            case Point(int x, int y) -> x + y;        // Deconstruct record
            case Point p -> p.x() + p.y();             // Traditional access
            default -> 0;
        };
    }

    // Nested patterns
    public record Line(Point start, Point end) {}

    public static boolean isVertical(Object obj) {
        return switch (obj) {
            case Line(Point(var x1, var y1), Point(var x2, var y2)) 
                when x1 == x2 -> true;
            default -> false;
        };
    }
}
```

---

## Ví dụ tổng hợp: Domain Model với Records + Sealed

```java
// ============ Domain: Payment Processing ============

// Sealed interface cho payment results
public sealed interface PaymentResult 
    permits Success, Failure, Pending {
}

// Records cho từng case
public record Success(String transactionId, BigDecimal amount) 
    implements PaymentResult {}

public record Failure(String reason, String errorCode) 
    implements PaymentResult {
}

public record Pending(String checkBackAt) 
    implements PaymentResult {
}
```

```java
// Service xử lý với pattern matching
@Service
public class PaymentProcessor {

    public String processResult(PaymentResult result) {
        return switch (result) {
            case Success(var id, var amount) -> 
                "✅ Thanh toán thành công: " + amount + " (ID: " + id + ")";
            case Failure(var reason, _) -> 
                "❌ Thanh toán thất bại: " + reason;
            case Pending(var checkAt) -> 
                "⏳ Đang xử lý, kiểm tra lại sau: " + checkBackAt;
        };
    }
}
```

---

## So sánh các tính năng theo version

| Tính năng | Java Version | Mô tả |
|-----------|--------------|-------|
| Lambda | 8 | Anonymous functions |
| Record | 16 (preview 14) | Immutable data class |
| Pattern Matching instanceof | 16 | Type check + cast trong 1 dòng |
| Sealed Classes | 17 | Kiểm soát inheritance |
| Pattern Matching for switch | 21 | Switch với patterns |
| Record Patterns | 21 | Deconstruct records |
| Virtual Threads | 21 | Lightweight concurrency |
| Flexible Constructor Bodies | 25 (final) | Code trước super() |
| Scoped Values | 21-25 | Thread-safe context sharing |

## Best Practices

1. **Dùng Record cho DTOs, Value Objects**: Thay vì class với Lombok
2. **Dùng Sealed + Pattern Matching**: Cho domain models với finite variants
3. **Records là immutable**: Không thể có setters
4. **Sealed cần cẩn thận**: Chỉ dùng khi số variants thực sự cố định
5. **Kết hợp cả 3**: Records + Sealed + Pattern Matching = powerful domain modeling

## Tài liệu tham khảo

- [Java Records (JEP 395)](https://openjdk.org/jeps/395)
- [Sealed Classes (JEP 409)](https://openjdk.org/jeps/409)
- [Pattern Matching for switch](https://openjdk.org/jeps/441)
- [Baeldung Java Records](https://www.baeldung.com/java-record-keyword)

---

**Tiếp theo**: [5. Performance và Garbage Collection](../5_performance-gc/)
