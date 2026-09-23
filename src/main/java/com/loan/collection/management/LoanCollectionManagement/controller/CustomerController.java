package com.loan.collection.management.LoanCollectionManagement.controller;

import com.loan.collection.management.LoanCollectionManagement.dto.CustomerApprovalRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CustomerRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CustomerResponse;
import com.loan.collection.management.LoanCollectionManagement.service.CustomerService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request,
            Authentication authentication
    ) {

        UUID userId = getAuthenticatedUserId(authentication);

        CustomerResponse response =
                customerService.createCustomer(
                        request,
                        userId
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                customerService.getCustomerById(id)
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<List<CustomerResponse>>
    getPendingCustomers() {

        return ResponseEntity.ok(
                customerService.getPendingCustomers()
        );
    }

    @GetMapping("/created-by/{userId}")
    public ResponseEntity<List<CustomerResponse>>
    getCustomersByCreator(
            @PathVariable UUID userId
    ) {

        return ResponseEntity.ok(
                customerService.getCustomersByCreator(userId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerRequest request
    ) {

        return ResponseEntity.ok(
                customerService.updateCustomer(
                        id,
                        request
                )
        );
    }

    @PatchMapping("/{id}/approval")
    public ResponseEntity<CustomerResponse>
    approveOrRejectCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerApprovalRequest request,
            Authentication authentication
    ) {

        UUID approverId =
                getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                customerService.approveOrRejectCustomer(
                        id,
                        request,
                        approverId
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable UUID id
    ) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

    private UUID getAuthenticatedUserId(
            Authentication authentication
    ) {

        return UUID.fromString(
                authentication.getPrincipal().toString()
        );
    }
}