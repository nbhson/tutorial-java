// Example of Java Constructor Overloading
// Creating a Student Class
class Student {
  String name;
  int age;

  // no-args constructor
  Student() {
    this.name = "Unknown";
    this.age = 0;
  }

  // parameterized constructor having one parameter
  Student(String name) {
    this.name = name;
    this.age = 0;
  }

  // parameterized constructor having both parameters
  Student(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public void printDetails() {
    System.out.println("Name : " + this.name);
    System.out.println("Age : " + this.age);
  }
}

public class Main {
  public static void main(String[] args) {
    Student std1 = new Student(); // invokes no-args constructor
    Student std2 = new Student("Jordan"); // invokes parameterized constructor
    Student std3 = new Student("Paxton", 25); // invokes parameterized constructor

    // Printing details
    System.out.println("std1...");
    std1.printDetails();

    System.out.println("std2...");
    std2.printDetails();

    System.out.println("std3...");
    std3.printDetails();
  }
}
