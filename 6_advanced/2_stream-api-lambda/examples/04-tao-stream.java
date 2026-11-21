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
