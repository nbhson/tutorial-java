// dieu kien ? gia_tri_neu_dung : gia_tri_neu_sai
String ketQua = (diem >= 5) ? "DAT" : "TRUOT";

// instanceof — kiem tra kieu object
Object obj = "hello";
if (obj instanceof String s) {   // Java 16+: khai bao bien ngay tai cho
    System.out.println(s.length());
}
