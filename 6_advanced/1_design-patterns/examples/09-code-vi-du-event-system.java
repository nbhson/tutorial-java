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
