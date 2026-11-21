// ❌ Sequential stream cho CPU-intensive
list.stream()
    .map(HeavyCalculation::process)
    .collect(toList());

// ✅ Parallel stream cho large datasets
list.parallelStream()
    .map(HeavyCalculation::process)
    .collect(toList());

// ✅ Hoặc
list.stream()
    .parallel()
    .map(HeavyCalculation::process)
    .collect(toList());

// ⚠️ Chỉ dùng parallel khi:
// - Dataset lớn (100k+ elements)
// - Operations CPU-intensive
// - Không cần order preservation
