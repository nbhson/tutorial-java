// 1. Dùng Optional làm return type
public Optional<User> findUserByEmail(String email) { ... }

// 2. Dùng orElse/orElseGet thay vì get()
String city = user.flatMap(User::getAddress)
                  .map(Address::getCity)
                  .orElse("Unknown");

// 3. Dùng ifPresent thay vì isPresent + get
optionalUser.ifPresent(this::sendWelcomeEmail);

// 4. orElseThrow cho bắt buộc
User user = userRepository.findById(id)
    .orElseThrow(() -> new UserNotFoundException(id));

// 5. Dùng map/flatMap để chain operations
Optional<String> result = Optional.ofNullable(input)
    .map(String::trim)
    .filter(s -> !s.isEmpty())
    .map(String::toUpperCase);
