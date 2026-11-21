public record Task(
    Long id,
    String title,
    String description,
    boolean completed,
    LocalDateTime createdAt
) {
    // Compact Constructor - validation
    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title không được để trống");
        }
    }

    // Optional: Custom methods
    public boolean isDone() {
        return completed;
    }

    // Optional: Static factory methods
    public static Task createNew(String title, String description) {
        return new Task(null, title, description, false, LocalDateTime.now());
    }

    // Optional: "With" methods - tạo copy với giá trị mới
    public Task withCompleted(boolean newCompleted) {
        return new Task(id, title, description, newCompleted, createdAt);
    }
}

// Sử dụng
Task task = new Task(1L, "Học Java", "Học Record", false, LocalDateTime.now());

// Accessor methods (không có "get" prefix)
String title = task.title();     // Không phải getTitle()
boolean done = task.completed(); // Không phải isCompleted()

// Record là immutable - không có setters!
// task.setTitle("New");  // ❌ Compile error

// Tạo copy với thay đổi
Task completedTask = task.withCompleted(true);
