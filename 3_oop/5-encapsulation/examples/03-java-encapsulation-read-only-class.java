// Class "Person"
class Person {
  private String name = "Robert";
  private int age = 21;

  // Getter methods
  public String getName() {
    return this.name;
  }

  public int getAge() {
    return this.age;
  }
}

public class Main {
  public static void main(String args[]) {
    // Object to Person class
    Person per = new Person();

    // Getting and printing the values
    System.out.println("Name of the person is: " + per.getName());
    System.out.println("Age of the person is: " + per.getAge());
  }
}
