package com.example.team4.domain;

public enum ChallengeStatus {
    NOT_STARTED("시작 전"),
    ONGOING("진행 중"),
    COMPLETED("완료");

    private final String description;

    ChallengeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
