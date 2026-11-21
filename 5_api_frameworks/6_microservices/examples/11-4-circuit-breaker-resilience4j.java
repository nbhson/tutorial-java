@Service
public class TaskService {

    private final UserServiceClient userServiceClient;

    public TaskService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "getDefaultUser")
    @Retry(name = "userService")
    @TimeLimiter(name = "userService")
    public UserDTO getUserWithResilience(Long userId) {
        return userServiceClient.getUser(userId);
    }

    // Fallback method - được gọi khi circuit breaker mở
    private UserDTO fallbackGetUser(Long userId, Throwable t) {
        // Trả về default user thay vì fail
        return new UserDTO(userId, "Unknown User", "unknown@example.com");
    }
}
