// ✗ da dạng: class con "làm rỗng" method của cha
abstract class NguoiChoi {
    abstract void danhCo();
}

class MayTinh implements NguoiChoi {
    public void danhCo() { /* AI danh co */ }
}

class NguoiXem implements NguoiChoi {
    public void danhCo() {
        throw new UnsupportedOperationException(); // ✗ caller không thể tin NguoiChoi nữa
    }
}
