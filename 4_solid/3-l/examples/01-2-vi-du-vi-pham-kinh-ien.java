// ✗ hinh vuong ke thua hinh chu nhat — nghe logic nhung pha hop dong
class ChuNhat {
    protected int w, h;

    public void setW(int w) { this.w = w; }
    public void setH(int h) { this.h = h; }
    public int dienTich()   { return w * h; }
}

class Vuong extends ChuNhat {
    @Override
    public void setW(int w) { this.w = w; this.h = w; } // dong bo 2 canh
    @Override
    public void setH(int h) { this.w = h; this.h = h; }
}

public class Demo {
    static void test(ChuNhat cn) {
        cn.setW(5);
        cn.setH(4);
        // Hop dong voi ChuNhat: w=5, h=4 → dien tich = 20
        if (cn.dienTich() != 20) {
            System.out.println("SAI! Dien tich = " + cn.dienTich()); // Vuong cho 16!
        }
    }

    public static void main(String[] args) {
        test(new ChuNhat()); // OK
        test(new Vuong());   // ✗ VI PHAM LSP — pha ky vọng của caller
    }
}
