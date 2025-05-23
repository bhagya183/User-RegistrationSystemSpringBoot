package com.bhagyashri.codebproject.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChainResponse {
    private Long chainId;
    private String companyName;
    private String gstnNo;
    private GroupResponse group;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public Long getChainId() {
		return chainId;
	}
	public void setChainId(Long chainId) {
		this.chainId = chainId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getGstnNo() {
		return gstnNo;
	}
	public void setGstnNo(String gstnNo) {
		this.gstnNo = gstnNo;
	}
	public GroupResponse getGroup() {
		return group;
	}
	public void setGroup(GroupResponse group) {
		this.group = group;
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