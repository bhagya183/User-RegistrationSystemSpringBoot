package com.bhagyashri.codebproject.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BrandResponse {
    private Long brandId;
    private String brandName;
    private ChainResponse chain;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public ChainResponse getChain() {
		return chain;
	}
	public void setChain(ChainResponse chain) {
		this.chain = chain;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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