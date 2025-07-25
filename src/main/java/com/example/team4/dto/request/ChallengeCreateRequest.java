package com.example.team4.dto.request;

import com.example.team4.domain.Challenge;
import com.example.team4.domain.User;
import com.example.team4.global.exception.AppException;
import lombok.Builder;
import lombok.Getter;

import static com.example.team4.global.exception.challenge.ChallengeErrorCode.MISSING_INPUT;

@Getter
@Builder
public class ChallengeCreateRequest {
    private String title;
    private String content;
    private String rule;

    public void validate() {
        if (title == null || title.isBlank() || content == null || content.isBlank()
        || rule == null || rule.isBlank()) {
            throw new AppException(MISSING_INPUT);
        }
    }
}
