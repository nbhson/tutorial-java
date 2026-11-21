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
