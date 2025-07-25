package com.example.team4.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDetailResponse {
    private Long chatId;
    private String chatContent;
    private String senderName;
    private String chatRoomName;

}
