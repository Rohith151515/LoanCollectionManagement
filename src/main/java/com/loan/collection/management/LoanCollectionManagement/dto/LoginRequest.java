package com.loan.collection.management.LoanCollectionManagement.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Mobile is required")
        String mobile,

        @NotBlank(message = "Password is required")
        String password

){

}

