public class Animal {
    public void eat() {
        System.out.println("Animal eats");
    }
}

// Động vật có vú
public class Mammal extends Animal {
    public void breathe() {
        System.out.println("Mammal breathes");
    }
}

public class Dog extends Mammal {
    public void bark() {
        System.out.println("Dog barks");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog myDog = new Dog();
        myDog.eat();    // Gọi phương thức từ Animal
        myDog.breathe(); // Gọi phương thức từ Mammal
        myDog.bark();   // Gọi phương thức từ Dog
    }
}
