// ✗ mỗi loại khuyến mãi mới phải sửa if-else trong class cũ
double tinhGiam(double total, String loai) {
    if (loai.equals("VIP")) return total * 0.1;
    if (loai.equals("MỚI"))  return 50_000;
    return 0;
}

// ✅ mở rộng bằng implement interface mới
interface KhuyenMai { double giamGia(double total); }
class KhuyenMaiVip implements KhuyenMai { public double giamGia(double t) { return t * 0.1; } }
class KhuyenMaiMoi implements KhuyenMai { public double giamGia(double t) { return 50_000; } }
