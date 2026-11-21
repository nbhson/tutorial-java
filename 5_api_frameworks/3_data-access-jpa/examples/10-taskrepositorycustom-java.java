// Interface cho custom methods
public interface TaskRepositoryCustom {
    List<Task> searchTasks(String keyword, Task.Priority priority, Boolean completed);
}
