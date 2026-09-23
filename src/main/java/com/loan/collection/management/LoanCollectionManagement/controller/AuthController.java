package com.loan.collection.management.LoanCollectionManagement.controller;

import com.loan.collection.management.LoanCollectionManagement.dto.LoginRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.LoginResponse;
import com.loan.collection.management.LoanCollectionManagement.dto.RegisterRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.RegisterResponse;
import com.loan.collection.management.LoanCollectionManagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response =
                authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}
