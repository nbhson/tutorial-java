# Stream API và Lambda Expressions

## Giới thiệu Functional Programming

**Lambda Expressions** và **Stream API** được giới thiệu từ Java 8, mang Functional Programming vào Java. Đây là những tính năng quan trọng nhất để xử lý collections hiện đại.

> **Yêu cầu**: Java 8+ (một số tính năng nâng cao cần Java 16+)

## Lambda Expressions

Lambda là một cách viết ngắn gọn cho anonymous function (hàm không tên).

### Cú pháp

```java
// Cú pháp cơ bản
(parameters) -> expression
(parameters) -> { statements; }

// Ví dụ
Runnable r1 = () -> System.out.println("Hello");           // Không tham số
Consumer<String> c1 = s -> System.out.println(s);           // 1 tham số
BinaryOperator<Integer> add = (a, b) -> a + b;             // 2 tham số
Comparator<String> cmp = (s1, s2) -> s1.length() - s2.length();
```

### So sánh trước/sau Lambda

```java
// ============ TRƯỚC Java 8: Anonymous Inner Class ============
Comparator<String> comparator = new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
};

// ============ SAU Java 8: Lambda ============
Comparator<String> comparator = (s1, s2) -> s1.length() - s2.length();

// ============ Method Reference (thậm chí ngắn hơn) ============
Comparator<String> comparator = Comparator.comparingInt(String::length);
```

## Functional Interfaces

Functional interface là interface có **đúng 1 abstract method**. Java 8 cung cấp 4 interfaces chính trong `java.util.function`.

### 4 Functional Interfaces chính

```java
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
```

### Các Functional Interfaces khác

| Interface | Signature | Mô tả |
|-----------|-----------|-------|
| `Predicate<T>` | `T → boolean` | Kiểm tra điều kiện |
| `Consumer<T>` | `T → void` | Nhận giá trị, không trả về |
| `Supplier<T>` | `() → T` | Chỉ trả về giá trị |
| `Function<T,R>` | `T → R` | Chuyển đổi T thành R |
| `BiFunction<T,U,R>` | `(T,U) → R` | 2 tham số vào, 1 ra |
| `UnaryOperator<T>` | `T → T` | Function với cùng input/output type |
| `BinaryOperator<T>` | `(T,T) → T` | 2 tham số cùng type, 1 ra |

## Stream API

Stream là một "pipeline" các operations để xử lý dữ liệu theo cách declarative.

### Luồng xử lý

```
Source → Intermediate Operations → Terminal Operation
(List)   (filter, map, sorted)     (collect, forEach, reduce)
```

### Tạo Stream

```java
import java.util.stream.*;
import java.util.*;

// Từ Collection
List<String> list = List.of("Java", "Spring", "Boot");
Stream<String> stream1 = list.stream();

// Từ Array
String[] array = {"a", "b", "c"};
Stream<String> stream2 = Arrays.stream(array);

// Stream.of
Stream<Integer> stream3 = Stream.of(1, 2, 3, 4, 5);

// Stream.generate (infinite stream)
Stream<Double> randoms = Stream.generate(Math::random).limit(5);

// Stream.iterate
Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2).limit(10);
```

## Các Operations

### Intermediate Operations (Lazy)

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// filter: Lọc theo điều kiện
Stream<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0);  // [2, 4, 6, 8, 10]

// map: Chuyển đổi từng element
Stream<Integer> squares = numbers.stream()
    .map(n -> n * n);  // [1, 4, 9, 16, 25, ...]

// sorted: Sắp xếp
Stream<Integer> sorted = numbers.stream()
    .sorted(Comparator.reverseOrder());

// distinct: Loại bỏ duplicates
Stream<Integer> unique = Stream.of(1, 2, 2, 3, 3, 3).distinct();  // [1, 2, 3]

// limit/skip: Giới hạn
Stream<Integer> first3 = numbers.stream().limit(3);      // [1, 2, 3]
Stream<Integer> skip3 = numbers.stream().skip(3);         // Bỏ 3 phần tử đầu

// flatMap: "Làm phẳng" nested streams
List<List<Integer>> nested = List.of(List.of(1, 2), List.of(3, 4));
Stream<Integer> flattened = nested.stream()
    .flatMap(List::stream);  // [1, 2, 3, 4]
```

### Terminal Operations

```java
// collect: Thu thập kết quả
List<Integer> result = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());

// Java 16+: toList() trực tiếp
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)
    .toList();  // Shorter version

// forEach: Xử lý từng phần tử
numbers.stream().forEach(System.out::println);

