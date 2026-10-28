# Design Patterns - Các Mẫu Thiết Kế

## Design Patterns là gì?

**Design Pattern** là giải pháp tổng quát, có thể tái sử dụng cho các vấn đề thường gặp trong thiết kế phần mềm. Chúng không phải là code hoàn chỉnh, mà là "blueprint" (bản thiết kế) mô tả cách giải quyết vấn đề.

> **Yêu cầu**: Java 17+. Các ví dụ dùng tính năng hiện đại như Records, Sealed Classes, Pattern Matching.

## Phân loại Design Patterns

| Loại | Mục đích | Ví dụ |
|------|----------|-------|
| **Creational** | Cách tạo object | Singleton, Factory, Builder |
| **Structural** | Cấu trúc class/object | Adapter, Decorator, Facade |
| **Behavioral** | Giao tiếp giữa objects | Observer, Strategy, Command |

Trong tutorial này, chúng ta học 5 patterns phổ biến nhất.

---

## 1. Singleton Pattern

**Mục đích**: Đảm bảo một class chỉ có **một instance duy nhất** và cung cấp điểm truy cập toàn cục.

### Khi nào dùng?
- Database connection pool
- Logger
- Configuration manager
- Cache

### Code ví dụ

```java
// ============ Cách 1: Enum Singleton (An toàn nhất) ============
public enum DatabaseConnection {
    INSTANCE;

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:h2:mem:db");
        }
        return connection;
    }
}

// Sử dụng: DatabaseManager.INSTANCE.getConnection()
```

```java
// ============ Cách 2: Thread-safe với Double-Checked Locking ============
public class Logger {

    private static volatile Logger instance;  // volatile: đảm bảo thread-safe

    private Logger() {
        // Private constructor: không cho tạo từ bên ngoài
    }

    public static Logger getInstance() {
        if (instance == null) {                    // Check 1 (không lock)
            synchronized (Logger.class) {
                if (instance == null) {            // Check 2 (có lock)
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[" + java.time.LocalTime.now() + "] " + message);
    }
}

// Sử dụng:
Logger logger = Logger.getInstance();
logger.info("Xin chào!");
```

### Ưu/Nhược điểm

| Ưu điểm | Nhược điểm |
|---------|-----------|
| Chỉ 1 instance toàn cục | Khó test (global state) |
| Lazy initialization | Phải cẩn thận thread-safety |
| Tránh overhead tạo nhiều objects | Có thể che giấu dependencies |

---

## 2. Factory Pattern

**Mục đích**: Tạo objects mà **không expose logic khởi tạo** cho client. Client chỉ cần biết interface chung.

### Code ví dụ: Notification Factory

```java
// Interface chung
public interface Notification {
    void notifyUser(String message);
}

// Các implementations
public record EmailNotification() implements Notification {
    @Override
    public void notifyUser(String message) {
        System.out.println("📧 Gửi Email: " + message);
    }
}

public record SmsNotification() implements Notification {
    @Override
    public void notifyUser(String message) {
        System.out.println("📱 Gửi SMS: " + message);
    }
}

public record PushNotification() implements Notification {
    @Override
    public void notifyUser(String message) {
        System.out.println("🔔 Gửi Push Notification: " + message);
    }
}
```

```java
// Factory Class
public class NotificationFactory {

    public static Notification createNotification(String type) {
        return switch (type.toUpperCase()) {  // Pattern matching (Java 21+)
            case "EMAIL" -> new EmailNotification();
            case "SMS" -> new SmsNotification();
            case "PUSH" -> new PushNotification();
            default -> throw new IllegalArgumentException(
                "Unknown notification type: " + type);
        };
    }
}

// Sử dụng:
Notification email = NotificationFactory.createNotification("EMAIL");
email.notifyUser("Chào mừng đăng ký!");
```

### Khi nào dùng Factory?

- Không biết trước chính xác loại object cần tạo (chỉ biết interface)
- Muốn tập trung logic tạo object ở một nơi
- Muốn giảm coupling giữa client và concrete classes

---

## 3. Builder Pattern

**Mục đích**: Tạo objects phức tạp với **nhiều optional parameters** một cách dễ đọc, tránh "telescoping constructors".

### Vấn đề với Constructor

```java
// ❌ Đoạn code này khó đọc và dễ nhầm thứ tự parameters
Pizza pizza = new Pizza("Large", true, false, true, false, true, false);
// Không biết true/false là gì!
```

### Code ví dụ: Builder với Record

```java
public record Pizza(
    String size,
    boolean cheese,
    boolean pepperoni,
    boolean mushroom,
    boolean olive
) {
    // Static method để tạo Builder
    public static Builder builder() {
        return new Builder();
    }

    // Builder inner class
    public static class Builder {
        // Bắt buộc
        private final String size;

        // Optional với default values
        private boolean cheese = false;
        private boolean pepperoni = false;
        private boolean mushroom = false;
        private boolean olive = false;

        public Builder(String size) {
            this.size = size;
        }

        public Builder cheese(boolean value) {
            this.cheese = cheese;
            return this;  // Return this để chain method
        }

        public Builder pepperoni(boolean value) {
            this.pepperoni = pepperoni;
            return this;
        }

        public Builder mushroom(boolean value) {
            this.mushroom = mushroom;
            return this;
        }

        public Builder olive(boolean value) {
            this.olive = olive;
            return this;
        }

        public Pizza build() {
            return new Pizza(size, cheese, pepperoni, mushroom, olive);
        }
    }
}

// Sử dụng: Rõ ràng, dễ đọc!
Pizza pizza = Pizza.builder()
    .size("Large")
    .cheese(true)
    .mushroom(true)
    .build();
```

