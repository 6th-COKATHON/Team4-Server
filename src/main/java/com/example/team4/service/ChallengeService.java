package com.example.team4.service;

import com.example.team4.domain.Challenge;
import com.example.team4.domain.Cycle;
import com.example.team4.domain.User;
import com.example.team4.dto.request.ChallengeCreateRequest;
import com.example.team4.dto.request.ChallengeUpdateRequest;
import com.example.team4.dto.response.ChallengeResponse;
import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.user.UserNotFoundException;
import com.example.team4.repository.ChallengeRepository;
import com.example.team4.repository.CycleRepository;
import com.example.team4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.example.team4.global.exception.challenge.ChallengeErrorCode.*;
import static com.example.team4.global.exception.user.UserErrorCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeService {

    private final ImageService imageService;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final CycleRepository cycleRepository;

    // 챌린지 생성
    public void createChallenge(Long userId, ChallengeCreateRequest request, MultipartFile image) {
        request.validate();
        // user 존재하는지
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND));
        // cycle status가 Complete가 아닌 사이클 찾기
        Cycle cycle = cycleRepository.findByStatusNot(Cycle.Status.COMPLETE)
                .orElseThrow(() -> new AppException(CYCLE_NOT_FOUND));

        String imageUrl = null;
        if(image != null) {
            imageUrl = imageService.uploadImage(image);
        }
        int order = (int) (challengeRepository.countByCycleId(cycleId) + 1);
        challengeRepository.save(Challenge.of(cycle, user, request, imageUrl, order));
    }


    // 챌린지 수정(챌린지 인증)
    // 챌린지 종료되면서 챌린지 상태 변경
    public void updateChallenge(Long userId, Long challengeId, ChallengeUpdateRequest request, MultipartFile image) {
        // user 존재하는지
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND));
        // 챌린지 존재하는지
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new AppException(CHALLENGE_NOT_FOUND));

        // 챌린지 작성자와 요청한 user가 같은지
        if (!challenge.getUser().getId().equals(user.getId())) {
            throw new AppException(NOT_CHALLENGE_WRITER);
        }
        request.validate();

        String imageUrl = null;
        if(image != null) {
            imageUrl = imageService.uploadImage(image);
        }

        challenge.update(request, imageUrl);
    }

    // 챌린지 상세 조회
    public ChallengeResponse getChallengeDetail(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new AppException(CHALLENGE_NOT_FOUND));
        return ChallengeResponse.from(challenge);
    }

}
