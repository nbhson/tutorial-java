public record Rect(double width, double height) {
    // field static duoc phep
    public static Rect square(double side) {
        return new Rect(side, side);
    }

    // method instance duoc phep
    public double area() {
        return width * height;
    }
}

Rect r = Rect.square(5);
System.out.println(r.area()); // 25.0
