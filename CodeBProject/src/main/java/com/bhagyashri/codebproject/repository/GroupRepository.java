package com.bhagyashri.codebproject.repository;

import com.bhagyashri.codebproject.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupName(String groupName);
    List<Group> findByIsActive(boolean isActive);
    boolean existsByGroupName(String groupName);
} 