package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.dto.CashierApprovalRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CashierRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.CashierResponse;
import com.loan.collection.management.LoanCollectionManagement.exception.ResourceNotFoundException;
import com.loan.collection.management.LoanCollectionManagement.model.Cashier;
import com.loan.collection.management.LoanCollectionManagement.model.User;
import com.loan.collection.management.LoanCollectionManagement.repository.CashierRepository;
import com.loan.collection.management.LoanCollectionManagement.repository.UserRepository;
import com.loan.collection.management.LoanCollectionManagement.service.CashierService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CashierServiceImpl implements CashierService {

    private final CashierRepository cashierRepository;
    private final UserRepository userRepository;

    @Override
    public CashierResponse createCashier(CashierRequest request) {

        if (cashierRepository.existsByUser_Id(request.getUserId())) {
            throw new IllegalArgumentException(
                    "Cashier already exists for user: " + request.getUserId()
            );
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found: " + request.getUserId()
                        )
                );

        Cashier cashier = Cashier.builder()
                .user(user)
                .address(request.getAddress())
                .aadhaarNumber(request.getAadhaarNumber())
                .aadhaarPhoto(request.getAadhaarPhoto())
                .guarantorName(request.getGuarantorName())
                .guarantorMobile(request.getGuarantorMobile())
                .guarantorAddress(request.getGuarantorAddress())
                .guarantorAadhaarNumber(request.getGuarantorAadhaarNumber())
                .guarantorAadhaarPhoto(request.getGuarantorAadhaarPhoto())
                .build();

        Cashier savedCashier = cashierRepository.save(cashier);

        return mapToResponse(savedCashier);
    }

    @Override
    @Transactional(readOnly = true)
    public CashierResponse getCashierById(UUID id) {

        Cashier cashier = cashierRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cashier not found: " + id
                        )
                );

        return mapToResponse(cashier);
    }

    @Override
    @Transactional(readOnly = true)
    public CashierResponse getCashierByUserId(UUID userId) {

        Cashier cashier = cashierRepository
                .findByUser_Id(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cashier not found for user: " + userId
                        )
                );

        if (cashier.getDeletedAt() != null) {
            throw new ResourceNotFoundException(
                    "Cashier has been deleted"
            );
        }

        return mapToResponse(cashier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CashierResponse> getAllCashiers() {

        return cashierRepository
                .findAllByDeletedAtIsNull()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CashierResponse updateCashier(
            UUID id,
            CashierRequest request
    ) {

        Cashier cashier = cashierRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cashier not found: " + id
                        )
                );

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found: " + request.getUserId()
                        )
                );

        cashier.setUser(user);
        cashier.setAddress(request.getAddress());
        cashier.setAadhaarNumber(request.getAadhaarNumber());
        cashier.setAadhaarPhoto(request.getAadhaarPhoto());
        cashier.setGuarantorName(request.getGuarantorName());
        cashier.setGuarantorMobile(request.getGuarantorMobile());
        cashier.setGuarantorAddress(request.getGuarantorAddress());
        cashier.setGuarantorAadhaarNumber(
                request.getGuarantorAadhaarNumber()
        );
        cashier.setGuarantorAadhaarPhoto(
                request.getGuarantorAadhaarPhoto()
        );

        Cashier updatedCashier = cashierRepository.save(cashier);

        return mapToResponse(updatedCashier);
    }

    @Override
    public CashierResponse approveCashier(
            UUID cashierId,
            CashierApprovalRequest request
    ) {

        Cashier cashier = cashierRepository
                .findByIdAndDeletedAtIsNull(cashierId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cashier not found: " + cashierId
                        )
                );

        User approver = userRepository
                .findById(request.getApprovedBy())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Approver user not found: "
                                        + request.getApprovedBy()
                        )
                );

        cashier.setApprovedBy(approver);
        cashier.setApprovedAt(LocalDateTime.now());

        Cashier savedCashier = cashierRepository.save(cashier);

        return mapToResponse(savedCashier);
    }

    @Override
    public void deleteCashier(UUID id) {

        Cashier cashier = cashierRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cashier not found: " + id
                        )
                );

        cashier.setDeletedAt(LocalDateTime.now());

        cashierRepository.save(cashier);
    }

    private CashierResponse mapToResponse(Cashier cashier) {

        return CashierResponse.builder()
                .id(cashier.getId())

                .userId(
                        cashier.getUser() != null
                                ? cashier.getUser().getId()
                                : null
                )

                .address(cashier.getAddress())
                .aadhaarNumber(cashier.getAadhaarNumber())
                .aadhaarPhoto(cashier.getAadhaarPhoto())

                .guarantorName(cashier.getGuarantorName())
                .guarantorMobile(cashier.getGuarantorMobile())
                .guarantorAddress(cashier.getGuarantorAddress())
                .guarantorAadhaarNumber(
                        cashier.getGuarantorAadhaarNumber()
                )
                .guarantorAadhaarPhoto(
                        cashier.getGuarantorAadhaarPhoto()
                )

                .approvedBy(
                        cashier.getApprovedBy() != null
                                ? cashier.getApprovedBy().getId()
                                : null
                )

                .approvedAt(cashier.getApprovedAt())
                .createdAt(cashier.getCreatedAt())
                .updatedAt(cashier.getUpdatedAt())

                .build();
    }
}