// switch la expression, tra ve gia tri
static int soNgay(int thang, int nam) {
    return switch (thang) {
        case 1, 3, 5, 7, 8, 10, 12 -> 31;
        case 4, 6, 9, 11           -> 30;
        case 2                     -> (nam % 4 == 0 && nam % 100 != 0) || (nam % 400 == 0)
                                         ? 29 : 28;
        default                    -> -1;
    };
}
