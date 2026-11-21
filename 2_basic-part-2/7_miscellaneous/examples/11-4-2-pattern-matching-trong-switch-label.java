// khai bao bien ngay trong nhan switch
static String phanLoai(Object obj) {
    return switch (obj) {
        case Integer i when i > 0 -> "so duong: " + i;
        case Integer i            -> "so am hoac 0: " + i;
        case String s when !s.isBlank() -> "chuoi hop le: " + s.length();
        case String s            -> "chuoi rong";
        case int[] arr           -> "mang " + arr.length + " phan tu";
        case null                -> "null";
        default                  -> "khong ro";
    };
}

// ket hop voi record
record DoiTuong(String ten, int tuoi) {}

static String moTa(Object obj) {
    return switch (obj) {
        case Circle c          -> "hinh tron r = " + c.radius();
        case Rectangle r      -> "hinh chu nhat " + r.width() + "x" + r.height();
        case Triangle t       -> "tam giac";
        case null, default    -> "khong xac dinh";
    };
}
