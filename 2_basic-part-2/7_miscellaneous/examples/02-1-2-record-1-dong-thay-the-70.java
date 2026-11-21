// ✅ mot dong thay the 70 dong
public record Person(String name, int age, String email) {}

// su dung
Person p = new Person("An", 25, "an@mail.com");
System.out.println(p.name());  // getter ten truong: name(), KHONG phai getName()
System.out.println(p);         // Person[name=An, age=25, email=an@mail.com]
