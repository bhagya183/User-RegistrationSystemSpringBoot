package com.bhagyashri.codebproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bhagyashri.codebproject.dto.ZoneRequest;
import com.bhagyashri.codebproject.dto.ZoneResponse;
import com.bhagyashri.codebproject.service.ZoneService;

@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = "*")
public class ZoneController {
    @Autowired
    private ZoneService zoneService;

    @GetMapping
    public ResponseEntity<List<ZoneResponse>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<ZoneResponse>> getZonesByBrand(@PathVariable Long brandId) {
        return ResponseEntity.ok(zoneService.getZonesByBrand(brandId));
    }

    @GetMapping("/chain/{chainId}")
    public ResponseEntity<List<ZoneResponse>> getZonesByChain(@PathVariable Long chainId) {
        return ResponseEntity.ok(zoneService.getZonesByChain(chainId));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ZoneResponse>> getZonesByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(zoneService.getZonesByGroup(groupId));
    }

    @PostMapping
    public ResponseEntity<ZoneResponse> createZone(@RequestBody ZoneRequest request) {
        return ResponseEntity.ok(zoneService.createZone(request));
    }

    @PutMapping("/{zoneId}")
    public ResponseEntity<ZoneResponse> updateZone(@PathVariable Long zoneId, @RequestBody ZoneRequest request) {
        return ResponseEntity.ok(zoneService.updateZone(zoneId, request));
    }

    @DeleteMapping("/{zoneId}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long zoneId) {
        zoneService.deleteZone(zoneId);
        return ResponseEntity.ok().build();
    }
} 