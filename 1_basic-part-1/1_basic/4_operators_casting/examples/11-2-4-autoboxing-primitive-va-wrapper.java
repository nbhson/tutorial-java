// primitive: int, long, double... | wrapper: Integer, Long, Double...
Integer a = 127;
Integer b = 127;
System.out.println(a == b);   // true  — cache cua Integer tu -128..127

Integer c = 128;
Integer d = 128;
System.out.println(c == d);      // false! — ngoai cache, la 2 object khac nhau
System.out.println(c.equals(d)); // true ✅

int e = c; // unboxing tu dong
