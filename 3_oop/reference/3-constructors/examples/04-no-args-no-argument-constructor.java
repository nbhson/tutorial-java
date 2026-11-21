public class Main {
  int num1;
  int num2;

  // Creating no-args constructor
  Main() {
    num1 = -1;
    num2 = -1;
  }

  public static void main(String[] args) {
    // no-args constructor will invoke
    Main obj_x = new Main();

    // Printing the values
    System.out.println("num1 : " + obj_x.num1);
    System.out.println("num2 : " + obj_x.num2);
  }
}
