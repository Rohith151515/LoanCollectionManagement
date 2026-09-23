package com.loan.collection.management.LoanCollectionManagement.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    @NotBlank(message = "Customer name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Mobile number must contain 10 to 15 digits"
    )
    private String mobile;

    private String address;

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal latitude;

    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal longitude;

    @Size(max = 20)
    private String aadhaarNumber;

    private String aadhaarPhoto;

    @Size(max = 100)
    private String guarantorName;

    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Guarantor mobile must contain 10 to 15 digits"
    )
    private String guarantorMobile;

    private String guarantorAddress;

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal guarantorLatitude;

    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal guarantorLongitude;

    @Size(max = 20)
    private String guarantorAadhaarNumber;

    private String guarantorAadhaarPhoto;
}