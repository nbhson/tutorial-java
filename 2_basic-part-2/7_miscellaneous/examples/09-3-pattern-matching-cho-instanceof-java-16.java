// ✗ cu: kiem tra roi ep kieu 2 lan
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}

// ✅ moi: pattern matching — khai bao bien ngay trong instanceof
if (obj instanceof String s && s.length() > 0) {
    System.out.println(s.length()); // s da duoc "binding" kieu String
}

// ket hop voi sealed + record
static String moTa(Object obj) {
    if (obj instanceof Integer i) return "so nguyen: " + i;
    if (obj instanceof String s)  return "chuoi " + s.length() + " ky tu";
    if (obj instanceof Person p)  return "nguoi: " + p.name();
    return "chua biet";
}