// reduce: Gộp thành 1 giá trị
int sum = numbers.stream()
    .reduce(0, Integer::sum);  // Tính tổng

// count, min, max
long count = numbers.stream().count();
Optional<Integer> max = numbers.stream().max(Integer::compareTo);

// anyMatch, allMatch, noneMatch
boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);    // true
boolean allPositive = numbers.stream().allMatch(n -> n > 0);     // false
boolean noNegative = numbers.stream().noneMatch(n -> n < 0);     // true

// findFirst, findAny
Optional<Integer> first = numbers.stream().findFirst();

// toArray
Integer[] arr = numbers.stream().toArray(Integer[]::new);
```

## Ví dụ thực tế

### Xử lý List Products

```java
public record Product(String name, String category, double price, int stock) {}

public class StreamExamples {

    public static void main(String[] args) {
        List<Product> products = List.of(
            new Product("Laptop", "Electronics", 25000, 10),
            new Product("Phone", "Electronics", 15000, 5),
            new Product("T-Shirt", "Clothing", 150, 100),
            new Product("Shoes", "Clothing", 500, 50),
            new Product("Book", "Books", 80, 200)
        );

        // 1. Lọc sản phẩm giá > 100, sắp xếp theo giá
        List<Product> affordable = products.stream()
            .filter(p -> p.price() < 1000)
            .sorted(Comparator.comparingDouble(Product::price))
            .toList();
        System.out.println("Sản phẩm giá < 500: " + affordable);

        // 2. Lấy danh sách tên sản phẩm (uppercase)
        List<String> names = products.stream()
            .map(Product::name)
            .toList();

        // 3. Tính tổng giá trị tồn kho
        double totalValue = products.stream()
            .mapToDouble(p -> p.price() * p.quantity())
            .sum();

        // 4. Group by category
        Map<String, List<Product>> byCategory = products.stream()
            .collect(Collectors.groupingBy(Product::category));

        // 5. Tìm sản phẩm có giá cao nhất
        Optional<Product> mostExpensive = products.stream()
            .max(Comparator.comparingDouble(Product::price));

        // 6. Đếm theo category
        Map<String, Long> countByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::category,
                Collectors.counting()
            ));

        // 7. Partition (chia 2 nhóm theo điều kiện)
        Map<Boolean, List<Product>> partition = products.stream()
            .collect(Collectors.partitioningBy(p -> p.price() < 1000));

        // 8. Chuỗi nối từ danh sách tên
        String nameStr = products.stream()
            .map(Product::name)
            .collect(Collectors.joining(", ", "[", "]"));
        // "[Laptop, T-Shirt, Shoes, Book]"
    }
}
```

### Parallel Streams

```java
// Sequential stream (1 thread)
long count1 = numbers.stream()
    .filter(n -> n % 2 == 0)
    .count();

// Parallel stream (nhiều threads)
long count2 = numbers.parallelStream()
    .filter(n -> n % 2 == 0)
    .count();

// Khi nào dùng parallel?
// ✅ Dataset lớn (hàng trăm nghìn+), CPU-bound tasks
// ❌ Dataset nhỏ, I/O-bound, cần ordering
```

## Optional với Stream

```java
// Optional trong Stream pipeline
Optional<String> firstLongName = names.stream()
    .filter(name -> name.length() > 5)
    .findFirst();

// Xử lý Optional
firstLongName.ifPresent(name -> System.out.println("Found: " + name));

// Map và flatMap trong Stream
Map<String, List<String>> groupedNames = people.stream()
    .collect(Collectors.groupingBy(
        Person::getCity,
        Collectors.mapping(Person::getName, Collectors.toList())
    ));
```

## Best Practices

1. **Prefer method references** khi có thể: `String::toUpperCase` thay vì `s -> s.toUpperCase()`
2. **Tránh side effects**: Stream không nên mutate external state
3. **Dùng Collectors utilities**: `toList()`, `joining()`, `groupingBy()`
4. **Lazy evaluation**: Intermediate ops không chạy cho đến khi có terminal op
5. **Không reuse Stream**: Mỗi stream chỉ được consume 1 lần
6. **Parallel stream cẩn thận**: Chỉ dùng cho CPU-bound + dataset lớn

## Tài liệu tham khảo

- [Java Stream API Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/package-summary.html)
- [Baeldung Java Stream API](https://www.baeldung.com/java-8-streams)
- [Baeldung Lambda Expressions](https://www.baeldung.com/java-8-lambda-expressions-tutorial)

---

**Tiếp theo**: [3. Optional và Null Safety](../3_optional-null-safety/)
