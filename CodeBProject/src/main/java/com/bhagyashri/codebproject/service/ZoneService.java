package com.bhagyashri.codebproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhagyashri.codebproject.dto.ZoneRequest;
import com.bhagyashri.codebproject.dto.ZoneResponse;
import com.bhagyashri.codebproject.entity.Brand;
import com.bhagyashri.codebproject.entity.Zone;
import com.bhagyashri.codebproject.repository.BrandRepository;
import com.bhagyashri.codebproject.repository.ZoneRepository;

@Service
@Transactional
public class ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private BrandRepository brandRepository;

    public List<ZoneResponse> getAllZones() {
        return zoneRepository.findByActive(true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ZoneResponse> getZonesByBrand(Long brandId) {
        return zoneRepository.findByBrand_BrandIdAndActive(brandId, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ZoneResponse> getZonesByChain(Long chainId) {
        return zoneRepository.findByBrand_Chain_ChainIdAndActive(chainId, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ZoneResponse> getZonesByGroup(Long groupId) {
        return zoneRepository.findByBrand_Chain_Group_GroupIdAndActive(groupId, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ZoneResponse createZone(ZoneRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        if (zoneRepository.findByZoneNameAndBrand_BrandId(request.getZoneName(), request.getBrandId()).isPresent()) {
            throw new RuntimeException("Zone name already exists for this brand");
        }

        Zone zone = new Zone();
        zone.setZoneName(request.getZoneName().trim());
        zone.setBrand(brand);
        zone.setActive(true);

        return mapToResponse(zoneRepository.save(zone));
    }

    public ZoneResponse updateZone(Long zoneId, ZoneRequest request) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        if (!zone.getZoneName().equals(request.getZoneName().trim()) &&
            zoneRepository.findByZoneNameAndBrand_BrandId(request.getZoneName(), request.getBrandId()).isPresent()) {
            throw new RuntimeException("Zone name already exists for this brand");
        }

        zone.setZoneName(request.getZoneName().trim());
        zone.setBrand(brand);

        return mapToResponse(zoneRepository.save(zone));
    }

    public void deleteZone(Long zoneId) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
        zone.setActive(false);
        zoneRepository.save(zone);
    }

    private ZoneResponse mapToResponse(Zone zone) {
        ZoneResponse response = new ZoneResponse();
        response.setZoneId(zone.getZoneId());
        response.setZoneName(zone.getZoneName());
        response.setBrandId(zone.getBrand().getBrandId());
        response.setBrandName(zone.getBrand().getBrandName());
        response.setChainName(zone.getBrand().getChain().getCompanyName());
        response.setGroupName(zone.getBrand().getChain().getGroup().getGroupName());
        response.setActive(zone.isActive());
        response.setCreatedAt(zone.getCreatedAt());
        response.setUpdatedAt(zone.getUpdatedAt());
        return response;
    }
} 