package com.bhagyashri.codebproject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InvoiceRequest {
    @NotNull(message = "Estimate ID is required")
    private Long estimateId;

    @NotNull(message = "Chain ID is required")
    private Long chainId;

    @NotBlank(message = "Service details are required")
    private String serviceDetails;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Cost per quantity is required")
    @Positive(message = "Cost per quantity must be positive")
    private BigDecimal costPerQuantity;

    @NotNull(message = "Amount payable is required")
    @Positive(message = "Amount payable must be positive")
    private BigDecimal amountPayable;

    private BigDecimal balance;

    @NotNull(message = "Date of payment is required")
    private LocalDateTime dateOfPayment;

    @NotNull(message = "Date of service is required")
    private LocalDate dateOfService;

    @NotBlank(message = "Delivery details are required")
    private String deliveryDetails;

    @NotBlank(message = "Email ID is required")
    @Email(message = "Invalid email format")
    private String emailId;

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
    
    
} 