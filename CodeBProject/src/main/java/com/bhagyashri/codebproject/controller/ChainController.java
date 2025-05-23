package com.bhagyashri.codebproject.controller;

import com.bhagyashri.codebproject.dto.ChainRequest;

import com.bhagyashri.codebproject.dto.ChainResponse;
import com.bhagyashri.codebproject.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chains")
@CrossOrigin(origins = "*")
public class ChainController {

    @Autowired
    private ChainService chainService;

    @GetMapping
    public ResponseEntity<List<ChainResponse>> getAllChains(
            @RequestParam(required = false) Long groupId) {
        if (groupId != null) {
            return ResponseEntity.ok(chainService.getChainsByGroup(groupId));
        }
        return ResponseEntity.ok(chainService.getAllChains());
    }

    @PostMapping
    public ResponseEntity<?> createChain(@RequestBody ChainRequest request) {
        try {
            if (request.getCompanyName() == null || request.getCompanyName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Company name cannot be empty");
            }
            if (request.getGstnNo() == null || request.getGstnNo().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("GSTN number cannot be empty");
            }
            if (request.getGroupId() == null) {
                return ResponseEntity.badRequest().body("Group ID cannot be empty");
            }
            return ResponseEntity.ok(chainService.createChain(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while creating the chain");
        }
    }

    @PutMapping("/{chainId}")
    public ResponseEntity<?> updateChain(
            @PathVariable Long chainId,
            @RequestBody ChainRequest request) {
        try {
            if (request.getCompanyName() == null || request.getCompanyName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Company name cannot be empty");
            }
            if (request.getGstnNo() == null || request.getGstnNo().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("GSTN number cannot be empty");
            }
            if (request.getGroupId() == null) {
                return ResponseEntity.badRequest().body("Group ID cannot be empty");
            }
            return ResponseEntity.ok(chainService.updateChain(chainId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while updating the chain");
        }
    }

    @DeleteMapping("/{chainId}")
    public ResponseEntity<?> deleteChain(@PathVariable Long chainId) {
        try {
            chainService.deleteChain(chainId);
            return ResponseEntity.ok().body("Chain deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while deleting the chain");
        }
    }
} 