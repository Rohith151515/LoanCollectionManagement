package com.loan.collection.management.LoanCollectionManagement.controller;

import com.loan.collection.management.LoanCollectionManagement.dto.*;
import com.loan.collection.management.LoanCollectionManagement.service.CashierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cashiers")
@RequiredArgsConstructor
public class CashierController {

    private final CashierService cashierService;

    // ============================================================
    // CREATE CASHIER
    // ============================================================

    @PostMapping
    public ResponseEntity<ApiResponse<CashierResponse>> createCashier(
            @Valid @RequestBody CashierRequest request
    ) {

        CashierResponse response =
                cashierService.createCashier(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Cashier created successfully",
                        response,
                        LocalDateTime.now()
                ));
    }

    // ============================================================
    // GET ALL CASHIERS
    // ============================================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<CashierResponse>>> getAllCashiers() {

        List<CashierResponse> cashiers =
                cashierService.getAllCashiers();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cashiers retrieved successfully",
                        cashiers,
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // GET CASHIER BY ID
    // ============================================================

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CashierResponse>> getCashierById(
            @PathVariable UUID id
    ) {

        CashierResponse response =
                cashierService.getCashierById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cashier retrieved successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // GET CASHIER BY USER ID
    // ============================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<CashierResponse>> getCashierByUserId(
            @PathVariable UUID userId
    ) {

        CashierResponse response =
                cashierService.getCashierByUserId(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cashier retrieved successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // UPDATE CASHIER
    // ============================================================

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CashierResponse>> updateCashier(
            @PathVariable UUID id,
            @Valid @RequestBody CashierRequest request
    ) {

        CashierResponse response =
                cashierService.updateCashier(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cashier updated successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // APPROVE CASHIER
    // ============================================================

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<CashierResponse>> approveCashier(
            @PathVariable UUID id,
            @Valid @RequestBody CashierApprovalRequest request
    ) {

        CashierResponse response =
                cashierService.approveCashier(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cashier approved successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // DELETE CASHIER
    // ============================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCashier(
            @PathVariable UUID id
    ) {

        cashierService.deleteCashier(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cashier deleted successfully",
                        null,
                        LocalDateTime.now()
                )
        );
    }
}