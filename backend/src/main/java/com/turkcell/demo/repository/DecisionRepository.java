package com.turkcell.demo.repository;

import com.turkcell.demo.entity.Decision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DecisionRepository extends JpaRepository<Decision, Long > {
}
