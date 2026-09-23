package com.loan.collection.management.LoanCollectionManagement.controller;

import com.loan.collection.management.LoanCollectionManagement.dto.CashierApprovalRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CashierRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CashierResponse;
import com.loan.collection.management.LoanCollectionManagement.service.CashierService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cashiers")
@RequiredArgsConstructor
public class CashierController {

    private final CashierService cashierService;

    @PostMapping
    public ResponseEntity<CashierResponse> createCashier(
            @Valid @RequestBody CashierRequest request
    ) {

        CashierResponse response =
                cashierService.createCashier(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CashierResponse>> getAllCashiers() {

        return ResponseEntity.ok(
                cashierService.getAllCashiers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashierResponse> getCashierById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                cashierService.getCashierById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CashierResponse> getCashierByUserId(
            @PathVariable UUID userId
    ) {

        return ResponseEntity.ok(
                cashierService.getCashierByUserId(userId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CashierResponse> updateCashier(
            @PathVariable UUID id,
            @Valid @RequestBody CashierRequest request
    ) {

        return ResponseEntity.ok(
                cashierService.updateCashier(id, request)
        );
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<CashierResponse> approveCashier(
            @PathVariable UUID id,
            @Valid @RequestBody CashierApprovalRequest request
    ) {

        return ResponseEntity.ok(
                cashierService.approveCashier(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashier(
            @PathVariable UUID id
    ) {

        cashierService.deleteCashier(id);

        return ResponseEntity.noContent().build();
    }
}