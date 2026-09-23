package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.dto.CashierApprovalRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CashierRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CashierResponse;

import java.util.List;
import java.util.UUID;

public interface CashierService {

    CashierResponse createCashier(CashierRequest request);

    CashierResponse getCashierById(UUID id);

    CashierResponse getCashierByUserId(UUID userId);

    List<CashierResponse> getAllCashiers();

    CashierResponse updateCashier(UUID id, CashierRequest request);

    CashierResponse approveCashier(
            UUID cashierId,
            CashierApprovalRequest request
    );

    void deleteCashier(UUID id);
}