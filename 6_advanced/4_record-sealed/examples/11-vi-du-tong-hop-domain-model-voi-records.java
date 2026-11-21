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
