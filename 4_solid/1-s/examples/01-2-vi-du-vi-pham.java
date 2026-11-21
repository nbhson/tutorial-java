// ✗ mot class lam 3 viec: tinh luong, in bao cao, luu DB
class NhanVien {
    private String ten;
    private double luongCoBan;

    double tinhLuong() {
        // logic tinh luong — doi khi KE TOAN yeu cau sua
        return luongCoBan * 1.1 - 500;
    }

    void inBaoCaoLuong() {
        // dinh dang bao cao — doi khi BO PHAN KD yeu cau sua
        System.out.println("Bang luong cua " + ten + ": " + tinhLuong());
    }

    void luuDatabase() {
        // SQL — doi khi DBA yeu cau sua
        System.out.println("INSERT INTO nhan_vien VALUES ('" + ten + "')");
    }
}
