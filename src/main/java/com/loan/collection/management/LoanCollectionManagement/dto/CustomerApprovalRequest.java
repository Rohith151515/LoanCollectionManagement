package com.loan.collection.management.LoanCollectionManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerApprovalRequest {

    @NotNull(message = "Approval status is required")
    private String status;
}