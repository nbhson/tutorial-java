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
