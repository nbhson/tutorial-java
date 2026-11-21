// Lombok @Builder annotation (rất phổ biến trong Spring projects)
@Builder
public class User {
    private String name;
    private String email;
    private int age;
}

// Sử dụng
User user = User.builder()
    .name("Nguyễn Sơn")
    .email("nguyen@example.com")
    .age(25)
    .build();
