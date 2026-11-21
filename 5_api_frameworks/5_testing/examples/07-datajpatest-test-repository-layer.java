@DataJpaTest
@DisplayName("TaskRepository Tests")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Tìm tasks theo priority")
    void shouldFindTasksByPriority() {
        // Arrange
        Task highPriority = new Task("High", "Desc", Task.Priority.HIGH);
        Task lowPriority = new Task("Low", "Desc", Task.Priority.LOW);
        taskRepository.saveAll(List.of(highPriority, lowPriority));

        // Act
        List<Task> result = taskRepository.findByPriority(Task.Priority.HIGH);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPriority()).isEqualTo(Task.Priority.HIGH);
    }
}
