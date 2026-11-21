import java.util.Optional;

// 1. Optional.of - giá trị không được null (NPE nếu null)
Optional<String> opt1 = Optional.of("Hello");

// 2. Optional.empty - không có giá trị
Optional<String> opt2 = Optional.empty();

// 3. Optional.ofNullable - chấp nhận cả null
String maybeNull = null;
Optional<String> opt3 = Optional.ofNullable(maybeNull);  // Optional.empty
