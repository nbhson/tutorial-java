// Request DTO
public record CreateUserRequest(
    @NotBlank String name,
    @Email String email,
    @Size(min = 8) String password
) {}

// Response DTO
public record UserResponse(
    Long id,
    String name,
    String email,
    String createdAt
) {
    // Static factory từ Entity
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt().toString()
        );
    }
}

// Controller
@PostMapping("/api/users")
public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
    User user = userService.create(request);
    return ResponseEntity.status(201).body(UserResponse.from(user));
}
