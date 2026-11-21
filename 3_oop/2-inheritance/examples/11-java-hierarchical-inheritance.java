// Base class
class One {
  public void printOne() {
    System.out.println("printOne() Method of Class One");
  }
}

// Derived class 1
class Two extends One {
  public void printTwo() {
    System.out.println("Two() Method of Class Two");
  }
}

// Derived class 2
class Three extends One {
  public void printThree() {
    System.out.println("printThree() Method of Class Three");
  }
}

// Testing CLass
public class Main {
  public static void main(String args[]) {
    Two obj1 = new Two();
    Three obj2 = new Three();

    //All classes can access the method of class One
    obj1.printOne();
    obj2.printOne();
  }
}
