package com.bhagyashri.codebproject.repository;

import com.bhagyashri.codebproject.entity.Chain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChainRepository extends JpaRepository<Chain, Long> {
    Optional<Chain> findByGstnNo(String gstnNo);
    List<Chain> findByIsActive(boolean isActive);
    List<Chain> findByGroup_GroupIdAndIsActive(Long groupId, boolean isActive);
    boolean existsByGstnNo(String gstnNo);
} 