// 1. khong the extends lop khac (record da ngam dinh extends java.lang.Record)
public record A(int x) extends Base {} // ✗ loi bien dich

// 2. field instance phai khai bao trong header — khong duoc them field khac
public record B(int x) {
    private int y; // ✗ loi
}

// 3. record la IMMUTABLE — khong co setter
Person p = new Person("An", 25, "an@mail.com");
p.setAge(26); // ✗ khong ton tai
