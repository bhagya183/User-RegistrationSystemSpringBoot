package com.bhagyashri.codebproject.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EstimateResponse {
    private Long estimateId;
    private Long chainId;
    private String groupName;
    private String brandName;
    private String zoneName;
    private String service;
    private Integer quantity;
    private Double costPerUnit;
    private Double totalCost;
    private LocalDate deliveryDate;
    private String deliveryDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public Long getEstimateId() {
		return estimateId;
	}
	public void setEstimateId(Long estimateId) {
		this.estimateId = estimateId;
	}
	public Long getChainId() {
		return chainId;
	}
	public void setChainId(Long chainId) {
		this.chainId = chainId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getCostPerUnit() {
		return costPerUnit;
	}
	public void setCostPerUnit(Double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryDetails() {
		return deliveryDetails;
	}
	public void setDeliveryDetails(String deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
    
    
} 