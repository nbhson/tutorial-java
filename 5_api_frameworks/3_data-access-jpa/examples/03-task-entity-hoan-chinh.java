package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks", indexes = {
    @Index(name = "idx_title", columnList = "title"),
    @Index(name = "idx_completed", columnList = "completed")
})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Boolean completed = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Priority priority = Priority.MEDIUM;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    // Lifecycle Callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Task() {}

    public Task(String title, String description, Priority priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    // Getters, Setters...
}
