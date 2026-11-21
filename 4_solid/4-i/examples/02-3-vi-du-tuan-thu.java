// ✅ tach nho theo nang luc
interface CoTheIn {
    void in(String taiLieu);
}

interface CoTheScan {
    void scan(String taiLieu);
}

interface CoTheFax {
    void fax(String soDienThoai);
}

class MayInInOnly implements CoTheIn {
    public void in(String taiLieu) { System.out.println("Dang in: " + taiLieu); }
}

// may da chuc nang implement nhung interface can
class MayInDaNang implements CoTheIn, CoTheScan, CoTheFax {
    public void in(String taiLieu)         { System.out.println("In: " + taiLieu); }
    public void scan(String taiLieu)       { System.out.println("Scan: " + taiLieu); }
    public void fax(String soDienThoai)    { System.out.println("Fax: " + soDienThoai); }
}

// client chi phu thuoc nang luc minh can
class TrungTamPhoto {
    void xongViec(CoTheIn mayIn) { mayIn.in("tai lieu"); } // khong biet fax lam gi
}
