public static void main(String args[]) {
      int a = 3;
      int b = 0;
      System.out.println("result:" + divide(a,b));
   }

   private static int divide(int a, int b) {
      if(b == 0) {
         throw new IllegalArgumentException("second argument cannot be zero.");
      }
      return a / b;
   }
