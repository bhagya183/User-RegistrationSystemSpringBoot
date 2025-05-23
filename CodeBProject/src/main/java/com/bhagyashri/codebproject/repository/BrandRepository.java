package com.bhagyashri.codebproject.repository;

import com.bhagyashri.codebproject.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByActive(boolean active);
    List<Brand> findByChain_ChainIdAndActive(Long chainId, boolean active);
    List<Brand> findByChain_Group_GroupIdAndActive(Long groupId, boolean active);
    Optional<Brand> findByBrandNameAndChain_ChainId(String brandName, Long chainId);
} 