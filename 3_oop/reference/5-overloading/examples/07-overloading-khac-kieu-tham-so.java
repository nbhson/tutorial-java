package com.tutorialspoint;

class Calculator{
   public int add(int a, int b){
      return a + b;
   }
   public double add(double a, double b){
      return a + b;
   }
}

public class Tester {
   public static void main(String args[]){
      Calculator calculator = new Calculator();   
      System.out.println(calculator.add(20, 40));
      System.out.println(calculator.add(20.0, 40.0));
   }
}
