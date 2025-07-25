package com.example.team4.dto.response;

import com.example.team4.domain.Challenge;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChallengeListResponse {
    private Long challengeId;
    private String title;

    public static ChallengeListResponse from(Challenge challenge) {
        return ChallengeListResponse.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .build();
    }
}
