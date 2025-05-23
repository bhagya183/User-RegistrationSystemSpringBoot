package com.bhagyashri.codebproject.service;

import com.bhagyashri.codebproject.dto.ChainRequest;
import com.bhagyashri.codebproject.dto.ChainResponse;
import com.bhagyashri.codebproject.dto.GroupResponse;
import com.bhagyashri.codebproject.entity.Chain;
import com.bhagyashri.codebproject.entity.Group;
import com.bhagyashri.codebproject.repository.ChainRepository;
import com.bhagyashri.codebproject.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChainService {

    @Autowired
    private ChainRepository chainRepository;

    @Autowired
    private GroupRepository groupRepository;

    public List<ChainResponse> getAllChains() {
        return chainRepository.findByIsActive(true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ChainResponse> getChainsByGroup(Long groupId) {
        return chainRepository.findByGroupIdAndIsActive(groupId, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChainResponse createChain(ChainRequest request) {
        if (chainRepository.existsByGstnNo(request.getGstnNo())) {
            throw new RuntimeException("GSTN number already exists");
        }

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Chain chain = new Chain();
        chain.setCompanyName(request.getCompanyName());
        chain.setGstnNo(request.getGstnNo());
        chain.setGroup(group);
        chain.setActive(true);
        
        return mapToResponse(chainRepository.save(chain));
    }

    @Transactional
    public ChainResponse updateChain(Long chainId, ChainRequest request) {
        Chain chain = chainRepository.findById(chainId)
                .orElseThrow(() -> new RuntimeException("Chain not found"));

        if (!chain.getGstnNo().equals(request.getGstnNo()) && 
            chainRepository.existsByGstnNo(request.getGstnNo())) {
            throw new RuntimeException("GSTN number already exists");
        }

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        chain.setCompanyName(request.getCompanyName());
        chain.setGstnNo(request.getGstnNo());
        chain.setGroup(group);
        
        return mapToResponse(chainRepository.save(chain));
    }

    @Transactional
    public void deleteChain(Long chainId) {
        Chain chain = chainRepository.findById(chainId)
                .orElseThrow(() -> new RuntimeException("Chain not found"));
        
        chain.setActive(false);
        chainRepository.save(chain);
    }

    private ChainResponse mapToResponse(Chain chain) {
        ChainResponse response = new ChainResponse();
        response.setChainId(chain.getChainId());
        response.setCompanyName(chain.getCompanyName());
        response.setGstnNo(chain.getGstnNo());
        response.setActive(chain.isActive());
        response.setCreatedAt(chain.getCreatedAt());
        response.setUpdatedAt(chain.getUpdatedAt());
        
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupId(chain.getGroup().getGroupId());
        groupResponse.setGroupName(chain.getGroup().getGroupName());
        response.setGroup(groupResponse);
        
        return response;
    }
} 