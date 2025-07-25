package com.example.team4.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatRoomDetailResponse {
    private Long chatRoomId;
    private String chatRoomName;
    private String chatRoomDescription;
    private ChatDetailResponse lastChat;

}
