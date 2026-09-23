package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.dto.LoginRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.LoginResponse;
import com.loan.collection.management.LoanCollectionManagement.dto.RegisterRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.RegisterResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest request);
}
