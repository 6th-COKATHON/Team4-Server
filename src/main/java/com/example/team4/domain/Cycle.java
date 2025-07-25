package com.example.team4.domain;

import com.example.team4.dto.request.CycleUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cycles")
public class Cycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cycle_id")
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private CycleStatus status;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String image;

    @Builder.Default
    private int userCount = 5;

    public void update(CycleUpdateRequest request, String image) {
        this.title = request.getTitle();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.image = image;
        this.status = CycleStatus.COMPLETED;
    }

    public static Cycle init() {
        return Cycle.builder()
                .status(CycleStatus.NOT_STARTED)
                .build();
    }

}

