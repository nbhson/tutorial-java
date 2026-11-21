// chi 3 class duoc phep implements Shape
public sealed interface Shape permits Circle, Rectangle, Triangle {}

// bat buoc: class con phai la final, sealed, hoac non-sealed
public record Circle(double radius) implements Shape {
    public double area() { return Math.PI * radius * radius; }
}

public record Rectangle(double width, double height) implements Shape {
    public double area() { return width * height; }
}

public non-sealed class Triangle implements Shape {  // mo cua lai cho ai cung extends
    public double area() { return 0; } // demo
}
