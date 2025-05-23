package com.bhagyashri.codebproject.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhagyashri.codebproject.dto.InvoiceRequest;
import com.bhagyashri.codebproject.dto.InvoiceResponse;
import com.bhagyashri.codebproject.entity.Chain;
import com.bhagyashri.codebproject.entity.Estimate;
import com.bhagyashri.codebproject.entity.Invoice;
import com.bhagyashri.codebproject.exception.ResourceNotFoundException;
import com.bhagyashri.codebproject.repository.ChainRepository;
import com.bhagyashri.codebproject.repository.EstimateRepository;
import com.bhagyashri.codebproject.repository.InvoiceRepository;

@Service
@Transactional
public class InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private ChainRepository chainRepository;

    public List<InvoiceResponse> getAllInvoices() {
        logger.debug("Fetching all invoices from repository");
        List<Invoice> invoices = invoiceRepository.findAll();
        logger.debug("Found {} invoices in repository", invoices.size());
        return invoices.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public InvoiceResponse getInvoiceById(Long id) {
        logger.debug("Fetching invoice with id: {} from repository", id);
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Invoice not found with id: {}", id);
                    return new ResourceNotFoundException("Invoice not found with id: " + id);
                });
        logger.debug("Found invoice: {}", invoice);
        return convertToResponse(invoice);
    }

    public InvoiceResponse getInvoiceByInvoiceNo(String invoiceNo) {
        logger.debug("Fetching invoice with invoice number: {} from repository", invoiceNo);
        Invoice invoice = invoiceRepository.findByInvoiceNo(invoiceNo)
                .orElseThrow(() -> {
                    logger.error("Invoice not found with invoice number: {}", invoiceNo);
                    return new ResourceNotFoundException("Invoice not found with invoice number: " + invoiceNo);
                });
        logger.debug("Found invoice: {}", invoice);
        return convertToResponse(invoice);
    }

    public List<InvoiceResponse> getInvoicesByEstimateId(Long estimateId) {
        logger.debug("Fetching invoices for estimate id: {} from repository", estimateId);
        List<Invoice> invoices = invoiceRepository.findByEstimate_EstimateId(estimateId);
        logger.debug("Found {} invoices for estimate id: {}", invoices.size(), estimateId);
        return invoices.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<InvoiceResponse> getInvoicesByChainId(Long chainId) {
        logger.debug("Fetching invoices for chain id: {} from repository", chainId);
        List<Invoice> invoices = invoiceRepository.findByChain_ChainId(chainId);
        logger.debug("Found {} invoices for chain id: {}", invoices.size(), chainId);
        return invoices.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<InvoiceResponse> searchInvoicesByCompanyName(String companyName) {
        logger.debug("Searching invoices for company name: {} from repository", companyName);
        List<Invoice> invoices = invoiceRepository.findByChain_CompanyNameContainingIgnoreCase(companyName);
        logger.debug("Found {} invoices for company name: {}", invoices.size(), companyName);
        return invoices.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public InvoiceResponse createInvoice(InvoiceRequest request) {
        logger.debug("Creating new invoice with request: {}", request);
        
        Estimate estimate = estimateRepository.findById(request.getEstimateId())
                .orElseThrow(() -> {
                    logger.error("Estimate not found with id: {}", request.getEstimateId());
                    return new ResourceNotFoundException("Estimate not found with id: " + request.getEstimateId());
                });
        logger.debug("Found estimate: {}", estimate);

        Chain chain = chainRepository.findById(request.getChainId())
                .orElseThrow(() -> {
                    logger.error("Chain not found with id: {}", request.getChainId());
                    return new ResourceNotFoundException("Chain not found with id: " + request.getChainId());
                });
        logger.debug("Found chain: {}", chain);

        // Check if invoice already exists for this estimate and chain
        if (invoiceRepository.findByEstimate_EstimateIdAndChain_ChainId(request.getEstimateId(), request.getChainId()).isPresent()) {
            logger.error("Invoice already exists for estimate id: {} and chain id: {}", request.getEstimateId(), request.getChainId());
            throw new IllegalStateException("Invoice already exists for this estimate and chain");
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceNo(generateInvoiceNumber());
        invoice.setEstimate(estimate);
        invoice.setChain(chain);
        invoice.setServiceDetails(request.getServiceDetails());
        invoice.setQuantity(request.getQuantity());
        invoice.setCostPerQuantity(request.getCostPerQuantity());
        invoice.setAmountPayable(request.getAmountPayable());
        invoice.setBalance(request.getBalance());
        invoice.setDateOfPayment(request.getDateOfPayment());
        invoice.setDateOfService(request.getDateOfService());
        invoice.setDeliveryDetails(request.getDeliveryDetails());
        invoice.setEmailId(request.getEmailId());

        logger.debug("Saving new invoice: {}", invoice);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        logger.debug("Saved invoice: {}", savedInvoice);
        
        return convertToResponse(savedInvoice);
    }

    public InvoiceResponse updateInvoice(Long id, InvoiceRequest request) {
        logger.debug("Updating invoice with id: {} and request: {}", id, request);
        
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Invoice not found with id: {}", id);
                    return new ResourceNotFoundException("Invoice not found with id: " + id);
                });
        logger.debug("Found existing invoice: {}", invoice);

        // Only allow updating email ID
        invoice.setEmailId(request.getEmailId());

        logger.debug("Saving updated invoice: {}", invoice);
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        logger.debug("Saved updated invoice: {}", updatedInvoice);
        
        return convertToResponse(updatedInvoice);
    }

    public void deleteInvoice(Long id) {
        logger.debug("Deleting invoice with id: {}", id);
        if (!invoiceRepository.existsById(id)) {
            logger.error("Invoice not found with id: {}", id);
            throw new ResourceNotFoundException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
        logger.debug("Successfully deleted invoice with id: {}", id);
    }

    private String generateInvoiceNumber() {
        // Generate a 4-digit invoice number based on current timestamp
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
    }

    private InvoiceResponse convertToResponse(Invoice invoice) {
        logger.debug("Converting invoice to response: {}", invoice);
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setInvoiceNo(invoice.getInvoiceNo());
        response.setEstimateId(invoice.getEstimate().getEstimateId());
        response.setChainId(invoice.getChain().getChainId());
        response.setCompanyName(invoice.getChain().getCompanyName());
        response.setGstnNo(invoice.getChain().getGstnNo());
        response.setServiceDetails(invoice.getServiceDetails());
        response.setQuantity(invoice.getQuantity());
        response.setCostPerQuantity(invoice.getCostPerQuantity());
        response.setAmountPayable(invoice.getAmountPayable());
        response.setBalance(invoice.getBalance());
        response.setDateOfPayment(invoice.getDateOfPayment());
        response.setDateOfService(invoice.getDateOfService());
        response.setDeliveryDetails(invoice.getDeliveryDetails());
        response.setEmailId(invoice.getEmailId());
        response.setCreatedAt(invoice.getCreatedAt());
        response.setUpdatedAt(invoice.getUpdatedAt());
        logger.debug("Converted to response: {}", response);
        return response;
    }
} 