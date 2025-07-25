package com.example.team4.domain.chat;


import com.example.team4.domain.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @jakarta.persistence.Id
    private Long id;

    private String name;

    private String description;


}
