import java.util.function.*;

// 1. Predicate<T>: T → boolean
Predicate<String> isEmpty = s -> s.isEmpty();
System.out.println(predicate.test(""));  // true

// 2. Consumer<T>: T → void
Consumer<String> printer = s -> System.out.println(s);
printer.accept("Hello");  // In ra console

// 3. Function<T, R>: T → R
Function<String, Integer> length = s -> s.length();
System.out.println(length.apply("Java"));  // 4

// 4. Supplier<T>: () → T
Supplier<List<String>> listSupplier = () -> new ArrayList<>();
List<String> list = listSupplier.get();
