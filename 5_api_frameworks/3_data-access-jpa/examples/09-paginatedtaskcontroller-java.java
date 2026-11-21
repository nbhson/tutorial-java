@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        // Tạo Pageable object
        Sort sort = direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Lấy dữ liệu phân trang
        Page<Task> tasks = taskRepository.findAll(pageable);

        return ResponseEntity.ok(tasks);
    }

    // Response ví dụ:
    // {
    //   "content": [...],           // Danh sách tasks
    //   "totalElements": 100,        // Tổng số records
    //   "totalPages": 10,           // Tổng số pages
    //   "size": 10,                 // Kích thước page
    //   "number": 0,                // Page hiện tại (0-indexed)
    //   "first": true,              // Là page đầu tiên?
    //   "last": false               // Là page cuối cùng?
    // }
}
