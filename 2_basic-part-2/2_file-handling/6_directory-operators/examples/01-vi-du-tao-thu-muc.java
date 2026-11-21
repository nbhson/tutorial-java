package com.tutorialspoint;

import java.io.File;

public class DirectoryTest {
   public static void main(String args[]) {
      String dirname = "/tmp/user/java/bin";
      File directory = new File(dirname);

      // Create directory now.
      directory.mkdirs();

      // create new file object
      File file = new File("/tmp/user/java/bin");

      System.out.println(file.exists());      
   }
}
