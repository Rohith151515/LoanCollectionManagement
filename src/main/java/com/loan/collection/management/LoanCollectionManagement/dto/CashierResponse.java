package com.loan.collection.management.LoanCollectionManagement.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashierResponse {

    private UUID id;

    private UUID userId;

    private String address;

    private String aadhaarNumber;

    private String aadhaarPhoto;

    private String guarantorName;

    private String guarantorMobile;

    private String guarantorAddress;

    private String guarantorAadhaarNumber;

    private String guarantorAadhaarPhoto;

    private UUID approvedBy;

    private LocalDateTime approvedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}