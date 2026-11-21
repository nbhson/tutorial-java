// Class "Person"
class Person {
  private String name;
  private int age;

  // Setter Methods
  public void setName(String name) {
    this.name = name;
  }
  public void setAge(int age) {
    this.age = age;
  }
}

public class Main {
  public static void main(String args[]) {
    // Object to Person class
    Person per = new Person();

    // Setting the values
    per.setName("Robert");
    per.setAge(21);
  }
}
