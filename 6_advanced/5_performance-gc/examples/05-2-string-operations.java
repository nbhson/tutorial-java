// ❌ String concatenation trong loop
String result = "";
for (String s : list) {
    result += s + ",";  // Tạo nhiều String objects
}

// ✅ StringBuilder
StringBuilder sb = new StringBuilder();
for (String s : list) {
    if (sb.length() > 0) sb.append(",");
    sb.append(s);
}
String result = sb.toString();

// ✅ Java 9+: interned strings được optimize
// Nhưng vẫn nên dùng StringBuilder cho loop dài

// ✅ String.join (Java 8+)
String result = String.join(",", list);

// ✅ Collectors.joining (Stream)
String result = list.stream().collect(Collectors.joining(","));
