// ✅ 1. business logic DINH NGHIA abstraction minh can
interface DonHangRepository {
    void save(DonHang don);
}

// ✅ 2. business logic chi phu thuoc abstraction
class OrderService {
    private final DonHangRepository repo;   // phu thuoc interface

    OrderService(DonHangRepository repo) {  // 3. inject tu ngoai (constructor injection)
        this.repo = repo;
    }

    void datHang(DonHang don) {
        // ... logic dat hang ...
        repo.save(don);
    }
}

// ✅ 4. chi tiet phu thuoc abstraction (muoi ten nguoc lai)
class MySQLDonHangRepository implements DonHangRepository {
    public void save(DonHang don) { System.out.println("Luu vao MySQL: " + don); }
}

class MemoryDonHangRepository implements DonHangRepository { // de test
    private final List<DonHang> daLuu = new ArrayList<>();
    public void save(DonHang don) { daLuu.add(don); }
    List<DonHang> daLuu() { return daLuu; }
}

// su dung
OrderService prod = new OrderService(new MySQLDonHangRepository());
OrderService test = new OrderService(new MemoryDonHangRepository()); // test khong can DB!
