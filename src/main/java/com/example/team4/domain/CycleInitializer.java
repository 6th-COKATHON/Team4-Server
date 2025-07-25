package com.example.team4.domain;

import com.example.team4.domain.Cycle;
import com.example.team4.domain.CycleStatus;
import com.example.team4.repository.CycleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CycleInitializer {

    private final CycleRepository cycleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDefaultCycle() {
        // 이미 하나라도 존재하면 생성하지 않음
        if (cycleRepository.count() == 0) {
            Cycle defaultCycle = Cycle.init();
            cycleRepository.save(defaultCycle);
            log.info("✅ 초기 사이클 생성 완료: {}", defaultCycle.getTitle());
        } else {
            log.info("ℹ️ 기존 사이클이 존재하므로 초기화 건너뜸");
        }
    }
}
