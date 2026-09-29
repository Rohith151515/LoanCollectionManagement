package com.loan.collection.management.LoanCollectionManagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.UUID;

@Data
public class CashierRequest {

    @NotNull(message = "User ID is required")
    @JsonProperty("userId")
    private UUID userId;

    @NotBlank(message = "Address is required")
    @JsonProperty("address")
    private String address;

    @JsonProperty("aadhaarNumber")
    private String aadhaarNumber;

    @JsonProperty("aadhaarPhoto")
    private String aadhaarPhoto;

    @JsonProperty("guarantorName")
    private String guarantorName;

    @JsonProperty("guarantorMobile")
    private String guarantorMobile;

    @JsonProperty("guarantorAddress")
    private String guarantorAddress;

    @JsonProperty("guarantorAadhaarNumber")
    private String guarantorAadhaarNumber;

    @JsonProperty("guarantorAadhaarPhoto")
    private String guarantorAadhaarPhoto;
}