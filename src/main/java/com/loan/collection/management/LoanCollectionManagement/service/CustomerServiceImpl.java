package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.dto.CustomerApprovalRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CustomerRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CustomerResponse;
import com.loan.collection.management.LoanCollectionManagement.exception.ResourceNotFoundException;
import com.loan.collection.management.LoanCollectionManagement.model.ApprovalStatus;
import com.loan.collection.management.LoanCollectionManagement.model.Customer;
import com.loan.collection.management.LoanCollectionManagement.model.User;
import com.loan.collection.management.LoanCollectionManagement.repository.CustomerRepository;
import com.loan.collection.management.LoanCollectionManagement.repository.UserRepository;
import com.loan.collection.management.LoanCollectionManagement.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    public CustomerResponse createCustomer(
            CustomerRequest request,
            UUID createdBy
    ) {

        User creator = userRepository.findById(createdBy)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Creator user not found: " + createdBy
                        )
                );

        Customer customer = Customer.builder()
                .name(request.getName())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())

                .aadhaarNumber(request.getAadhaarNumber())
                .aadhaarPhoto(request.getAadhaarPhoto())

                .guarantorName(request.getGuarantorName())
                .guarantorMobile(request.getGuarantorMobile())
                .guarantorAddress(request.getGuarantorAddress())

                .guarantorLatitude(
                        request.getGuarantorLatitude()
                )

                .guarantorLongitude(
                        request.getGuarantorLongitude()
                )

                .guarantorAadhaarNumber(
                        request.getGuarantorAadhaarNumber()
                )

                .guarantorAadhaarPhoto(
                        request.getGuarantorAadhaarPhoto()
                )

                .createdBy(creator)

                .status(ApprovalStatus.PENDING)

                .build();

        Customer savedCustomer =
                customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(UUID id) {

        Customer customer =
                customerRepository
                        .findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Customer not found: " + id
                                )
                        );

        return mapToResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository
                .findAllByDeletedAtIsNull()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getPendingCustomers() {

        return customerRepository
                .findAllByStatusAndDeletedAtIsNull(
                        ApprovalStatus.PENDING
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomersByCreator(
            UUID userId
    ) {

        return customerRepository
                .findAllByCreatedBy_IdAndDeletedAtIsNull(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(
            UUID id,
            CustomerRequest request
    ) {

        Customer customer =
                customerRepository
                        .findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Customer not found: " + id
                                )
                        );

        customer.setName(request.getName());
        customer.setMobile(request.getMobile());
        customer.setAddress(request.getAddress());
        customer.setLatitude(request.getLatitude());
        customer.setLongitude(request.getLongitude());

        customer.setAadhaarNumber(
                request.getAadhaarNumber()
        );

        customer.setAadhaarPhoto(
                request.getAadhaarPhoto()
        );

        customer.setGuarantorName(
                request.getGuarantorName()
        );

        customer.setGuarantorMobile(
                request.getGuarantorMobile()
        );

        customer.setGuarantorAddress(
                request.getGuarantorAddress()
        );

        customer.setGuarantorLatitude(
                request.getGuarantorLatitude()
        );

        customer.setGuarantorLongitude(
                request.getGuarantorLongitude()
        );

        customer.setGuarantorAadhaarNumber(
                request.getGuarantorAadhaarNumber()
        );

        customer.setGuarantorAadhaarPhoto(
                request.getGuarantorAadhaarPhoto()
        );

        Customer updatedCustomer =
                customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    @Override
    public CustomerResponse approveOrRejectCustomer(
            UUID id,
            CustomerApprovalRequest request,
            UUID approvedBy
    ) {

        Customer customer =
                customerRepository
                        .findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Customer not found: " + id
                                )
                        );

        User approver =
                userRepository.findById(approvedBy)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Approver user not found: "
                                                + approvedBy
                                )
                        );

        ApprovalStatus status;

        try {
            status = ApprovalStatus.valueOf(
                    request.getStatus().toUpperCase()
            );
        } catch (IllegalArgumentException exception) {

            throw new IllegalArgumentException(
                    "Invalid status. Use APPROVED or REJECTED"
            );
        }

        if (status == ApprovalStatus.PENDING) {
            throw new IllegalArgumentException(
                    "Customer cannot be manually changed to PENDING"
            );
        }

        customer.setStatus(status);
        customer.setApprovedBy(approver);
        customer.setUpdatedAt(LocalDateTime.now());

        Customer savedCustomer =
                customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {

        Customer customer =
                customerRepository
                        .findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Customer not found: " + id
                                )
                        );

        customer.setDeletedAt(LocalDateTime.now());

        customerRepository.save(customer);
    }

    private CustomerResponse mapToResponse(
            Customer customer
    ) {

        return CustomerResponse.builder()

                .id(customer.getId())

                .name(customer.getName())
                .mobile(customer.getMobile())
                .address(customer.getAddress())

                .latitude(customer.getLatitude())
                .longitude(customer.getLongitude())

                .aadhaarNumber(
                        customer.getAadhaarNumber()
                )

                .aadhaarPhoto(
                        customer.getAadhaarPhoto()
                )

                .guarantorName(
                        customer.getGuarantorName()
                )

                .guarantorMobile(
                        customer.getGuarantorMobile()
                )

                .guarantorAddress(
                        customer.getGuarantorAddress()
                )

                .guarantorLatitude(
                        customer.getGuarantorLatitude()
                )

                .guarantorLongitude(
                        customer.getGuarantorLongitude()
                )

                .guarantorAadhaarNumber(
                        customer.getGuarantorAadhaarNumber()
                )

                .guarantorAadhaarPhoto(
                        customer.getGuarantorAadhaarPhoto()
                )

                .createdBy(
                        customer.getCreatedBy() != null
                                ? customer.getCreatedBy().getId()
                                : null
                )

                .approvedBy(
                        customer.getApprovedBy() != null
                                ? customer.getApprovedBy().getId()
                                : null
                )

                .status(customer.getStatus())

                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .deletedAt(customer.getDeletedAt())

                .build();
    }
}