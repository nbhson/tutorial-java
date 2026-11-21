// ✗ ai cung co the extends Shape — khong the kiem soat
public interface Shape { double area(); }

public class Circle implements Shape { /* ... */ }
// ... va ben duoi, mot ai do viet:
public class TriangleThatBreaksEverything implements Shape { /* ... */ }
