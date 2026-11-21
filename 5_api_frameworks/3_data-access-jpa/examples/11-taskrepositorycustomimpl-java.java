public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Task> searchTasks(String keyword, Task.Priority priority, Boolean completed) {
        StringBuilder jpql = new StringBuilder("SELECT t FROM Task t WHERE 1=1");

        if (keyword != null) {
            jpql.append(" AND (t.title LIKE :keyword OR t.description LIKE :keyword)");
        }
        if (priority != null) {
            jpql.append(" AND t.priority = :priority");
        }
        if (completed != null) {
            jpql.append(" AND t.completed = :completed");
        }

        TypedQuery<Task> query = entityManager.createQuery(jpql.toString(), Task.class);

        if (keyword != null) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        if (priority != null) {
            query.setParameter("priority", priority);
        }
        if (completed != null) {
            query.setParameter("completed", completed);
        }

        return query.getResultList();
    }
}
