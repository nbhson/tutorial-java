// ✗ business logic "cam co" vao MySQL
class OrderService {
    private MySQLDatabase db = new MySQLDatabase(); // tao cung, phu thuoc chi tiet

    void datHang(DonHang don) {
        // ... logic dat hang ...
        db.save(don);   // neu doi sang PostgreSQL hoac them cache? sua code nay!
    }
}

class MySQLDatabase {
    void save(DonHang don) {
        System.out.println("Luu vao MySQL: " + don);
    }
}
