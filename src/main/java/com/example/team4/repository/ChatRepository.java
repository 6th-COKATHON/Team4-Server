package com.example.team4.repository;

import com.example.team4.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    //findAllByChatroomId
    List<Chat> findAllByChatRoomId(Long chatroomId);

    //findTopByOrderByIdDesc ChatRoom별로
    Chat findTopByChatRoomIdOrderByIdDesc(Long chatroomId);

}
