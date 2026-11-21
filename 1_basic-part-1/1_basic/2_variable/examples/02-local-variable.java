public class Test {
   public void pupAge() {
      int age; // Test.java:4:variable number might not have been initialized
      age = age + 7;
      System.out.println("Puppy age is : " + age);
   }

   public static void main(String args[]) {
      Test test = new Test();
      test.pupAge();
   }
}
