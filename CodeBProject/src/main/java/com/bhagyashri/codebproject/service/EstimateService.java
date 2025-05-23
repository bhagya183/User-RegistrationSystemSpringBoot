package com.bhagyashri.codebproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhagyashri.codebproject.dto.EstimateRequest;
import com.bhagyashri.codebproject.dto.EstimateResponse;
import com.bhagyashri.codebproject.entity.Chain;
import com.bhagyashri.codebproject.entity.Estimate;
import com.bhagyashri.codebproject.exception.ResourceNotFoundException;
import com.bhagyashri.codebproject.repository.ChainRepository;
import com.bhagyashri.codebproject.repository.EstimateRepository;

@Service
@Transactional
public class EstimateService {
    private static final Logger logger = LoggerFactory.getLogger(EstimateService.class);

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private ChainRepository chainRepository;

    public List<EstimateResponse> getAllEstimates() {
        logger.debug("Fetching all estimates from repository");
        List<Estimate> estimates = estimateRepository.findAll();
        logger.debug("Found {} estimates in repository", estimates.size());
        return estimates.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public EstimateResponse getEstimateById(Long id) {
        logger.debug("Fetching estimate with id: {} from repository", id);
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Estimate not found with id: {}", id);
                    return new ResourceNotFoundException("Estimate not found with id: " + id);
                });
        logger.debug("Found estimate: {}", estimate);
        return convertToResponse(estimate);
    }

    public List<EstimateResponse> getEstimatesByChainId(Long chainId) {
        logger.debug("Fetching estimates for chain id: {} from repository", chainId);
        List<Estimate> estimates = estimateRepository.findByChain_ChainId(chainId);
        logger.debug("Found {} estimates for chain id: {}", estimates.size(), chainId);
        return estimates.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public EstimateResponse createEstimate(EstimateRequest request) {
        logger.debug("Creating new estimate with request: {}", request);
        
        Chain chain = chainRepository.findById(request.getChainId())
                .orElseThrow(() -> {
                    logger.error("Chain not found with id: {}", request.getChainId());
                    return new ResourceNotFoundException("Chain not found with id: " + request.getChainId());
                });
        logger.debug("Found chain: {}", chain);

        Estimate estimate = new Estimate();
        estimate.setChain(chain);
        estimate.setGroupName(request.getGroupName());
        estimate.setBrandName(request.getBrandName());
        estimate.setZoneName(request.getZoneName());
        estimate.setService(request.getService());
        estimate.setQuantity(request.getQuantity());
        estimate.setCostPerUnit(request.getCostPerUnit());
        estimate.setTotalCost(request.getQuantity() * request.getCostPerUnit());
        estimate.setDeliveryDate(request.getDeliveryDate());
        estimate.setDeliveryDetails(request.getDeliveryDetails());

        logger.debug("Saving new estimate: {}", estimate);
        Estimate savedEstimate = estimateRepository.save(estimate);
        logger.debug("Saved estimate: {}", savedEstimate);
        
        return convertToResponse(savedEstimate);
    }

    public EstimateResponse updateEstimate(Long id, EstimateRequest request) {
        logger.debug("Updating estimate with id: {} and request: {}", id, request);
        
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Estimate not found with id: {}", id);
                    return new ResourceNotFoundException("Estimate not found with id: " + id);
                });
        logger.debug("Found existing estimate: {}", estimate);

        Chain chain = chainRepository.findById(request.getChainId())
                .orElseThrow(() -> {
                    logger.error("Chain not found with id: {}", request.getChainId());
                    return new ResourceNotFoundException("Chain not found with id: " + request.getChainId());
                });
        logger.debug("Found chain: {}", chain);

        estimate.setChain(chain);
        estimate.setGroupName(request.getGroupName());
        estimate.setBrandName(request.getBrandName());
        estimate.setZoneName(request.getZoneName());
        estimate.setService(request.getService());
        estimate.setQuantity(request.getQuantity());
        estimate.setCostPerUnit(request.getCostPerUnit());
        estimate.setTotalCost(request.getQuantity() * request.getCostPerUnit());
        estimate.setDeliveryDate(request.getDeliveryDate());
        estimate.setDeliveryDetails(request.getDeliveryDetails());

        logger.debug("Saving updated estimate: {}", estimate);
        Estimate updatedEstimate = estimateRepository.save(estimate);
        logger.debug("Saved updated estimate: {}", updatedEstimate);
        
        return convertToResponse(updatedEstimate);
    }

    public void deleteEstimate(Long id) {
        logger.debug("Deleting estimate with id: {}", id);
        if (!estimateRepository.existsById(id)) {
            logger.error("Estimate not found with id: {}", id);
            throw new ResourceNotFoundException("Estimate not found with id: " + id);
        }
        estimateRepository.deleteById(id);
        logger.debug("Successfully deleted estimate with id: {}", id);
    }

    private EstimateResponse convertToResponse(Estimate estimate) {
        logger.debug("Converting estimate to response: {}", estimate);
        EstimateResponse response = new EstimateResponse();
        response.setEstimateId(estimate.getEstimateId());
        response.setChainId(estimate.getChain().getChainId());
        response.setGroupName(estimate.getGroupName());
        response.setBrandName(estimate.getBrandName());
        response.setZoneName(estimate.getZoneName());
        response.setService(estimate.getService());
        response.setQuantity(estimate.getQuantity());
        response.setCostPerUnit(estimate.getCostPerUnit());
        response.setTotalCost(estimate.getTotalCost());
        response.setDeliveryDate(estimate.getDeliveryDate());
        response.setDeliveryDetails(estimate.getDeliveryDetails());
        response.setCreatedAt(estimate.getCreatedAt());
        response.setUpdatedAt(estimate.getUpdatedAt());
        logger.debug("Converted to response: {}", response);
        return response;
    }
} 