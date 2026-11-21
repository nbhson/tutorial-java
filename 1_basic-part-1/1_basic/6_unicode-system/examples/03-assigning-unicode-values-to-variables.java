package com.tutorialspoint;

public class UnicodeCharacterDemo {
   public static void main(String[] args) {
      // Storing Unicode characters using escape sequences
      char letterA = '\u0041';
      char letterSigma = '\u03A3';
      char copyrightSymbol = '\u00A9';
      // Storing Unicode characters directly
      char letterZ = 'Z';
      char letterOmega = 'Ω';
      char registeredSymbol = '®';
      // Printing the stored Unicode characters
      System.out.println("Stored Unicode Characters using Escape Sequences:");
      System.out.println("Letter A: " + letterA); //  Letter A: A
      System.out.println("Greek Capital Letter Sigma: " + letterSigma); //  Greek Capital Letter Sigma: Σ
      System.out.println("Copyright Symbol: " + copyrightSymbol); //  Copyright Symbol: ©
      System.out.println("\nStored Unicode Characters Directly:");
      System.out.println("Letter Z: " + letterZ); //  Letter Z: Z
      System.out.println("Greek Capital Letter Omega: " + letterOmega); //  Greek Capital Letter Omega: Ω
      System.out.println("Registered Symbol: " + registeredSymbol); // Registered Symbol: ®

   }
}
