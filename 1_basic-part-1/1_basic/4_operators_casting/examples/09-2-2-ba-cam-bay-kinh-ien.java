// cam bay 1: chia nguyen truoc, ep kieu sau
int a = 10, b = 3;
System.out.println((double) (a / b)); // 3.0  — SAI y
System.out.println((double) a / b);   // 3.33 — DUNG

// cam bay 2: tran so khi narrow
int lon = 130;
byte be = (byte) lon;   // be = -126 — wrap-around, khong bao loi!

// cam bay 3: ep kieu object khac chuyen chuoi
String so = "123";
int x = (int) so;                    // ✗ khong bien dich duoc!
int y = Integer.parseInt(so);        // ✅ dung parse
double z = Double.parseDouble("3.14");
