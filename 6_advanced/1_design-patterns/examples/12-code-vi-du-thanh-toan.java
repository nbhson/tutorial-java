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
