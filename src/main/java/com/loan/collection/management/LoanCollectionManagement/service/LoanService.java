package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.dto.LoanRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.LoanResponse;

import java.util.List;
import java.util.UUID;

public interface LoanService {

    LoanResponse createLoan(LoanRequest request);

    LoanResponse getLoanById(UUID id);

    List<LoanResponse> getAllLoans();

    List<LoanResponse> getLoansByCustomer(UUID customerId);

    LoanResponse updateLoan(
            UUID id,
            LoanRequest request
    );

    void deleteLoan(UUID id);
}