List<Optional<String>> optionals = List.of(
    Optional.of("Java"),
    Optional.empty(),
    Optional.of("Spring")
);

// flatMap để "làm phẳng" optionals
List<String> values = optionals.stream()
    .flatMap(Optional::stream)  // Java 9+
    .toList();  // [Java, Spring]

// Tìm phần tử với Stream + Optional
Optional<User> admin = users.stream()
    .filter(u -> u.role().equals("ADMIN"))
    .findFirst();

admin.ifPresentOrElse(
    u -> System.out.println("Found: " + u.name()),
    () -> System.out.println("Not found")
);
