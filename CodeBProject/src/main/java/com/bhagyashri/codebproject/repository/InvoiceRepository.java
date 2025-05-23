package com.bhagyashri.codebproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bhagyashri.codebproject.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNo(String invoiceNo);
    List<Invoice> findByEstimate_EstimateId(Long estimateId);
    List<Invoice> findByChain_ChainId(Long chainId);
    List<Invoice> findByChain_CompanyNameContainingIgnoreCase(String companyName);
    Optional<Invoice> findByEstimate_EstimateIdAndChain_ChainId(Long estimateId, Long chainId);
} 