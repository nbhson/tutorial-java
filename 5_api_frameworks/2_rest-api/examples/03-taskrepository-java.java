package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Tìm tất cả task chưa hoàn thành
    List<Task> findByCompletedFalse();
    
    // Tìm theo priority
    List<Task> findByPriority(Integer priority);
    
    // Tìm kiếm trong title hoặc description
    List<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        String title, String description
    );
}
