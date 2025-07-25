package com.example.team4.service;

import com.example.team4.domain.Challenge;
import com.example.team4.domain.Cycle;
import com.example.team4.domain.CycleStatus;
import com.example.team4.domain.User;
import com.example.team4.dto.request.ChallengeUpdateRequest;
import com.example.team4.dto.request.CycleUpdateRequest;
import com.example.team4.dto.response.ChallengeListResponse;
import com.example.team4.dto.response.ChallengeResponse;
import com.example.team4.dto.response.CompleteCycleResponse;
import com.example.team4.dto.response.CycleResponse;
import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.user.UserNotFoundException;
import com.example.team4.repository.ChallengeRepository;
import com.example.team4.repository.CycleRepository;
import com.example.team4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.team4.global.exception.challenge.ChallengeErrorCode.CHALLENGE_NOT_FOUND;
import static com.example.team4.global.exception.challenge.ChallengeErrorCode.CYCLE_NOT_FOUND;
import static com.example.team4.global.exception.user.UserErrorCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class CycleService {
    private final ChallengeRepository challengeRepository;
    private final CycleRepository cycleRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;




    // 사이클별 챌린지 목록
    public void getChallengesByCycleId(Long cycleId) {
        // 사이클 존재하는지
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new AppException(CYCLE_NOT_FOUND));
        // 사이클에 속한 챌린지 목록 조회
        List<ChallengeListResponse> list = challengeRepository.findAllByCycleId(cycleId)
                .stream()
                .map(ChallengeListResponse::from)
                .toList();
        CycleStatus cycleStatus;
        if(list.size() == cycle.getUserCount()) {
            cycleStatus = CycleStatus.ONGOING;
            cycle.setStatus(cycleStatus);
        } else {
            cycleStatus = CycleStatus.NOT_STARTED;
            cycle.setStatus(cycleStatus);
        }

        CycleResponse.from(cycleStatus.name(), list);
    }

    // 사이클 종료
    public void updateChallenge(Long userId, Long cycleId, CycleUpdateRequest request, MultipartFile image) {
        // 사이클 존재하는지
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new AppException(CYCLE_NOT_FOUND));
        // user 존재하는지
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND));

        request.validate();
        String imageUrl = null;
        if(image != null) {
            imageUrl = imageService.uploadImage(image);
        }
        cycle.update(request, imageUrl);
    }

    // 완료된 사이클 목록 조회
    public List<CompleteCycleResponse> getCompletedCycles(Long userId) {
        // user 존재하는지
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND));

        // 완료된 사이클 목록 조회
        List<Cycle> completedCycles = cycleRepository.findAllByStatus(CycleStatus.COMPLETED);
        return completedCycles.stream()
                .map(CompleteCycleResponse::from)
                .toList();
    }



}
