package com.example.team4.controller;


import com.example.team4.domain.Challenge;
import com.example.team4.dto.request.ChallengeCreateRequest;
import com.example.team4.dto.request.ChallengeUpdateRequest;
import com.example.team4.dto.response.ChallengeResponse;
import com.example.team4.global.dto.DataResponse;
import com.example.team4.global.dto.ErrorResponse;
import com.example.team4.global.exception.AppException;
import com.example.team4.global.security.CustomUserDetails;
import com.example.team4.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.team4.global.exception.challenge.ChallengeErrorCode.CHALLENGE_NOT_FOUND;

@RestController
@RequestMapping("api/v1/challenges")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "ChallengesController", description = "Challenges 관련 API")
public class ChallengeController {
    private final ChallengeService challengeService;

    @Operation(summary = "챌린지 생성", description = "현재 진행 중인 사이클에 챌린지를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 생성 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 또는 사이클을 찾을 수 없음 (USER-001, CHALLENGE-001)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 존재하는 챌린지입니다. (CHALLENGE-005)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createChallenge(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @ModelAttribute ChallengeCreateRequest request) {
        challengeService.createChallenge(userDetails.getId(), request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "챌린지 수정", description = "인증한 사용자만 자신의 챌린지를 수정할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 수정 성공"),
            @ApiResponse(responseCode = "403", description = "작성자가 아님 (CHALLENGE-002)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자 또는 챌린지를 찾을 수 없음 (USER-001, CHALLENGE-001)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/{challengeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateChallenge(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @PathVariable Long challengeId,
                                                @ModelAttribute ChallengeUpdateRequest request) {
        challengeService.updateChallenge(userDetails.getId(), challengeId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "챌린지 상세 조회", description = "챌린지의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "챌린지 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = ChallengeResponse.class))),
            @ApiResponse(responseCode = "404", description = "챌린지를 찾을 수 없음 (CHALLENGE-001)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{challengeId}")
    public ResponseEntity<DataResponse<ChallengeResponse>> getChallengeDetail(@PathVariable Long challengeId) {
        ChallengeResponse response = challengeService.getChallengeDetail(challengeId);
        return ResponseEntity.ok(DataResponse.from(response));
    }


}
