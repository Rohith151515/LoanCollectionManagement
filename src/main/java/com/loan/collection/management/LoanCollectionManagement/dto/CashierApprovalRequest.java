package com.loan.collection.management.LoanCollectionManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashierApprovalRequest {

    @NotNull(message = "Approver user ID is required")
    private UUID approvedBy;
}