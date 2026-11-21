// ✗ loi kinh dien: so sanh chuoi bang ==
String s1 = "hello";
String s2 = new String("hello");
System.out.println(s1 == s2);        // false! so sanh THAM CHIEU
System.out.println(s1.equals(s2));   // true — so sanh NOI DUNG ✅

// voi primitive (int, long, double...) thi == la dung y nghia
// voi String, Integer, ... luon dung equals()
