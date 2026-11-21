// ✗ mot interface "beo" — ep tat ca may in lam moi thu
interface MayIn {
    void in(String taiLieu);
    void scan(String taiLieu);
    void fax(String soDienThoai);
}

class MayInInOnly implements MayIn {
    public void in(String taiLieu)   { System.out.println("Dang in: " + taiLieu); }

    public void scan(String taiLieu) {
        throw new UnsupportedOperationException(); // may nay khong scan!
    }

    public void fax(String soDienThoai) {
        throw new UnsupportedOperationException(); // may nay khong fax!
    }
}
