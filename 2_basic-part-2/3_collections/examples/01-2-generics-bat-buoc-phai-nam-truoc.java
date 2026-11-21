// Khong co generics: nhet gi vao cung duoc, lay ra phai ep kieu
List list = new ArrayList();
list.add("hello");
list.add(42);
String s = (String) list.get(1); // ClassCastException khi chay!

// Co generics: compiler chan ngay tu buoc bien dich
List<String> list2 = new ArrayList<>();
list2.add("hello");
// list2.add(42); // ← loi bien dich, catch ngay trong IDE
String s2 = list2.get(0); // khong can ep kieu
