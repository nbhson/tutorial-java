import java.io.File;
import java.io.FileReader;

public class FilenotFound_Demo {

   public static void main(String args[]) {		
      File file = new File("E://file.txt");
      FileReader fr = new FileReader(file); 
   }
}

// C:\>javac FilenotFound_Demo.java
// FilenotFound_Demo.java:8: error: unreported exception FileNotFoundException; must be caught or declared to be thrown
//       FileReader fr = new FileReader(file);
