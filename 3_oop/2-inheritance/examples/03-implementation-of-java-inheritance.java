   class Animal {
       void makeSound() {
           System.out.println("Animal sound");
       }
   }

   class Dog extends Animal {
       void makeSound() {
           System.out.println("Bark");
       }

       void wagTail() {
           System.out.println("Wagging tail");
       }
   }

   public class Main {
       public static void main(String[] args) {
           Animal myDog = new Dog(); // Biến tham chiếu của lớp cha giữ đối tượng của lớp con
           myDog.makeSound(); // Gọi phương thức của lớp cha (hoặc lớp con nếu được ghi đè)
           // myDog.wagTail(); // Lỗi biên dịch, không thể gọi phương thức của lớp con


            // RECOMMEND
            Dog myDog = new Dog();
            myDog.makeSound(); // Gọi phương thức của lớp con
            myDog.wagTail();   // Gọi phương thức của lớp con    
       }
   }
