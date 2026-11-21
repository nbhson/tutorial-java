package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO cho request tạo/cập nhật task
public record TaskRequest(
    @NotBlank(message = "Title không được để trống")
    @Size(min = 3, max = 100, message = "Title phải từ 3-100 ký tự")
    String title,
    
    @Size(max = 1000, message = "Description tối đa 1000 ký tự")
    String description,
    
    Integer priority
) {}

// DTO cho response trả về
public record TaskResponse(
    Long id,
    String title,
    String description,
    boolean completed,
    Integer priority,
    String createdAt,
    String updatedAt
) {}
