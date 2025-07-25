package com.example.team4.dto.request;

import com.example.team4.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "이메일은 필수입니다")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

    public User toEntity(String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
