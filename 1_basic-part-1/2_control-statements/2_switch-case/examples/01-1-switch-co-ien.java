int thu = 3;
switch (thu) {
    case 1:
        System.out.println("Thu Hai");
        break;                       // bat buoc! khong co break → rơi xuống case sau
    case 2:
        System.out.println("Ba");
        break;
    case 6:
    case 7:                          // nhom case — khong co break o day la Y DING
        System.out.println("Cuoi tuan");
        break;
    default:                         // khong khop case nao
        System.out.println("Ngay trong tuan");
        break;
}
