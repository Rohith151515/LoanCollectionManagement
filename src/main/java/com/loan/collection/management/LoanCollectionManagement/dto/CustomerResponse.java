package com.loan.collection.management.LoanCollectionManagement.dto;

import com.loan.collection.management.LoanCollectionManagement.model.ApprovalStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private UUID id;

    private String name;

    private String mobile;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String aadhaarNumber;

    private String aadhaarPhoto;

    private String guarantorName;

    private String guarantorMobile;

    private String guarantorAddress;

    private BigDecimal guarantorLatitude;

    private BigDecimal guarantorLongitude;

    private String guarantorAadhaarNumber;

    private String guarantorAadhaarPhoto;

    private UUID createdBy;

    private UUID approvedBy;

    private ApprovalStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}