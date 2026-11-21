public class ShapeCalculator {

    // Pattern matching cho switch (Java 21)
    public static double area(Shape shape) {
        return switch (shape) {
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
            case Triangle t -> 0.5 * t.base() * t.height();
            // Không cần default! Compiler biết đã cover hết
        };
    }

    // instanceof pattern matching (Java 16+)
    public static String describe(Shape shape) {
        if (shape instanceof Circle c) {
            return "Circle with radius " + c.radius();
        } else if (shape instanceof Rectangle r) {
            return "Rectangle " + r.width() + "x" + r.height();
        }
        return "Unknown shape";
    }
}
