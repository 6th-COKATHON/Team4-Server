package com.example.team4.domain;

import lombok.Getter;

@Getter
public enum CycleStatus {
    NOT_STARTED("시작 전"),
    ONGOING("진행 중"),
    COMPLETED("완료");

    private final String description;

    CycleStatus(String description) {
        this.description = description;
    }

}
