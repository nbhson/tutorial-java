// ✗ Pojo day du: hon 70 dong chi de luu 3 truong
public class Person {
    private final String name;
    private final int age;
    private final String email;

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName()  { return name; }
    public int    getAge()   { return age; }
    public String getEmail() { return email; }

    @Override public boolean equals(Object o) { /* ... */ }
    @Override public int hashCode() { /* ... */ }
    @Override public String toString() { /* ... */ }
}
