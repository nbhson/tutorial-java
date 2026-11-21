package com.tutorialspoint;

import java.io.File;
import java.io.IOException;

public class FileTest {
  
   public static void deleteFiles(File dirPath) {
      File filesList[] = dirPath.listFiles(); // mảng chứa tất cả các file và thư mục con trong đường dẫn được chỉ định dirPath.
      for(File file : filesList) {
         if(file.isFile()) {
            file.delete();
         } else {
            deleteFiles(file);
         }
      }
   }
   public static void main(String args[]) throws IOException {
      
      //Creating a File object for directory
      File file = new File("D:\\test");
      
      //List of all files and directories
      deleteFiles(file);
      System.out.println("Files deleted.");
   }
}
