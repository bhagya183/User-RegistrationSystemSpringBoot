package com.bhagyashri.codebproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhagyashri.codebproject.dto.EstimateRequest;
import com.bhagyashri.codebproject.dto.EstimateResponse;
import com.bhagyashri.codebproject.service.EstimateService;

@RestController
@RequestMapping("/api/estimates")
public class EstimateController {
    private static final Logger logger = LoggerFactory.getLogger(EstimateController.class);

    @Autowired
    private EstimateService estimateService;

    @GetMapping
    public ResponseEntity<List<EstimateResponse>> getAllEstimates() {
        logger.debug("Fetching all estimates");
        try {
            List<EstimateResponse> estimates = estimateService.getAllEstimates();
            logger.debug("Found {} estimates", estimates.size());
            return ResponseEntity.ok(estimates);
        } catch (Exception e) {
            logger.error("Error fetching estimates", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstimateResponse> getEstimateById(@PathVariable Long id) {
        logger.debug("Fetching estimate with id: {}", id);
        try {
            EstimateResponse estimate = estimateService.getEstimateById(id);
            logger.debug("Found estimate: {}", estimate);
            return ResponseEntity.ok(estimate);
        } catch (Exception e) {
            logger.error("Error fetching estimate with id: {}", id, e);
            throw e;
        }
    }

    @GetMapping("/chain/{chainId}")
    public ResponseEntity<List<EstimateResponse>> getEstimatesByChainId(@PathVariable Long chainId) {
        logger.debug("Fetching estimates for chain id: {}", chainId);
        try {
            List<EstimateResponse> estimates = estimateService.getEstimatesByChainId(chainId);
            logger.debug("Found {} estimates for chain id: {}", estimates.size(), chainId);
            return ResponseEntity.ok(estimates);
        } catch (Exception e) {
            logger.error("Error fetching estimates for chain id: {}", chainId, e);
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<EstimateResponse> createEstimate(@RequestBody EstimateRequest request) {
        logger.debug("Creating new estimate: {}", request);
        try {
            EstimateResponse estimate = estimateService.createEstimate(request);
            logger.debug("Created estimate: {}", estimate);
            return ResponseEntity.ok(estimate);
        } catch (Exception e) {
            logger.error("Error creating estimate", e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstimateResponse> updateEstimate(@PathVariable Long id, @RequestBody EstimateRequest request) {
        logger.debug("Updating estimate with id: {}, data: {}", id, request);
        try {
            EstimateResponse estimate = estimateService.updateEstimate(id, request);
            logger.debug("Updated estimate: {}", estimate);
            return ResponseEntity.ok(estimate);
        } catch (Exception e) {
            logger.error("Error updating estimate with id: {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstimate(@PathVariable Long id) {
        logger.debug("Deleting estimate with id: {}", id);
        try {
            estimateService.deleteEstimate(id);
            logger.debug("Successfully deleted estimate with id: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting estimate with id: {}", id, e);
            throw e;
        }
    }
} 