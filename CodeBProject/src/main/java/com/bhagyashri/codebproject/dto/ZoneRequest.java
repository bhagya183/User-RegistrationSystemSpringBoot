package com.bhagyashri.codebproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ZoneRequest {
    @NotBlank(message = "Zone name is required")
    private String zoneName;

    @NotNull(message = "Brand ID is required")
    private Long brandId;

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
    
    
} 