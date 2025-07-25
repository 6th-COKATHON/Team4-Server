package com.example.team4.repository;

import com.example.team4.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // Additional query methods can be defined here if needed

    //모든 채팅방 조회를 위한 메소드
    List<ChatRoom> findAll();


}
