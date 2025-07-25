package com.example.team4.dto.response;

import com.example.team4.domain.Cycle;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CompleteCycleResponse {
    private String image;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    public static CompleteCycleResponse from(Cycle cycle) {
        return CompleteCycleResponse.builder()
                .image(cycle.getImage())
                .title(cycle.getTitle())
                .startDate(cycle.getStartDate())
                .endDate(cycle.getEndDate())
                .build();
    }
}
