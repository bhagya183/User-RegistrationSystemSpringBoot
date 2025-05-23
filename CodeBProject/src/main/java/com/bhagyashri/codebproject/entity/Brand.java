package com.bhagyashri.codebproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "brands")
@Data
public class Brand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    
    @Column(nullable = false, length = 50)
    private String brandName;
    
    @ManyToOne
    @JoinColumn(name = "chain_id", nullable = false)
    private Chain chain;
    
    @Column(name = "is_active", nullable = false)
    private boolean active = true;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
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

	public Chain getChain() {
		return chain;
	}

	public void setChain(Chain chain) {
		this.chain = chain;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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