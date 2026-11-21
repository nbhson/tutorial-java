class One {
  public void printOne() {
    System.out.println("printOne() method of One class.");
  }
}

public class Main extends One {
  public static void main(String args[]) {
    // Creating object of the derived class (Main)
    Main obj = new Main();

    // Calling method
    obj.printOne();
  }
}
