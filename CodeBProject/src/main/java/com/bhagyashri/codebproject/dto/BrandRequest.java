package com.bhagyashri.codebproject.dto;

import lombok.Data;

@Data
public class BrandRequest {
    private String brandName;
    private Long chainId;
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Long getChainId() {
		return chainId;
	}
	public void setChainId(Long chainId) {
		this.chainId = chainId;
	}
    
    
} 