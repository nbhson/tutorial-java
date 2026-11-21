class Logger {
  private String format;

  private String getFormat() {
    return this.format;
  }

  private void setFormat(String format) {
    this.format = format;
  }
}


public class Main {
  public static void main(String[] args) {
    // Creating an object 
    Logger log = new Logger();
    // Setting the value
    log.setFormat("Text");
    // Getting the value
    System.out.println(log.getFormat());


    // Main.java:18: error: setFormat(String) has private access in Logger
    // log.setFormat("Text");
  }
}
