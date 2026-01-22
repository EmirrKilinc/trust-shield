package com.turkcell.demo.repository;

import com.turkcell.demo.entity.CaseAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaseActionRepository extends JpaRepository<CaseAction, Long> {

    @Query("""
    SELECT ca
    FROM CaseAction ca
    JOIN ca.fraudCase fc
    WHERE fc.id = :caseId
    ORDER BY ca.timestamp ASC
""")
    List<CaseAction> findActionsByCaseId(Long caseId);


}
