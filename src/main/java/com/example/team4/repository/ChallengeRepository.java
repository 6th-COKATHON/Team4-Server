package com.example.team4.repository;

import com.example.team4.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // cycle에 따른 챌린지가 몇 개인지
    long countByCycleId(Long cycleId);

    // cycle에 따른 챌린지 목록 조회
    List<Challenge> findAllByCycleId(Long cycleId);

    boolean existsByCycleIdAndUserId(Long cycleId, Long userId);


    boolean existsByCycleIdAndChallengeOrder(Long cycleId, int order);

    Challenge findByCycleIdAndChallengeOrder(Long cycleId, int order);
}
