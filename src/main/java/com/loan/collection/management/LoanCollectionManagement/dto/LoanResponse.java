package com.loan.collection.management.LoanCollectionManagement.dto;

import com.loan.collection.management.LoanCollectionManagement.model.Loan;
import com.loan.collection.management.LoanCollectionManagement.model.LoanStatus;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanResponse {

    private UUID id;

    private UUID customerId;

    private String customerName;

    private String customerMobile;

    private BigDecimal principalAmount;

    private BigDecimal loanAmount;

    private BigDecimal outstandingAmount;

    private BigDecimal discount;

    private LocalDate disbursalDate;

    private LocalDate tentativeSettlementDate;

    private LocalDate actualSettlementDate;

    private LoanStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static LoanResponse fromEntity(Loan loan) {

        return LoanResponse.builder()
                .id(loan.getId())

                .customerId(
                        loan.getCustomer() != null
                                ? loan.getCustomer().getId()
                                : null
                )

                .customerName(
                        loan.getCustomer() != null
                                ? loan.getCustomer().getName()
                                : null
                )

                .customerMobile(
                        loan.getCustomer() != null
                                ? loan.getCustomer().getMobile()
                                : null
                )

                .principalAmount(
                        loan.getPrincipalAmount()
                )

                .loanAmount(
                        loan.getLoanAmount()
                )

                .outstandingAmount(
                        loan.getOutstandingAmount()
                )

                .discount(
                        loan.getDiscount()
                )

                .disbursalDate(
                        loan.getDisbursalDate()
                )

                .tentativeSettlementDate(
                        loan.getTentativeSettlementDate()
                )

                .actualSettlementDate(
                        loan.getActualSettlementDate()
                )

                .status(
                        loan.getStatus()
                )

                .createdAt(
                        loan.getCreatedAt()
                )

                .updatedAt(
                        loan.getUpdatedAt()
                )

                .build();
    }
}