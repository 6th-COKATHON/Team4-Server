package com.example.team4.controller;

import com.example.team4.dto.request.CycleUpdateRequest;
import com.example.team4.dto.response.CompleteCycleResponse;
import com.example.team4.dto.response.CycleResponse;
import com.example.team4.global.dto.DataResponse;
import com.example.team4.global.dto.ErrorResponse;
import com.example.team4.global.security.CustomUserDetails;
import com.example.team4.service.CycleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/cycles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "ChallengesController", description = "Challenges 관련 API")
public class CycleController {
    private final CycleService cycleService;

    @Operation(summary = "사이클의 챌린지 목록 조회", description = "주어진 사이클 ID에 속한 챌린지 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사이클을 찾을 수 없음 (CHALLENGE-001)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("")
    public ResponseEntity<DataResponse<CycleResponse>> getChallengesByCycleId() {
        CycleResponse challengesByCycleId = cycleService.getChallengesByCycleId();
        return ResponseEntity.ok(DataResponse.from(challengesByCycleId));
    }

    @Operation(summary = "사이클 종료(업데이트)", description = "주어진 사이클을 이미지와 함께 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사이클 업데이트 성공"),
            @ApiResponse(responseCode = "404", description = "사이클을 찾을 수 없음 (CHALLENGE-001)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/{cycleId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateChallenge(@PathVariable Long cycleId,
                                                @ModelAttribute CycleUpdateRequest request) {
        cycleService.updateChallenge(cycleId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "완료된 사이클 목록 조회", description = "사용자가 완료한 사이클 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "완료된 사이클 목록 조회 성공")
    })
    @GetMapping("/completed")
    public ResponseEntity<DataResponse<List<CompleteCycleResponse>>> getCompletedCycles() {
        List<CompleteCycleResponse> response = cycleService.getCompletedCycles();
        return ResponseEntity.ok(DataResponse.from(response));
    }
}
