public class PatternMatchingDemo {

    // Switch với pattern matching
    public static String process(Object obj) {
        return switch (obj) {
            case Integer i when i > 0 -> "Positive number: " + i;
            case Integer i -> "Non-positive number: " + i;
            case String s -> "String: " + s.toUpperCase();
            case List<?> list -> "List with " + list.size() + " elements";
            case null -> "Null value";
            default -> "Unknown type";
        };
    }

    // Record patterns (Java 21)
    public record Point(int x, int y) {}

    public static int sumCoordinates(Object obj) {
        return switch (obj) {
            case Point(int x, int y) -> x + y;        // Deconstruct record
            case Point p -> p.x() + p.y();             // Traditional access
            default -> 0;
        };
    }

    // Nested patterns
    public record Line(Point start, Point end) {}

    public static boolean isVertical(Object obj) {
        return switch (obj) {
            case Line(Point(var x1, var y1), Point(var x2, var y2)) 
                when x1 == x2 -> true;
            default -> false;
        };
    }
}
