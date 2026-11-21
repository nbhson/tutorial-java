// ============ RestClient (Mới, Spring 6.1+) - Khuyến nghị ============
@Service
public class UserServiceClient {

    private final RestClient restClient;

    public UserServiceClient(RestClient.Builder builder) {
        this.restClient = builder
            .baseUrl("http://user-service")  // Tên từ Eureka, không cần IP
            .build();
    }

    public UserDTO getUser(Long id) {
        return restClient
            .get()
            .uri("/api/users/{id}", id)
            .retrieve()
            .body(UserDTO.class);
    }
}
