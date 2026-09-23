package com.loan.collection.management.LoanCollectionManagement.repository;

import com.loan.collection.management.LoanCollectionManagement.model.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CashierRepository extends JpaRepository<Cashier, UUID> {

    Optional<Cashier> findByUser_Id(UUID userId);

    boolean existsByUser_Id(UUID userId);

    List<Cashier> findAllByDeletedAtIsNull();

    Optional<Cashier> findByIdAndDeletedAtIsNull(UUID id);
}