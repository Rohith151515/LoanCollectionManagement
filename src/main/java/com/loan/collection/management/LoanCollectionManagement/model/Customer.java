package com.loan.collection.management.LoanCollectionManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "mobile", length = 15)
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "aadhaar_number", length = 20)
    private String aadhaarNumber;

    @Column(name = "aadhaar_photo")
    private String aadhaarPhoto;

    @Column(name = "guarantor_name", length = 100)
    private String guarantorName;

    @Column(name = "guarantor_mobile", length = 15)
    private String guarantorMobile;

    @Column(name = "guarantor_address")
    private String guarantorAddress;

    @Column(name = "guarantor_latitude", precision = 10, scale = 8)
    private BigDecimal guarantorLatitude;

    @Column(name = "guarantor_longitude", precision = 11, scale = 8)
    private BigDecimal guarantorLongitude;

    @Column(name = "guarantor_aadhaar_number", length = 20)
    private String guarantorAadhaarNumber;

    @Column(name = "guarantor_aadhaar_photo")
    private String guarantorAadhaarPhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            columnDefinition = "approval_status"
    )
    private ApprovalStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = ApprovalStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}