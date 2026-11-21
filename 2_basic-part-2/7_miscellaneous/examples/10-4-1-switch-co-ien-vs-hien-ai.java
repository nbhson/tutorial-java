// ✗ cu: nhieu break, fallthrough nguy hiem
static String dienGiaiC1(Object obj) {
    switch (obj) {
        case Integer i: return "so " + i;
        case String s:   return "chuoi " + s;
        case null:       return "null";
        default:         return "khong ro";
    }
}

// ✅ java 21+: switch expression, arrow, exhaustiveness
static String dienGiaiC2(Object obj) {
    return switch (obj) {
        case Integer i -> "so nguyen: " + i;
        case String s  -> "chuoi do dai " + s.length();
        case null      -> "la null";
        default        -> "loai khac: " + obj.getClass().getSimpleName();
    };
}
