package com.bhagyashri.codebproject.service;

import com.bhagyashri.codebproject.dto.BrandRequest;
import com.bhagyashri.codebproject.dto.BrandResponse;
import com.bhagyashri.codebproject.dto.ChainResponse;
import com.bhagyashri.codebproject.entity.Brand;
import com.bhagyashri.codebproject.entity.Chain;
import com.bhagyashri.codebproject.repository.BrandRepository;
import com.bhagyashri.codebproject.repository.ChainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ChainRepository chainRepository;

    public List<BrandResponse> getAllBrands() {
        return brandRepository.findByActive(true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BrandResponse> getBrandsByChain(Long chainId) {
        return brandRepository.findByChain_ChainIdAndActive(chainId, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BrandResponse> getBrandsByGroup(Long groupId) {
        return brandRepository.findByChain_Group_GroupIdAndActive(groupId, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BrandResponse createBrand(BrandRequest request) {
        // Validate request
        if (request.getBrandName() == null || request.getBrandName().trim().isEmpty()) {
            throw new RuntimeException("Brand name cannot be empty");
        }
        if (request.getChainId() == null) {
            throw new RuntimeException("Chain ID cannot be empty");
        }

        // Check if brand name already exists for this chain
        if (brandRepository.findByBrandNameAndChain_ChainId(request.getBrandName(), request.getChainId()).isPresent()) {
            throw new RuntimeException("Brand name already exists for this chain");
        }

        // Find the chain
        Chain chain = chainRepository.findById(request.getChainId())
                .orElseThrow(() -> new RuntimeException("Chain not found with ID: " + request.getChainId()));

        // Create new brand
        Brand brand = new Brand();
        brand.setBrandName(request.getBrandName().trim());
        brand.setChain(chain);
        brand.setActive(true);
        
        try {
            return mapToResponse(brandRepository.save(brand));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create brand: " + e.getMessage());
        }
    }

    @Transactional
    public BrandResponse updateBrand(Long brandId, BrandRequest request) {
        // Validate request
        if (request.getBrandName() == null || request.getBrandName().trim().isEmpty()) {
            throw new RuntimeException("Brand name cannot be empty");
        }
        if (request.getChainId() == null) {
            throw new RuntimeException("Chain ID cannot be empty");
        }

        // Find existing brand
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found with ID: " + brandId));

        // Check if new name conflicts with existing brand
        if (!brand.getBrandName().equals(request.getBrandName()) && 
            brandRepository.findByBrandNameAndChain_ChainId(request.getBrandName(), request.getChainId()).isPresent()) {
            throw new RuntimeException("Brand name already exists for this chain");
        }

        // Find the chain
        Chain chain = chainRepository.findById(request.getChainId())
                .orElseThrow(() -> new RuntimeException("Chain not found with ID: " + request.getChainId()));

        // Update brand
        brand.setBrandName(request.getBrandName().trim());
        brand.setChain(chain);
        
        try {
            return mapToResponse(brandRepository.save(brand));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update brand: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found with ID: " + brandId));
        
        brand.setActive(false);
        try {
            brandRepository.save(brand);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete brand: " + e.getMessage());
        }
    }

    private BrandResponse mapToResponse(Brand brand) {
        BrandResponse response = new BrandResponse();
        response.setBrandId(brand.getBrandId());
        response.setBrandName(brand.getBrandName());
        response.setActive(brand.isActive());
        response.setCreatedAt(brand.getCreatedAt());
        response.setUpdatedAt(brand.getUpdatedAt());
        
        ChainResponse chainResponse = new ChainResponse();
        chainResponse.setChainId(brand.getChain().getChainId());
        chainResponse.setCompanyName(brand.getChain().getCompanyName());
        chainResponse.setGstnNo(brand.getChain().getGstnNo());
        response.setChain(chainResponse);
        
        return response;
    }
} 