### Builder trong Spring Boot (thường thấy)

```java
// Lombok @Builder annotation (rất phổ biến trong Spring projects)
@Builder
public class User {
    private String name;
    private String email;
    private int age;
}

// Sử dụng
User user = User.builder()
    .name("Nguyễn Sơn")
    .email("nguyen@example.com")
    .age(25)
    .build();
```

---

## 4. Observer Pattern

**Mục đích**: Định nghĩa **one-to-many** dependency - khi một object thay đổi, tất cả dependents được thông báo tự động.

### Code ví dụ: Event System

```java
import java.util.ArrayList;
import java.util.List;

// Observer Interface
public interface EventListener {
    void onEvent(String eventType, String data);
}

// Subject (Publisher)
public class EventManager {

    private final List<EventListener> listeners = new ArrayList<>();

    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(EventListener listener) {
        listeners.remove(listener);
    }

    public void publish(String eventType, String data) {
        for (EventListener listener : listeners) {
            listener.onEvent(eventType, data);
        }
    }
}
```

```java
// Concrete Observers
public class EmailListener implements EventListener {
    @Override
    public void onEvent(String eventType, String data) {
        if ("ORDER_CREATED".equals(eventType)) {
            System.out.println("📧 Gửi email xác nhận đơn hàng: " + data);
        }
    }
}

public class InventoryListener implements EventListener {
    @Override
    public void onEvent(String eventType, String data) {
        if ("ORDER_CREATED".equals(eventType)) {
            System.out.println("📦 Trừ tồn kho cho đơn hàng: " + data);
        }
    }
}

// Sử dụng
EventManager manager = new EventManager();
manager.subscribe(new EmailListener());
manager.subscribe(new InventoryListener());
manager.publish("ORDER_CREATED", "ORD-12345");
// Output:
// 📧 Gửi email xác nhận đơn hàng: ORD-12345
// 📦 Trừ tồn kho cho đơn hàng: ORD-12345
```

### Observer trong Spring (ApplicationEventPublisher)

```java
// Custom Event
public record OrderCreatedEvent(String orderId, BigDecimal amount) {}

// Publisher (Service)
@Service
public class OrderService {

    private final ApplicationEventPublisher publisher;

    public OrderService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void createOrder(String orderId) {
        // ... logic tạo order
        publisher.publishEvent(new OrderCreatedEvent(orderId, BigDecimal.valueOf(100)));
    }
}

// Listeners
@Component
public class EmailEventListener {
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("📧 Gửi email cho đơn hàng: " + event.orderId());
    }
}

@Component
public class InventoryEventListener {
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("📦 Trừ kho cho đơn hàng: " + event.orderId());
    }
}
```

---

## 5. Strategy Pattern

**Mục đích**: Định nghĩa **một tập hợp algorithms**, đóng gói từng cái, và cho phép swap linh hoạt tại runtime.

### Code ví dụ: Thanh toán

```java
// Strategy Interface (sealed: chỉ các class được phép implement)
public sealed interface PaymentStrategy permits CreditCardPayment, PayPalPayment, MomoPayment {
    void pay(BigDecimal amount);
}

// Concrete Strategies
public record CreditCardPayment(String cardNumber) implements PaymentStrategy {
    @Override
    public void pay(BigDecimal amount) {
        System.out.println("💳 Thanh toán " + amount + " bằng thẻ: " + maskCard());
    }

    private String maskCard() {
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}

public record PayPalPayment(String email) implements PaymentStrategy {
    @Override
    public void pay(BigDecimal amount) {
        System.out.println("🅿️ Thanh toán " + amount + " qua PayPal: " + email);
    }
}

public record MomoPayment(String phone) implements PaymentStrategy {
    @Override
    public void pay(BigDecimal amount) {
        System.out.println("📲 Thanh toán " + amount + " qua Momo: " + phone);
    }
}
```

```java
// Context - Sử dụng Strategy
public class CheckoutService {

    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void checkout(ShoppingCart cart) {
        BigDecimal total = cart.calculateTotal();
        paymentStrategy.pay(total);  // Strategy quyết định cách thanh toán
    }
}

// Sử dụng: Thay đổi strategy tại runtime
CheckoutService service = new CheckoutService();
service.setPaymentStrategy(new CreditCardPayment("1234567890123456"));
service.checkout(cart);  // Thanh toán bằng thẻ

service.setPaymentStrategy(new MomoPayment("0912345678"));
service.checkout(cart);  // Đổi sang Momo
```

---

## Pattern Comparison

| Pattern | Mục đích chính | Khi nào dùng |
|---------|----------------|--------------|
| **Singleton** | 1 instance duy nhất | Config, Logger, Connection Pool |
| **Factory** | Tạo object, che giấu logic | Không biết trước class cụ thể |
| **Builder** | Tạo object phức tạp | Nhiều optional parameters |
| **Observer** | Thông báo khi state thay đổi | Event systems, pub/sub |
| **Strategy** | Swap algorithms linh hoạt | Nhiều cách làm 1 việc |

## Tài liệu tham khảo

- [Refactoring.Guru - Design Patterns](https://refactoring.guru/design-patterns/java)
- [Baeldung Design Patterns](https://www.baeldung.com/java-design-patterns)
- [GoF Design Patterns](https://en.wikipedia.org/wiki/Design_Patterns)

---

**Tiếp theo**: [2. Stream API và Lambda Expressions](../2_stream-api-lambda/)
