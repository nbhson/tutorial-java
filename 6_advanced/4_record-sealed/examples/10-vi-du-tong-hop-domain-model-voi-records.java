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
