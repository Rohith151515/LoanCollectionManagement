package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.dto.UserRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.UserResponse;
import com.loan.collection.management.LoanCollectionManagement.model.User;
import com.loan.collection.management.LoanCollectionManagement.model.UserStatus;
import com.loan.collection.management.LoanCollectionManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest request) {

        // Check mobile
        if (userRepository.existsByMobile(request.mobile())) {
            throw new RuntimeException(
                    "Mobile number already exists"
            );
        }

        // Check email
        if (request.email() != null
                && !request.email().isBlank()
                && userRepository.existsByEmail(request.email())) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        User user = User.builder()
                .role(request.role())
                .name(request.name())
                .mobile(request.mobile())
                .email(request.email())
                .passwordHash(
                        passwordEncoder.encode(request.password())
                )
                .status(
                        request.status() != null
                                ? request.status()
                                : UserStatus.ACTIVE
                )
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: " + id
                        )
                );

        return mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(
            UUID id,
            UserRequest request
    ) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: " + id
                        )
                );

        // Check mobile if changed
        if (!user.getMobile().equals(request.mobile())
                && userRepository.existsByMobile(request.mobile())) {

            throw new RuntimeException(
                    "Mobile number already exists"
            );
        }

        // Check email if changed
        if (request.email() != null
                && !request.email().equals(user.getEmail())
                && userRepository.existsByEmail(request.email())) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        user.setRole(request.role());
        user.setName(request.name());
        user.setMobile(request.mobile());
        user.setEmail(request.email());

        if (request.status() != null) {
            user.setStatus(request.status());
        }

        // Update password only if supplied
        if (request.password() != null
                && !request.password().isBlank()) {

            user.setPasswordHash(
                    passwordEncoder.encode(request.password())
            );
        }

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException(
                    "User not found with id: " + id
            );
        }

        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getRole(),
                user.getName(),
                user.getMobile(),
                user.getEmail(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}