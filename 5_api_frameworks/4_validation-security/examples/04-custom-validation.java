// Tạo annotation tùy chỉnh
package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Mật khẩu không đủ mạnh";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
