// ✗ cu: chuoi if-instanceof loi nhoi
static String moTa(Object obj) {
    if (obj instanceof Integer i) {
        return "so nguyen " + i;
    } else if (obj instanceof String s) {
        return "chuoi " + s;
    } else if (obj == null) {
        return "null";
    }
    return "khong ro";
}

// ✅ java 21: switch pattern matching — gon va day du (exhaustive)
static String moTa(Object obj) {
    return switch (obj) {
        case Integer i when i > 0 -> "so nguyen duong: " + i;  // when = dieu kien bo sung
        case Integer i            -> "so nguyen: " + i;
        case String s when s.isEmpty() -> "chuoi rong";
        case String s             -> "chuoi: " + s;
        case null                 -> "la null";                // xu ly null trong switch!
        default                   -> "khong ro";
    };
}
