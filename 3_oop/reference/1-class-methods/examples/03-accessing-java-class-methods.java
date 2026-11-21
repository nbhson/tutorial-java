package com.tutorialspoint;

class Util {
   public int minimum(int n1, int n2) {
      int min;
      if (n1 > n2)
         min = n2;
      else
         min = n1;

      return min; 
   }
}
public class Tester {
   
   public static void main(String[] args) {
      int a = 11;
      int b = 6;

      Util util = new Util();
	  
      int c = util.minimum(a, b);
      System.out.println("Minimum Value = " + c);
   }
}
