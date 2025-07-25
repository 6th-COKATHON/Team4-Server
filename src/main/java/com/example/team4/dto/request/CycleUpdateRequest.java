package com.example.team4.dto.request;

import com.example.team4.global.exception.AppException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static com.example.team4.global.exception.challenge.ChallengeErrorCode.MISSING_INPUT;

@Getter
@Builder
public class CycleUpdateRequest {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    @Schema(description = "이미지 파일 (선택)", type = "string", format = "binary")
    private MultipartFile image;

    public void validate() {
        if (title == null || title.isBlank() || startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new AppException(MISSING_INPUT);
        }
    }
}
