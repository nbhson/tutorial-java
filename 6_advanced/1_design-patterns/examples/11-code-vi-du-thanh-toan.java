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
