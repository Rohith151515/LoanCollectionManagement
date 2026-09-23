package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.dto.UserRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(UUID id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(UUID id, UserRequest request);

    void deleteUser(UUID id);
}