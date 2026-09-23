package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.dto.CustomerApprovalRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CustomerRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CustomerResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerResponse createCustomer(
            CustomerRequest request,
            UUID createdBy
    );

    CustomerResponse getCustomerById(UUID id);

    List<CustomerResponse> getAllCustomers();

    List<CustomerResponse> getPendingCustomers();

    List<CustomerResponse> getCustomersByCreator(UUID userId);

    CustomerResponse updateCustomer(
            UUID id,
            CustomerRequest request
    );

    CustomerResponse approveOrRejectCustomer(
            UUID id,
            CustomerApprovalRequest request,
            UUID approvedBy
    );

    void deleteCustomer(UUID id);
}