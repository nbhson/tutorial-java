// switch tra ve gia tri, khong can break, khong so rơi xu
String tenThu = switch (thu) {
    case 1 -> "Thu Hai";
    case 2 -> "Ba";
    case 6, 7 -> "Cuoi tuan";        // nhieu case mot dong
    default -> "Ngay trong tuan";
};

// co the co khoi lenh + yield de tra ve gia tri
int soNgay = switch (thang) {
    case 1, 3, 5, 7, 8, 10, 12 -> 31;
    case 4, 6, 9, 11 -> 30;
    case 2 -> {
        int namNhuan = (nam % 4 == 0 && nam % 100 != 0) || nam % 400 == 0 ? 1 : 0;
        yield 28 + namNhuan;         // yield = tra ve gia tri trong khoi
    }
    default -> -1;
};
