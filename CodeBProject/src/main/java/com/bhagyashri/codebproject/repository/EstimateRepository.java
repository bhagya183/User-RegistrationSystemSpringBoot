package com.bhagyashri.codebproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bhagyashri.codebproject.entity.Estimate;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    List<Estimate> findByChain_ChainId(Long chainId);
    List<Estimate> findByGroupName(String groupName);
    List<Estimate> findByBrandName(String brandName);
    List<Estimate> findByZoneName(String zoneName);
} 