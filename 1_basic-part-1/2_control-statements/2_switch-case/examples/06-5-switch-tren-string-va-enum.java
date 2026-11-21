// String — Java 7+
switch (lenh) {
    case "start": batDau(); break;
    case "stop":  dungLai(); break;
    default:      System.out.println("Lenh khong hop le");
}

// enum — pho bien nhat
enum HinhThuc { TUAN_TRA, THANG, NAM }

double tienTietKiem(double tien, HinhThuc kyHan) {
    return switch (kyHan) {
        case TUAN_TRA -> tien * 0.001;
        case THANG -> tien * 0.05;
        case NAM -> tien * 0.06;
    }; // khong can default — enum da du case
}
