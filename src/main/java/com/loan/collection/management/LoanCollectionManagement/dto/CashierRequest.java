package com.loan.collection.management.LoanCollectionManagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashierRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @Size(max = 20, message = "Aadhaar number cannot exceed 20 characters")
    private String aadhaarNumber;

    private String aadhaarPhoto;

    @Size(max = 100, message = "Guarantor name cannot exceed 100 characters")
    private String guarantorName;

    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Guarantor mobile must contain 10 to 15 digits"
    )
    private String guarantorMobile;

    private String guarantorAddress;

    @Size(max = 20, message = "Guarantor Aadhaar number cannot exceed 20 characters")
    private String guarantorAadhaarNumber;

    private String guarantorAadhaarPhoto;
}