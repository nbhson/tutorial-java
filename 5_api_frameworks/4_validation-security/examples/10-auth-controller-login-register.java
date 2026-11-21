package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // Trong thực tế: query user từ database và so sánh password
        // Ví dụ đơn giản:
        if ("user@example.com".equals(email) && "Password123".equals(password)) {
            String token = jwtService.generateToken(email, "USER");
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        // Hash password trước khi lưu
        String hashedPassword = passwordEncoder.encode(request.password());
        // Lưu user vào database...
        return ResponseEntity.ok("Đăng ký thành công");
    }
}
