package com.bhagyashri.codebproject.dto;

import lombok.Data;

@Data
public class ChainRequest {
    private String companyName;
    private String gstnNo;
    private Long groupId;
    
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
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
    
    
} 