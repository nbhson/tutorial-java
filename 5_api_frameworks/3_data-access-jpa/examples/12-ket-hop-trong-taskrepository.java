@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom {
    // Có cả methods của JpaRepository và TaskRepositoryCustom
}
