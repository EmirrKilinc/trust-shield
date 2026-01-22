package com.turkcell.demo.repository;

import com.turkcell.demo.entity.RiskRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskRuleRepository extends JpaRepository<RiskRule, Long> {
    List<RiskRule> findAllByIsActiveTrue();
}