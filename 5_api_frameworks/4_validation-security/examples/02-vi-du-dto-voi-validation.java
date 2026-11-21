package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record RegisterRequest(
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 3, max = 50, message = "Tên phải từ 3 đến 50 ký tự")
    String name,

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    String email,

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu tối thiểu 8 ký tự")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
        message = "Mật khẩu phải chứa chữ thường, chữ hoa và số"
    )
    String password,

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải trong quá khứ")
    LocalDate birthDate,

    @Min(value = 18, message = "Tuổi tối thiểu 18")
    @Max(value = 100, message = "Tuổi tối đa 100")
    Integer age,

    @Pattern(
        regexp = "^[0-9]{10,11}$",
        message = "Số điện thoại phải có 10-11 chữ số"
    )
    String phone
) {}
