package com.bhagyashri.codebproject.dto;

import lombok.Data;

@Data
public class GroupRequest {
    private String groupName;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
    
} 