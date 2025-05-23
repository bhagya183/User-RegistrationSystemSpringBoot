package com.bhagyashri.codebproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bhagyashri.codebproject.entity.Zone;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findByActive(boolean active);
    List<Zone> findByBrand_BrandIdAndActive(Long brandId, boolean active);
    List<Zone> findByBrand_Chain_ChainIdAndActive(Long chainId, boolean active);
    List<Zone> findByBrand_Chain_Group_GroupIdAndActive(Long groupId, boolean active);
    Optional<Zone> findByZoneNameAndBrand_BrandId(String zoneName, Long brandId);
} 