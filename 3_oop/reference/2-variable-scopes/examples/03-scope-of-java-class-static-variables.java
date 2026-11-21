package com.tutorialspoint;

public class Puppy {
   public static String BREED="Bulldog";

   public static void main(String []args) {    
      Puppy myPuppy = new Puppy();
      System.out.println("Breed :" + Puppy.BREED );
	  
      // local variables
      int a = 10;
      int b = 20;
      
      int c = a + b;
      
      System.out.println("c: " + c);
   }
}
