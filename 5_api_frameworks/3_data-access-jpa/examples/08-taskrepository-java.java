package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;        // Phân trang
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Custom query
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // ============ Derived Queries (Tự động generate SQL) ============

    // SELECT * FROM tasks WHERE completed = false
    List<Task> findByCompletedFalse();

    // SELECT * FROM tasks WHERE priority = ?
    List<Task> findByPriority(Task.Priority priority);

    // SELECT * FROM tasks WHERE title LIKE '%keyword%'
    List<Task> findByTitleContaining(String keyword);

    // SELECT * FROM tasks WHERE user_id = ?
    List<Task> findByUserId(Long userId);

    // ============ Custom Queries với @Query ============

    @Query("SELECT t FROM Task t WHERE t.completed = false AND t.priority = :priority ORDER BY t.createdAt DESC")
    List<Task> findIncompleteTasksByPriority(@Param("priority") Task.Priority priority);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.user.id = :userId AND t.completed = true")
    long countCompletedTasksByUser(@Param("userId") Long userId);

    // ============ Pagination ============

    Page<Task> findByUser(User user, Pageable pageable);

    Page<Task> findByCompleted(boolean completed, Pageable pageable);

    // ============ Optional Queries ============

    Optional<Task> findByTitle(String title);
}
