// ============ Cách 1: Enum Singleton (An toàn nhất) ============
public enum DatabaseConnection {
    INSTANCE;

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:h2:mem:db");
        }
        return connection;
    }
}

// Sử dụng: DatabaseManager.INSTANCE.getConnection()
