// ✅ vi du khac: giam gia theo thue bao
interface KhuyenMai {
    double giamGia(double tongTien);
}

class KhuyenMaiVip implements KhuyenMai {
    public double giamGia(double t) { return t * 0.1; }
}

class KhuyenMaiBlackFriday implements KhuyenMai {
    public double giamGia(double t) { return t > 1_000_000 ? 200_000 : 0; }
}

class MayTinhHoaDon {
    private final List<KhuyenMai> khuyenMais;   // mo rong bang cach them phan tu
    MayTinhHoaDon(List<KhuyenMai> km) { this.khuyenMais = km; }

    double thanhToan(double tongTien) {
        double giam = khuyenMais.stream().mapToDouble(k -> k.giamGia(tongTien)).sum();
        return tongTien - giam;
    }
}
