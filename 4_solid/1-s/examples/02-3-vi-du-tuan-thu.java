// ✅ tach theo trach nhiem
class NhanVien {                       // chi giu du lieu
    private final String ten;
    private final double luongCoBan;
    NhanVien(String ten, double luong) { this.ten = ten; this.luongCoBan = luong; }
    String ten()          { return ten; }
    double luongCoBan()   { return luongCoBan; }
}

class TinhLuongService {               // chi logic tinh toan
    double tinh(NhanVien nv) { return nv.luongCoBan() * 1.1 - 500; }
}

class BaoCaoLuongService {             // chi dinh dang xuat
    void in(NhanVien nv, TinhLuongService service) {
        System.out.println("Bang luong cua " + nv.ten() + ": " + service.tinh(nv));
    }
}

class NhanVienRepository {             // chi truy van du lieu
    void save(NhanVien nv) {
        System.out.println("INSERT INTO nhan_vien VALUES ('" + nv.ten() + "')");
    }
}
