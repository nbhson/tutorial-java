public class Pizza {
    private final String size;          // bat buoc
    private final boolean phoMat;       // tuy chon
    private final boolean hanh;
    private final boolean oi;
    private final String ghiChu;

    private Pizza(Builder b) {          // constructor private — chi Builder tao duoc
        this.size    = b.size;
        this.phoMat  = b.phoMat;
        this.hanh    = b.hanh;
        this.oi      = b.oi;
        this.ghiChu  = b.ghiChu;
    }

    public static class Builder {
        // bat buoc
        private final String size;
        // tuy chon — khoi tao gia tri mac dinh
        private boolean phoMat = true;
        private boolean hanh   = false;
        private boolean oi     = false;
        private String ghiChu  = "";

        public Builder(String size) { this.size = size; }

        public Builder phoMat(boolean v) { this.phoMat = v; return this; }
        public Builder hanh(boolean v)   { this.hanh = v;   return this; }
        public Builder oi(boolean v)     { this.oi = v;     return this; }
        public Builder ghiChu(String s)  { this.ghiChu = s; return this; }

        public Pizza build() {
            if (size == null || size.isBlank())
                throw new IllegalArgumentException("Size bat buoc");
            return new Pizza(this);
        }
    }
}

// ✅ su dung — doc nhu cau thoai mai
Pizza p = new Pizza.Builder("L")
        .phoMat(true)
        .oi(true)
        .ghiChu("Cat thanh 8 mieng")
        .build();
