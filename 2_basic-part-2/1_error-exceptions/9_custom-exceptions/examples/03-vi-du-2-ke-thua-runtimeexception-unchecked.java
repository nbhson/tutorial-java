package com.tutorialspoint;

public class BankDemo {

   public static void main(String [] args) {
      CheckingAccount c = new CheckingAccount(101); // question - different between Exception & Runtime
      System.out.println("Depositing $500...");
      c.deposit(500.00);
      
      System.out.println("\nWithdrawing $100...");
      c.withdraw(100.00);
      System.out.println("\nWithdrawing $600...");

   }
}

class CheckingAccount {
   private double balance;
   private int number;
   
   public CheckingAccount(int number) {
      this.number = number;
   }
   
   public void deposit(double amount) {
      balance += amount;
   }
   
   public void withdraw(double amount) {
      if(amount <= balance) {
         balance -= amount;
      }else {
         double needs = amount - balance;
         throw new InsufficientFundsException(needs);
      }
   }
   
   public double getBalance() {
      return balance;
   }
   
   public int getNumber() {
      return number;
   }
}

class InsufficientFundsException extends RuntimeException {
   private double amount;
   
   public InsufficientFundsException(double amount) {
      this.amount = amount;
   }
   
   public double getAmount() {
      return amount;
   }
}
