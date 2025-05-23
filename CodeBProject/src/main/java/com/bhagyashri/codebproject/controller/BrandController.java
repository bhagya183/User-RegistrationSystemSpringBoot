package com.bhagyashri.codebproject.controller;

import com.bhagyashri.codebproject.dto.BrandRequest;
import com.bhagyashri.codebproject.dto.BrandResponse;
import com.bhagyashri.codebproject.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin(origins = "*")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands(
            @RequestParam(required = false) Long chainId,
            @RequestParam(required = false) Long groupId) {
        if (chainId != null) {
            return ResponseEntity.ok(brandService.getBrandsByChain(chainId));
        }
        if (groupId != null) {
            return ResponseEntity.ok(brandService.getBrandsByGroup(groupId));
        }
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @PostMapping
    public ResponseEntity<?> createBrand(@RequestBody BrandRequest request) {
        try {
            if (request.getBrandName() == null || request.getBrandName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Brand name cannot be empty");
            }
            if (request.getChainId() == null) {
                return ResponseEntity.badRequest().body("Chain ID cannot be empty");
            }
            return ResponseEntity.ok(brandService.createBrand(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while creating the brand");
        }
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<?> updateBrand(
            @PathVariable Long brandId,
            @RequestBody BrandRequest request) {
        try {
            if (request.getBrandName() == null || request.getBrandName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Brand name cannot be empty");
            }
            if (request.getChainId() == null) {
                return ResponseEntity.badRequest().body("Chain ID cannot be empty");
            }
            return ResponseEntity.ok(brandService.updateBrand(brandId, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while updating the brand");
        }
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long brandId) {
        try {
            brandService.deleteBrand(brandId);
            return ResponseEntity.ok().body("Brand deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while deleting the brand");
        }
    }
} 