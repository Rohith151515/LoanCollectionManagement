package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.dto.LoanRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.LoanResponse;
import com.loan.collection.management.LoanCollectionManagement.model.Customer;
import com.loan.collection.management.LoanCollectionManagement.model.Loan;
import com.loan.collection.management.LoanCollectionManagement.model.LoanStatus;
import com.loan.collection.management.LoanCollectionManagement.repository.LoanRepository;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public LoanResponse createLoan(LoanRequest request) {

        // Validate amounts
        validateLoanAmounts(request);

        // Customer reference
        Customer customer = new Customer();
        customer.setId(request.getCustomerId());

        // Discount
        BigDecimal discount =
                request.getDiscount() != null
                        ? request.getDiscount()
                        : BigDecimal.ZERO;

        /*
         * Initial outstanding amount.
         *
         * Example:
         * loan amount = 10000
         * discount    = 500
         * outstanding = 9500
         */
        BigDecimal outstandingAmount =
                request.getLoanAmount()
                        .subtract(discount);

        Loan loan = Loan.builder()

                .customer(customer)

                .principalAmount(
                        request.getPrincipalAmount()
                )

                .loanAmount(
                        request.getLoanAmount()
                )

                .discount(discount)

                .outstandingAmount(
                        outstandingAmount
                )

                .disbursalDate(
                        request.getDisbursalDate()
                )

                .tentativeSettlementDate(
                        request.getTentativeSettlementDate()
                )

                .status(LoanStatus.ACTIVE)

                .build();

        Loan savedLoan =
                loanRepository.save(loan);

        return LoanResponse.fromEntity(savedLoan);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanResponse getLoanById(UUID id) {

        Loan loan =
                loanRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Loan not found with id: " + id
                                )
                        );

        return LoanResponse.fromEntity(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponse> getAllLoans() {

        return loanRepository.findAll()
                .stream()
                .map(LoanResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponse> getLoansByCustomer(
            UUID customerId
    ) {

        return loanRepository
                .findByCustomerId(customerId)
                .stream()
                .map(LoanResponse::fromEntity)
                .toList();
    }

    @Override
    public LoanResponse updateLoan(
            UUID id,
            LoanRequest request
    ) {

        validateLoanAmounts(request);

        Loan loan =
                loanRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Loan not found with id: " + id
                                )
                        );

        Customer customer = new Customer();
        customer.setId(request.getCustomerId());

        BigDecimal discount =
                request.getDiscount() != null
                        ? request.getDiscount()
                        : BigDecimal.ZERO;

        BigDecimal outstandingAmount =
                request.getLoanAmount()
                        .subtract(discount);

        loan.setCustomer(customer);

        loan.setPrincipalAmount(
                request.getPrincipalAmount()
        );

        loan.setLoanAmount(
                request.getLoanAmount()
        );

        loan.setDiscount(discount);

        loan.setOutstandingAmount(
                outstandingAmount
        );

        loan.setDisbursalDate(
                request.getDisbursalDate()
        );

        loan.setTentativeSettlementDate(
                request.getTentativeSettlementDate()
        );

        loan.setActualSettlementDate(
                request.getActualSettlementDate()
        );

        if (request.getStatus() != null) {
            loan.setStatus(
                    LoanStatus.valueOf(
                            request.getStatus().toUpperCase()
                    )
            );
        }

        loan.setUpdatedAt(
                LocalDateTime.now()
        );

        Loan updatedLoan =
                loanRepository.save(loan);

        return LoanResponse.fromEntity(updatedLoan);
    }

    @Override
    public void deleteLoan(UUID id) {

        Loan loan =
                loanRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Loan not found with id: " + id
                                )
                        );

        /*
         * Soft delete
         */
        loan.setDeletedAt(
                LocalDateTime.now()
        );

        loanRepository.save(loan);
    }

    private void validateLoanAmounts(
            LoanRequest request
    ) {

        if (
                request.getLoanAmount()
                        .compareTo(
                                request.getPrincipalAmount()
                        ) < 0
        ) {
            throw new IllegalArgumentException(
                    "Loan amount cannot be less than principal amount"
            );
        }

        BigDecimal discount =
                request.getDiscount() != null
                        ? request.getDiscount()
                        : BigDecimal.ZERO;

        if (
                discount.compareTo(
                        request.getLoanAmount()
                ) > 0
        ) {
            throw new IllegalArgumentException(
                    "Discount cannot be greater than loan amount"
            );
        }
    }
}