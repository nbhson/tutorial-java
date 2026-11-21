class Example {
    static void method(int a) {
        // Phương thức static
        System.out.println("Static method: " + a);
    }

    // Invalid overload: cùng tên và cùng tham số
    void method(int a) {
        // Phương thức non-static
        System.out.println("Non-static method: " + a);
    }
}
