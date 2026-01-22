package com.turkcell.demo.repository;

import com.turkcell.demo.entity.CaseStatus;
import com.turkcell.demo.entity.FraudCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FraudCaseRepository extends JpaRepository<FraudCase, Long> {
    List<FraudCase> findByStatus(CaseStatus status);
}
