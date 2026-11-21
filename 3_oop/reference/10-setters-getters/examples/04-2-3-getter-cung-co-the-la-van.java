public class LopHoc {
    private final List<String> hs = new ArrayList<>();

    public List<String> getHocSinh() { return hs; } // ✗ nguoi ngoai hs.clear() duoc!
}

// ✅ tra ve ban sao khong the sua
public List<String> getHocSinh() {
    return List.copyOf(hs); // bang sao khong doi (Java 10+)
}
