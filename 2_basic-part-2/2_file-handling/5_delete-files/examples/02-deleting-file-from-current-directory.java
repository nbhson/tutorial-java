package com.tutorialspoint;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileTest {
  
   public static void main(String args[]) throws IOException {
      BufferedWriter out = new BufferedWriter (new FileWriter("test.txt"));
      out.write("test data");
      out.close();

      File file = new File("test.txt");
      if(file.exists()) {
         boolean success = file.delete();

         if (success) {
            System.out.println("The file has been successfully deleted."); 
         }else {
            System.out.println("The file deletion failed.");
         }        
      }else {
         System.out.println("The file is not present."); 
      }
   }
}
