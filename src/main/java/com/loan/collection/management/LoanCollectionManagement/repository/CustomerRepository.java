package com.loan.collection.management.LoanCollectionManagement.repository;

import com.loan.collection.management.LoanCollectionManagement.model.ApprovalStatus;
import com.loan.collection.management.LoanCollectionManagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository
        extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByIdAndDeletedAtIsNull(UUID id);

    List<Customer> findAllByDeletedAtIsNull();

    List<Customer> findAllByStatusAndDeletedAtIsNull(
            ApprovalStatus status
    );

    List<Customer> findAllByCreatedBy_IdAndDeletedAtIsNull(
            UUID createdBy
    );

    long countByStatusAndDeletedAtIsNull(
            ApprovalStatus status
    );
}