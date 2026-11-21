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
