   class Animal {
       final void move() {
           System.out.println("Animals can move");
       }
   }

   class Dog extends Animal {
       // void move() { // Lỗi biên dịch: không thể ghi đè phương thức final
       //     System.out.println("Dogs can walk and run");
       // }
   }
