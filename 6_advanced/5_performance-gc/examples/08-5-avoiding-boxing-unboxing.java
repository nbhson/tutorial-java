// ❌ Primitive boxing trong loop
int sum = 0;
for (Integer i : intList) {  // Boxing: int → Integer
    sum += i;  // Unboxing: Integer → int
}

// ✅ Primitive streams
int sum = intList.stream()
    .mapToInt(Integer::intValue)  // Giữ nguyên primitive
    .sum();

// ✅ Hoặc sum trực tiếp
long sum = IntStream.range(1, 1000).sum();
