package com.example.team4.repository;

import com.example.team4.domain.Cycle;
import com.example.team4.domain.CycleStatus;
import com.example.team4.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CycleRepository extends JpaRepository<Cycle, Long> {
    List<Cycle> findAllByStatus(CycleStatus cycleStatus);
}
