// ✗ vi phạm: một class vừa tính lương, vừa in báo cáo, vừa lưu DB
class NhanVien {
    void tinhLuong() {}
    void inBaoCao() {}
    void luuDatabase() {}
}

// ✅ tách: mỗi class một lý do thay đổi duy nhất
class TinhLuongService { void tinhLuong(NhanVien nv) {} }
class BaoCaoService     { void in(NhanVien nv) {} }
class NhanVienRepository{ void save(NhanVien nv) {} }
