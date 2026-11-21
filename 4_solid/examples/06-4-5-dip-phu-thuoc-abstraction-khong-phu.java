// ✗ phụ thuộc cứng vào MySQL
class UserService {
    private MySQLDatabase db = new MySQLDatabase(); // muốn đổi PostgreSQL? sửa code!
}

// ✅ phụ thuộc interface
class UserService {
    private final UserRepository db;      // abstraction
    UserService(UserRepository db) { this.db = db; } // inject từ ngoài — chính là DI trong Spring
}
