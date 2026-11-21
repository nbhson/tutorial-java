// Sealed interface - chỉ các class được phép implement
public sealed interface Shape 
    permits Circle, Rectangle, Triangle {
    double area();
}

// Các permitted subclasses
public record Circle(double radius) implements Shape {
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

public record Rectangle(double width, double height) implements Shape {
    @Override
    public double area() {
        return width * height;
    }
}

public record Triangle(double base, double height) implements Shape {
    @Override
    public double area() {
        return 0.5 * width * height;
    }
}

// Lưu ý: Circle, Rectangle, Triangle phải là final, sealed, hoặc non-sealed
// final: không cho kế thừa nữa
// sealed: tiếp tục hạn chế
// non-sealed: mở lại cho mọi class
