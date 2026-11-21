TreeSet<Integer> diem = new TreeSet<>();
diem.add(7); diem.add(9); diem.add(5);
System.out.println(diem);            // [5, 7, 9]
System.out.println(diem.first());    // 5
System.out.println(diem.last());     // 9
System.out.println(diem.ceiling(6)); // 7 - phan tu nho nhat >= 6
