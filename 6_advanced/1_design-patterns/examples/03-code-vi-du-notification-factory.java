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
