package com.bhagyashri.codebproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhagyashri.codebproject.dto.InvoiceRequest;
import com.bhagyashri.codebproject.dto.InvoiceResponse;
import com.bhagyashri.codebproject.service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        logger.debug("Fetching all invoices");
        try {
            List<InvoiceResponse> invoices = invoiceService.getAllInvoices();
            logger.debug("Found {} invoices", invoices.size());
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            logger.error("Error fetching invoices", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        logger.debug("Fetching invoice with id: {}", id);
        try {
            InvoiceResponse invoice = invoiceService.getInvoiceById(id);
            logger.debug("Found invoice: {}", invoice);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            logger.error("Error fetching invoice with id: {}", id, e);
            throw e;
        }
    }

    @GetMapping("/number/{invoiceNo}")
    public ResponseEntity<InvoiceResponse> getInvoiceByInvoiceNo(@PathVariable String invoiceNo) {
        logger.debug("Fetching invoice with invoice number: {}", invoiceNo);
        try {
            InvoiceResponse invoice = invoiceService.getInvoiceByInvoiceNo(invoiceNo);
            logger.debug("Found invoice: {}", invoice);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            logger.error("Error fetching invoice with invoice number: {}", invoiceNo, e);
            throw e;
        }
    }

    @GetMapping("/estimate/{estimateId}")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByEstimateId(@PathVariable Long estimateId) {
        logger.debug("Fetching invoices for estimate id: {}", estimateId);
        try {
            List<InvoiceResponse> invoices = invoiceService.getInvoicesByEstimateId(estimateId);
            logger.debug("Found {} invoices for estimate id: {}", invoices.size(), estimateId);
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            logger.error("Error fetching invoices for estimate id: {}", estimateId, e);
            throw e;
        }
    }

    @GetMapping("/chain/{chainId}")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByChainId(@PathVariable Long chainId) {
        logger.debug("Fetching invoices for chain id: {}", chainId);
        try {
            List<InvoiceResponse> invoices = invoiceService.getInvoicesByChainId(chainId);
            logger.debug("Found {} invoices for chain id: {}", invoices.size(), chainId);
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            logger.error("Error fetching invoices for chain id: {}", chainId, e);
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<InvoiceResponse>> searchInvoicesByCompanyName(@RequestParam String companyName) {
        logger.debug("Searching invoices for company name: {}", companyName);
        try {
            List<InvoiceResponse> invoices = invoiceService.searchInvoicesByCompanyName(companyName);
            logger.debug("Found {} invoices for company name: {}", invoices.size(), companyName);
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            logger.error("Error searching invoices for company name: {}", companyName, e);
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest request) {
        logger.debug("Creating new invoice: {}", request);
        try {
            InvoiceResponse invoice = invoiceService.createInvoice(request);
            logger.debug("Created invoice: {}", invoice);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            logger.error("Error creating invoice", e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponse> updateInvoice(@PathVariable Long id, @RequestBody InvoiceRequest request) {
        logger.debug("Updating invoice with id: {}, data: {}", id, request);
        try {
            InvoiceResponse invoice = invoiceService.updateInvoice(id, request);
            logger.debug("Updated invoice: {}", invoice);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            logger.error("Error updating invoice with id: {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        logger.debug("Deleting invoice with id: {}", id);
        try {
            invoiceService.deleteInvoice(id);
            logger.debug("Successfully deleted invoice with id: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting invoice with id: {}", id, e);
            throw e;
        }
    }
} 