package com.loan.collection.management.LoanCollectionManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "cashiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cashier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_cashier_user")
    )
    private User user;

    @Column(name = "address")
    private String address;

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

    @Column(name = "guarantor_aadhaar_number", length = 20)
    private String guarantorAadhaarNumber;

    @Column(name = "guarantor_aadhaar_photo")
    private String guarantorAadhaarPhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}