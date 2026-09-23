
        package com.loan.collection.management.LoanCollectionManagement.Config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // REST API - disable CSRF
                .csrf(csrf -> csrf.disable())

                // Disable CORS here if you are not configuring
                // Spring Security CORS separately
                .cors(cors -> cors.disable())

                // Disable browser login
                .formLogin(form -> form.disable())

                // Disable HTTP Basic authentication
                .httpBasic(basic -> basic.disable())

                // Disable logout endpoint
                .logout(logout -> logout.disable())

                // JWT authentication is stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // =================================================
                        // PUBLIC APIs
                        // =================================================

                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login"
                        ).permitAll()


                        // =================================================
                        // CASHIER APIs
                        // =================================================

                        // Admin can access cashier APIs
                        .requestMatchers(
                                "/api/v1/cashiers/**"
                        ).hasRole("ADMIN")


                        // =================================================
                        // CUSTOMER APIs
                        // =================================================

                        // CASHIER + ADMIN can create customers
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/customers"
                        ).hasAnyRole("ADMIN", "CASHIER")


                        // CASHIER + ADMIN can view customers
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/customers/**"
                        ).hasAnyRole("ADMIN", "CASHIER")


                        // CASHIER + ADMIN can update customers
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/customers/**"
                        ).hasAnyRole("ADMIN", "CASHIER")


                        // Only ADMIN can approve/reject customers
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/customers/*/approval"
                        ).hasRole("ADMIN")


                        // Only ADMIN can delete customers
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/customers/**"
                        ).hasRole("ADMIN")


                        // =================================================
                        // USER APIs
                        // =================================================

                        .requestMatchers(
                                "/api/v1/users/**"
                        ).authenticated()


                        // =================================================
                        // EVERYTHING ELSE
                        // =================================================

                        .anyRequest().authenticated()
                )

                // JWT filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}

