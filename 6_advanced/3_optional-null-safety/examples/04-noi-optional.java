// Ví dụ: Tìm user theo email, rồi lấy city
public record Address(String city) {}
public record User(String name, Address address) {}

public class OptionalChaining {

    public static String getUserCity(User user) {
        return Optional.ofNullable(user)
            .map(User::address)      // Optional<Address>
            .map(Address::city)      // Optional<String>
            .map(String::toUpperCase)
            .orElse("UNKNOWN");
    }
}
