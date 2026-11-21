// ✗ anti-pattern: setter mù, object khong bao gio "hoan chinh"
public class TaiKhoan {
    private String soTk;
    private long soDu;

    public void setSoTk(String s) { this.soTk = s; }
    public void setSoDu(long d)   { this.soDu = d; }  // ai cung set duoc so du!
}

// ✅ tot hon: tao hop le trong constructor, thay doi qua method y nghia
public class TaiKhoan {
    private final String soTk;   // final: khong the thay sau khi tao
    private long soDu;

    public TaiKhoan(String soTk, long soDuBanDau) {
        if (soTk == null || soTk.isBlank()) throw new IllegalArgumentException("soTK rong");
        if (soDuBanDau < 0) throw new IllegalArgumentException("so du am");
        this.soTk = soTk;
        this.soDu = soDuBanDau;
    }

    public long soDu() { return soDu; }

    public void napTien(long tien) {        // hanh vi co nghia, khong phai "set"
        if (tien <= 0) throw new IllegalArgumentException("Tien nap phai > 0");
        soDu += tien;
    }

    public void rutTien(long tien) {
        if (tien > soDu) throw new IllegalArgumentException("Khong du so du");
        soDu -= tien;
    }
}
