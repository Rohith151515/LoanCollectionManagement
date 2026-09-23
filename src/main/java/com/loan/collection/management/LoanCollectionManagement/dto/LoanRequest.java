package com.loan.collection.management.LoanCollectionManagement.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRequest {

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    @NotNull(message = "Principal amount is required")
    @DecimalMin(
            value = "0.00",
            inclusive = true,
            message = "Principal amount cannot be negative"
    )
    private BigDecimal principalAmount;

    @NotNull(message = "Loan amount is required")
    @DecimalMin(
            value = "0.00",
            inclusive = true,
            message = "Loan amount cannot be negative"
    )
    private BigDecimal loanAmount;

    @DecimalMin(
            value = "0.00",
            inclusive = true,
            message = "Discount cannot be negative"
    )
    private BigDecimal discount;

    @NotNull(message = "Disbursal date is required")
    private LocalDate disbursalDate;

    private LocalDate tentativeSettlementDate;

    private LocalDate actualSettlementDate;

    private String status;
}