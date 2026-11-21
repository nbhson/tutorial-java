// This example is to use Try with Resources 
// with multiple  Resources
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    // try block with multiple resources
    try (
      FileReader fileReader = new FileReader("file1.txt"); 
      BufferedReader bufferedReader = new BufferedReader(fileReader); 
      FileWriter fileWriter = new FileWriter("file2.txt"); 
      PrintWriter printWriter = new PrintWriter(fileWriter)
    ) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        // Read content line by line and write it 
        // to the output (file2.txt) file
        printWriter.println(line);
      }
      System.out.println("Content copied.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
