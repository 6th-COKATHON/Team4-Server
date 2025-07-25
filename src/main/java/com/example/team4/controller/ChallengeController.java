package com.example.team4.controller;


import com.example.team4.domain.Challenge;
import com.example.team4.dto.request.ChallengeCreateRequest;
import com.example.team4.dto.response.ChallengeResponse;
import com.example.team4.global.dto.DataResponse;
import com.example.team4.global.exception.AppException;
import com.example.team4.global.security.CustomUserDetails;
import com.example.team4.service.ChallengeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.example.team4.global.exception.challenge.ChallengeErrorCode.CHALLENGE_NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;

    @GetMapping("/api/v1/challenge/{challengeId}")
    public ResponseEntity<DataResponse<ChallengeResponse>> getChallengeDetail(Long challengeId) {
        ChallengeResponse challengeResponse = challengeService.getChallengeDetail(challengeId);

        if (challengeResponse == null) {
            throw new AppException(CHALLENGE_NOT_FOUND);
        }
        return ResponseEntity.ok(DataResponse.from(challengeResponse));
    }


    @PostMapping("/api/v1/challenge")
    public ResponseEntity<DataResponse<?>> createChallenge(
            @RequestPart()
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ChallengeCreateRequest challengeCreateRequest) {
        challengeService.createChallenge(userDetails.getId(), challengeCreateRequest
        return ResponseEntity.status(HttpStatus.CREATED).body(DataResponse.from(createdChallenge));
    }



}
