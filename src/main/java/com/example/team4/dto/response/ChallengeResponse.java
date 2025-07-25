package com.example.team4.dto.response;

import com.example.team4.domain.Challenge;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ChallengeResponse {
    public String title;
    public String content;
    public String rule;
    public LocalDate startDate;
    public LocalDate endDate;
    public String challengeImage;
    public String proofImage;

    public static ChallengeResponse from(Challenge challenge) {
        return ChallengeResponse.builder()
                .title(challenge.getTitle())
                .content(challenge.getContent())
                .rule(challenge.getRule())
                .startDate(challenge.getStartDate() == null ? null : challenge.getStartDate())
                .endDate(challenge.getEndDate() == null ? null : challenge.getEndDate())
                .challengeImage(challenge.getChallengeImage())
                .proofImage(challenge.getProofImage() == null ? null : challenge.getProofImage())
                .build();
    }
}
