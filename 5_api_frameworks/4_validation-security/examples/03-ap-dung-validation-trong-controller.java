@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {
        // Nếu validation fail, MethodArgumentNotValidException sẽ được throw
        // và GlobalExceptionHandler sẽ xử lý
        return ResponseEntity.ok("Đăng ký thành công: " + request.email());
    }
}
