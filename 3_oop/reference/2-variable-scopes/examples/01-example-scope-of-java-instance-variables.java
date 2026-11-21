package com.tutorialspoint;

public class Puppy {
   private int puppyAge;
   
   public void setAge( int age ) {
      // access the instance variable and modify it	   
      puppyAge = age;
   }

   public int getAge( ) {
	  // access the instance variable  
      return puppyAge;
   }

   public static void main(String []args) {    
      Puppy myPuppy = new Puppy();
      myPuppy.setAge( 2 );
      System.out.println("Puppy Age :" + myPuppy.getAge() );
   }
}
