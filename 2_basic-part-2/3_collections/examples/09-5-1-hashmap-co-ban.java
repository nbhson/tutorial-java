Map<String, Integer> diem = new HashMap<>();
int x = diem.get("khong co");       // NPE! get tra null, unboxing no
int y = diem.getOrDefault("khong co", 0); // ✅ an toan
