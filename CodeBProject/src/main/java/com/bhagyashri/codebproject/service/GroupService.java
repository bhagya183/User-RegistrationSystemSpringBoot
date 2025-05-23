package com.bhagyashri.codebproject.service;

import com.bhagyashri.codebproject.dto.GroupRequest;
import com.bhagyashri.codebproject.dto.GroupResponse;
import com.bhagyashri.codebproject.entity.Group;
import com.bhagyashri.codebproject.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<GroupResponse> getAllGroups() {
        return groupRepository.findByIsActive(true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public GroupResponse createGroup(GroupRequest request) {
        if (groupRepository.existsByGroupName(request.getGroupName())) {
            throw new RuntimeException("Group name already exists");
        }

        Group group = new Group();
        group.setGroupName(request.getGroupName());
        group.setActive(true);
        
        return mapToResponse(groupRepository.save(group));
    }

    @Transactional
    public GroupResponse updateGroup(Long groupId, GroupRequest request) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getGroupName().equals(request.getGroupName()) && 
            groupRepository.existsByGroupName(request.getGroupName())) {
            throw new RuntimeException("Group name already exists");
        }

        group.setGroupName(request.getGroupName());
        return mapToResponse(groupRepository.save(group));
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        
        group.setActive(false);
        groupRepository.save(group);
    }

    private GroupResponse mapToResponse(Group group) {
        GroupResponse response = new GroupResponse();
        response.setGroupId(group.getGroupId());
        response.setGroupName(group.getGroupName());
        response.setActive(group.isActive());
        response.setCreatedAt(group.getCreatedAt());
        response.setUpdatedAt(group.getUpdatedAt());
        return response;
    }
} 