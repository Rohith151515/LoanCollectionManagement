package com.loan.collection.management.LoanCollectionManagement.Config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // ============================================
                // CSRF
                // ============================================

                .csrf(csrf -> csrf.disable())


                // ============================================
                // SESSION
                // ============================================

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                // ============================================
                // AUTHENTICATION PROVIDER
                // ============================================

                .authenticationProvider(
                        authenticationProvider()
                )


                // ============================================
                // AUTHORIZATION
                // ============================================

                .authorizeHttpRequests(auth -> auth

                        // ------------------------------------
                        // AUTH APIs
                        // ------------------------------------

                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/register"
                        )
                        .permitAll()


                        // ------------------------------------
                        // USER APIs
                        // TEMPORARY TEST
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/users",
                                "/api/v1/users/**"
                        )
                        .permitAll()


                        // ------------------------------------
                        // USER APIs - POST
                        // ADMIN / SUPER_ADMIN
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/users",
                                "/api/v1/users/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )


                        // ------------------------------------
                        // USER APIs - PUT
                        // ADMIN / SUPER_ADMIN
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/users/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )


                        // ------------------------------------
                        // USER APIs - PATCH
                        // ADMIN / SUPER_ADMIN
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/users/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )


                        // ------------------------------------
                        // USER APIs - DELETE
                        // ADMIN / SUPER_ADMIN
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/users/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )


                        // ------------------------------------
                        // CASHIER APIs - GET
                        // ADMIN / SUPER_ADMIN
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/cashiers",
                                "/api/v1/cashiers/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )


                        // ------------------------------------
                        // CASHIER APIs - POST
                        // ADMIN / SUPER_ADMIN
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/cashiers"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )


                        // ------------------------------------
                        // CASHIER APIs - PUT
                        // ADMIN / SUPER_ADMIN
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/cashiers/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )


                        // ------------------------------------
                        // CASHIER APIs - PATCH
                        // ADMIN / SUPER_ADMIN
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/cashiers/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )


                        // ------------------------------------
                        // CASHIER APIs - DELETE
                        // ADMIN / SUPER_ADMIN
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/cashiers/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "SUPER_ADMIN"
                        )


                        // ------------------------------------
                        // EVERYTHING ELSE
                        // ------------------------------------

                        .anyRequest()
                        .authenticated()
                )


                // ============================================
                // JWT FILTER
                // ============================================

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }
}