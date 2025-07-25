package com.example.team4.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CycleResponse {
    private String cycleStatus;
    private List<ChallengeListResponse> challengeList;

    public static CycleResponse from(String cycleStatus, List<ChallengeListResponse> challengeList) {
        return CycleResponse.builder()
                .cycleStatus(cycleStatus)
                .challengeList(challengeList)
                .build();
    }
}
