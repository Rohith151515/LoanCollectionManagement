package com.loan.collection.management.LoanCollectionManagement.repository;

import com.loan.collection.management.LoanCollectionManagement.model.Loan;
import com.loan.collection.management.LoanCollectionManagement.model.LoanStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository
        extends JpaRepository<Loan, UUID> {

    List<Loan> findByCustomerId(UUID customerId);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByCustomerIdAndStatus(
            UUID customerId,
            LoanStatus status
    );
}