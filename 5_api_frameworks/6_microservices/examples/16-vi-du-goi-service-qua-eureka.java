// Trong Task Service, gọi User Service
@Service
public class TaskService {

    private final RestClient restClient;

    public TaskService(@LoadBalanced RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public TaskResponse createTask(TaskRequest request) {
        // Gọi User Service để validate user tồn tại
        UserDTO user = restClient
            .get()
            .uri("http://user-service/api/users/{id}", request.userId())
            .retrieve()
            .body(UserDTO.class);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // Tạo task...
        Task task = new Task(request.title(), request.description());
        return taskRepository.save(task);
    }
}
