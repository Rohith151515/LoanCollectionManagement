package com.loan.collection.management.LoanCollectionManagement.Config;

import com.loan.collection.management.LoanCollectionManagement.model.User;
import com.loan.collection.management.LoanCollectionManagement.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");


        System.out.println("=================================");
        System.out.println(
                "REQUEST: " +
                        request.getMethod() +
                        " " +
                        request.getRequestURI()
        );

        System.out.println(
                "AUTH HEADER: " +
                        (authHeader == null
                                ? "null"
                                : "Bearer [TOKEN]")
        );


        // =====================================================
        // NO AUTHORIZATION HEADER
        // =====================================================

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            System.out.println("NO JWT TOKEN");

            filterChain.doFilter(request, response);
            return;
        }


        // =====================================================
        // GET TOKEN
        // =====================================================

        String token =
                authHeader.substring(7).trim();


        if (token.isEmpty()) {

            System.out.println("EMPTY JWT TOKEN");

            filterChain.doFilter(request, response);
            return;
        }


        // =====================================================
        // DON'T OVERWRITE EXISTING AUTHENTICATION
        // =====================================================

        if (SecurityContextHolder
                .getContext()
                .getAuthentication() != null) {

            System.out.println(
                    "AUTHENTICATION ALREADY EXISTS"
            );

            filterChain.doFilter(request, response);
            return;
        }


        // =====================================================
        // CHECK JWT STRUCTURE
        // =====================================================

        long dotCount = token.chars()
                .filter(ch -> ch == '.')
                .count();

        System.out.println(
                "JWT DOT COUNT: " +
                        dotCount
        );


        if (dotCount != 2) {

            System.out.println(
                    "MALFORMED JWT - EXPECTED 2 DOTS"
            );

            filterChain.doFilter(request, response);
            return;
        }


        try {

            // =================================================
            // VALIDATE TOKEN
            // =================================================

            if (!jwtService.isTokenValid(token)) {

                System.out.println(
                        "JWT TOKEN IS INVALID"
                );

                filterChain.doFilter(request, response);
                return;
            }


            System.out.println(
                    "========== JWT VALID =========="
            );


            // =================================================
            // EXTRACT USER ID
            // =================================================

            String userId =
                    jwtService.extractUserId(token);


            if (userId == null ||
                    userId.isBlank()) {

                System.out.println(
                        "USER ID NOT FOUND IN JWT"
                );

                filterChain.doFilter(request, response);
                return;
            }


            System.out.println(
                    "JWT USER ID FOUND"
            );


            // =================================================
            // CONVERT USER ID TO UUID
            // =================================================

            UUID uuid;

            try {

                uuid = UUID.fromString(userId);

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "INVALID USER UUID"
                );

                filterChain.doFilter(request, response);
                return;
            }


            // =================================================
            // FIND USER
            // =================================================

            User user =
                    userRepository
                            .findById(uuid)
                            .orElse(null);


            if (user == null) {

                System.out.println(
                        "USER NOT FOUND"
                );

                filterChain.doFilter(request, response);
                return;
            }


            // =================================================
            // CREATE SPRING SECURITY ROLE
            // =================================================

            String role =
                    "ROLE_" +
                            user.getRole().name();


            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority(role);


            System.out.println(
                    "USER ROLE: " +
                            role
            );


            // =================================================
            // CREATE AUTHENTICATION
            // =================================================

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getId(),
                            null,
                            List.of(authority)
                    );


            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);


            // =================================================
            // SUCCESS
            // =================================================

            System.out.println(
                    "AUTHENTICATION SUCCESS"
            );

            System.out.println(
                    "AUTHORITIES: " +
                            authentication.getAuthorities()
            );

        } catch (Exception e) {

            SecurityContextHolder.clearContext();

            System.out.println(
                    "JWT FILTER ERROR: " +
                            e.getMessage()
            );

            e.printStackTrace();
        }


        // =====================================================
        // CONTINUE REQUEST
        // =====================================================

        filterChain.doFilter(request, response);
    }
}