package com.example.team4.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    @Setter
    private String nickname;


    public User of(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

}