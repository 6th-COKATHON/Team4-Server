package com.example.team4.domain;

import com.example.team4.dto.request.ChallengeCreateRequest;
import com.example.team4.dto.request.ChallengeUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "challenges")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String rule; // 인증 규칙

    private LocalDateTime createdAt;
    private String challengeImage;
    private String proofImage;
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int challenge_order;

    public static Challenge of(Cycle cycle, User user, ChallengeCreateRequest request, String imageUrl, int order) {
        return Challenge.builder()
                .cycle(cycle)
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .rule(request.getRule())
                .createdAt(LocalDateTime.now())
                .challengeImage(imageUrl == null ? null : imageUrl)
                .proofImage(null) // 초기에는 이미지 없음
                .status(ChallengeStatus.NOT_STARTED) // 초기 상태는 NOT_STARTED
                .startDate(null)
                .endDate(null)
                .challenge_order(order)
                .build();
    }

    // update
    public void update(ChallengeUpdateRequest request, String imageUrl) {
        this.challengeImage = imageUrl;
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.status = ChallengeStatus.COMPLETED;
    }

}
