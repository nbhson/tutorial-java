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
