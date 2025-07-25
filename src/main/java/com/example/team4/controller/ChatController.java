package com.example.team4.controller;

import com.example.team4.dto.response.ChatDetailResponse;
import com.example.team4.dto.response.ChatRoomDetailResponse;
import com.example.team4.global.dto.DataResponse;
import com.example.team4.global.security.CustomUserDetails;
import com.example.team4.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ChatController {
    private final ChatService chatService;

    @Operation(summary = "채팅방 목록 조회", description = "사용자가 참여한 모든 채팅방의 목록을 조회합니다.")
    @GetMapping("/api/v1/chatrooms")
    public DataResponse<List<ChatRoomDetailResponse>> getChatrooms(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();

        List<ChatRoomDetailResponse> chatrooms = chatService.getAllChatrooms();

        return DataResponse.from(chatrooms);

    }

    @Operation(summary = "채팅방의 채팅 목록 조회", description = "특정 채팅방에 있는 모든 채팅 메시지를 조회합니다.")
    @GetMapping("/api/v1/chatrooms/{chatroomId}/chats")
    public ResponseEntity<DataResponse<List<ChatDetailResponse>>> getChatsByChatroomId(
            @AuthenticationPrincipal CustomUserDetails userDetails, Long chatroomId) {
        List<ChatDetailResponse> chats = chatService.getChatsByChatroomId(chatroomId);

        return ResponseEntity.ok(DataResponse.from(chats));
    }

    @Operation(summary = "채팅 생성", description = "특정 채팅방에 새로운 채팅 메시지를 생성합니다.")
    @PostMapping("/api/v1/chatrooms/{chatroomId}/chats")
    public ResponseEntity<DataResponse<?>> createChat(@AuthenticationPrincipal CustomUserDetails userDetails, Long chatroomId, String message) {
        Long userId = userDetails.getId();
        ChatDetailResponse chatDetailResponse = chatService.createChat(userId, chatroomId, message);
        return ResponseEntity.ok(DataResponse.from(chatDetailResponse));
    }


}
