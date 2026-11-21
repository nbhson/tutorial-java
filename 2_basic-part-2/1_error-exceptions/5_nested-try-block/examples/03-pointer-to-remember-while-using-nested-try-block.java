try {
   int a[] = new int[2];
   try {
      int b = 0;
      int c = 1/b;
   } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Child - Exception thrown: " + e);
   }
   System.out.println("Access element three :" + a[3]);
}
catch (Exception e) {
   System.out.println("Parent - Exception thrown: " + e);
}

// Parent - Exception thrown: java.lang.ArithmeticException: / by zero
