// ❌ Tạo object không cần thiết
for (String s : list) {
    String upper = new String(s.toUpperCase());  // Unnecessary new object
    process(upper);
}

// ✅ Tái sử dụng/Caching
String upper = s.toUpperCase();  // Không tạo String mới
