public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndStatus(String username, String status);
}

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ============ Tìm user theo ID ============
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    // ============ Tìm hoặc tạo mới ============
    public User findOrCreateUser(String email) {
        return userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                return userRepository.save(newUser);
            });
    }

    // ============ Chain operations ============
    public String getUserCity(Long userId) {
        return userRepository.findById(userId)
            .map(User::getAddress)
            .map(Address::city)
            .map(String::toUpperCase)
            .orElse("UNKNOWN");
    }

    // ============ Xử lý nhiều Optional ============
    public String getContactInfo(Long userId) {
        return userRepository.findById(userId)
            .flatMap(this::findPrimaryContact)  // Optional<Contact>
            .map(Contact::getPhone)
            .orElse("No contact info");
    }

    private Optional<Contact> findPrimaryContact(User user) {
        return user.getContacts().stream()
            .filter(Contact::isPrimary)
            .findFirst();
    }
}
