package com.example.team4.service;

import com.example.team4.domain.User;
import com.example.team4.domain.chat.Chat;
import com.example.team4.domain.chat.ChatRoom;
import com.example.team4.dto.response.ChatDetailResponse;
import com.example.team4.dto.response.ChatRoomDetailResponse;
import com.example.team4.repository.ChatRepository;
import com.example.team4.repository.ChatRoomRepository;
import com.example.team4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatDetailResponse> getChatsByChatroomId(Long chatroomId) {
        //채팅방에 있는 채팅들 조회
        List<Chat> chats = chatRepository.findAllByChatRoomId(chatroomId);

        //Chat 객체를 ChatDetailResponse로 변환
        return chats.stream()
                .map(chat -> new ChatDetailResponse(
                        chat.getId(),
                        chat.getContent(),
                        chat.getUser().getNickname(),
                        chat.getChatRoom().getName()))
                .toList();
    }

    public ChatDetailResponse createChat(Long userId, Long chatroomId, String message) {
        //채팅방에 채팅 생성 로직
        Chat chat = new Chat();
        chat.setContent(message);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        ChatRoom chatRoom = chatRoomRepository.findById(chatroomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found with id: " + chatroomId));


        Chat savedChat = chatRepository.save(chat);

        return new ChatDetailResponse(
                savedChat.getId(),
                savedChat.getContent(),
                savedChat.getUser().getNickname(),
                savedChat.getChatRoom().getName());
    }

    public List<ChatRoomDetailResponse> getAllChatrooms() {
        //사용자가 참여한 채팅방 조회
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();

//        List<ChatRoom> chatRooms = List.of(
//                new ChatRoom(1L, "General", "General discussion"),
//                new ChatRoom(2L, "Sports", "Sports talk"),
//                new ChatRoom(3L, "Technology", "Tech discussions")
//        );

        //ChatRoom 객체를 ChatRoomDetailResponse로 변환
        return chatRooms.stream()
                .map(chatRoom -> new ChatRoomDetailResponse(
                        chatRoom.getId(),
                        chatRoom.getName(),
                        chatRoom.getDescription(),
                        getLastChatDetailResponse(chatRoom.getId())))
                .toList();

    }

    private ChatDetailResponse getLastChatDetailResponse(Long chatRoomId) {
        //가장 최근 채팅 조회
        Chat chat = chatRepository.findTopByChatRoomIdOrderByIdDesc(chatRoomId);
        if (chat == null) {
            return null; // No chats found in this chat room
        }
        return new ChatDetailResponse(
                chat.getId(),
                chat.getContent(),
                chat.getUser().getNickname(),
                chat.getChatRoom().getName());
    }


}
