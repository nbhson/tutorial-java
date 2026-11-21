@Service
public class OrderService {
    private final DonHangRepository repo;       // chi biet interface

    public OrderService(DonHangRepository repo) { // Spring tu tim bean trien khai va inject
        this.repo = repo;
    }
}

@Repository
public class JpaDonHangRepository implements DonHangRepository { /* JPA */ }
