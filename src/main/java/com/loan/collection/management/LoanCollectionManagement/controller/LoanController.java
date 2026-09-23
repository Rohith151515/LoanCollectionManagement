package com.loan.collection.management.LoanCollectionManagement.controller;

import com.loan.collection.management.LoanCollectionManagement.dto.LoanRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.LoanResponse;
import com.loan.collection.management.LoanCollectionManagement.service.LoanService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(
            @Valid @RequestBody LoanRequest request
    ) {

        LoanResponse response =
                loanService.createLoan(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoanById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                loanService.getLoanById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getAllLoans() {

        return ResponseEntity.ok(
                loanService.getAllLoans()
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponse>> getLoansByCustomer(
            @PathVariable UUID customerId
    ) {

        return ResponseEntity.ok(
                loanService.getLoansByCustomer(customerId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanResponse> updateLoan(
            @PathVariable UUID id,
            @Valid @RequestBody LoanRequest request
    ) {

        return ResponseEntity.ok(
                loanService.updateLoan(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(
            @PathVariable UUID id
    ) {

        loanService.deleteLoan(id);

        return ResponseEntity.noContent().build();
    }
}