package com.loan.collection.management.LoanCollectionManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CashierUpdateRequest {

    @NotBlank(message = "Address is required")
    private String address;

    private String aadhaarNumber;

    private String aadhaarPhoto;

    private String guarantorName;

    private String guarantorMobile;

    private String guarantorAddress;

    private String guarantorAadhaarNumber;

    private String guarantorAadhaarPhoto;
}