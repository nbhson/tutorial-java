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
