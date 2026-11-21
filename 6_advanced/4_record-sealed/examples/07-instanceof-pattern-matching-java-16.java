// ============ TRƯỚC Java 16 ============
if (obj instanceof String) {
    String str = (String) obj;
    System.out.println(str.length());
}

// ============ SAU Java 16 ============
if (obj instanceof String str) {
    System.out.println(str.length());  // str đã được cast
}

// Với conditions
if (obj instanceof String str && str.length() > 5) {
    System.out.println("Long string: " + str);
}
