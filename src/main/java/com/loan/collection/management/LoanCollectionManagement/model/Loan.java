
        package com.loan.collection.management.LoanCollectionManagement.model;

import com.loan.collection.management.LoanCollectionManagement.model.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    private Customer customer;

    @Column(
            name = "principal_amount",
            precision = 12,
            scale = 2,
            nullable = false
    )
    private BigDecimal principalAmount;

    @Column(
            name = "loan_amount",
            precision = 12,
            scale = 2,
            nullable = false
    )
    private BigDecimal loanAmount;

    @Column(
            name = "outstanding_amount",
            precision = 12,
            scale = 2,
            nullable = false
    )
    private BigDecimal outstandingAmount;

    @Column(
            name = "discount",
            precision = 12,
            scale = 2
    )
    private BigDecimal discount;

    @Column(
            name = "disbursal_date",
            nullable = false
    )
    private LocalDate disbursalDate;

    @Column(name = "tentative_settlement_date")
    private LocalDate tentativeSettlementDate;

    @Column(name = "actual_settlement_date")
    private LocalDate actualSettlementDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (discount == null) {
            discount = BigDecimal.ZERO;
        }

        if (outstandingAmount == null) {
            outstandingAmount = loanAmount;
        }

        if (status == null) {
            status = LoanStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}