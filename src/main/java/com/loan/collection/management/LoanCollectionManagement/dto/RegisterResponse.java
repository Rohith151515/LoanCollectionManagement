
        package com.loan.collection.management.LoanCollectionManagement.dto;

import com.loan.collection.management.LoanCollectionManagement.model.UserRole;
import com.loan.collection.management.LoanCollectionManagement.model.UserStatus;

import java.util.UUID;

public record RegisterResponse(

        UUID id,

        UserRole role,

        String name,

        String mobile,

        String email,

        UserStatus status


) {
}

