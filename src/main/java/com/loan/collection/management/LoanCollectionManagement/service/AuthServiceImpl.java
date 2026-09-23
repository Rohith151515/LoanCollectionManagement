
        package com.loan.collection.management.LoanCollectionManagement.service;

import com.loan.collection.management.LoanCollectionManagement.Config.JwtService;
import com.loan.collection.management.LoanCollectionManagement.dto.LoginRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.LoginResponse;
import com.loan.collection.management.LoanCollectionManagement.dto.RegisterRequest;
import com.loan.collection.management.LoanCollectionManagement.dto.RegisterResponse;
import com.loan.collection.management.LoanCollectionManagement.model.User;
import com.loan.collection.management.LoanCollectionManagement.model.UserStatus;
import com.loan.collection.management.LoanCollectionManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByMobile(request.mobile())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid mobile or password"
                        )
                );

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash())) {

            throw new RuntimeException(
                    "Invalid mobile or password"
            );
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException(
                    "User account is inactive"
            );
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByMobile(request.mobile())) {
            throw new RuntimeException(
                    "Mobile number already registered"
            );
        }

        if (request.email() != null &&
                !request.email().isBlank() &&
                userRepository.existsByEmail(request.email())) {

            throw new RuntimeException(
                    "Email already registered"
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
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getRole(),
                savedUser.getName(),
                savedUser.getMobile(),
                savedUser.getEmail(),
                savedUser.getStatus()

        );
    }
}

