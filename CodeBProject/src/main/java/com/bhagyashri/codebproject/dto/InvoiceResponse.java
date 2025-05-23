package com.bhagyashri.codebproject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InvoiceResponse {
    private Long id;
    private String invoiceNo;
    private Long estimateId;
    private Long chainId;
    private String companyName;
    private String gstnNo;
    private String serviceDetails;
    private Integer quantity;
    private BigDecimal costPerQuantity;
    private BigDecimal amountPayable;
    private BigDecimal balance;
    private LocalDateTime dateOfPayment;
    private LocalDate dateOfService;
    private String deliveryDetails;
    private String emailId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
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
	public String getServiceDetails() {
		return serviceDetails;
	}
	public void setServiceDetails(String serviceDetails) {
		this.serviceDetails = serviceDetails;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getCostPerQuantity() {
		return costPerQuantity;
	}
	public void setCostPerQuantity(BigDecimal costPerQuantity) {
		this.costPerQuantity = costPerQuantity;
	}
	public BigDecimal getAmountPayable() {
		return amountPayable;
	}
	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public LocalDateTime getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(LocalDateTime dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public LocalDate getDateOfService() {
		return dateOfService;
	}
	public void setDateOfService(LocalDate dateOfService) {
		this.dateOfService = dateOfService;
	}
	public String getDeliveryDetails() {
		return deliveryDetails;
	}
	public void setDeliveryDetails(String deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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