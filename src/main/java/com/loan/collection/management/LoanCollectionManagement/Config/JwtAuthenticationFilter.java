
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

        String authHeader = request.getHeader("Authorization");

        System.out.println("=================================");
        System.out.println("REQUEST: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("AUTH HEADER: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("NO JWT TOKEN");

            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {

            if (!jwtService.isTokenValid(token)) {

                System.out.println("JWT TOKEN IS INVALID");

                filterChain.doFilter(request, response);
                return;
            }

            String userId = jwtService.extractUserId(token);

            System.out.println("JWT USER ID: " + userId);

            if (userId == null || userId.isBlank()) {

                System.out.println("USER ID NOT FOUND IN TOKEN");

                filterChain.doFilter(request, response);
                return;
            }

            UUID uuid = UUID.fromString(userId);

            User user = userRepository.findById(uuid).orElse(null);

            if (user == null) {

                System.out.println("USER NOT FOUND: " + uuid);

                filterChain.doFilter(request, response);
                return;
            }

            String role = "ROLE_" + user.getRole().name();

            System.out.println("USER FOUND: " + user.getId());
            System.out.println("USER ROLE: " + role);

            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority(role);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getId(),
                            null,
                            List.of(authority)
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            System.out.println("AUTHENTICATION SUCCESS");
            System.out.println(
                    "AUTHORITIES: " +
                            SecurityContextHolder.getContext()
                                    .getAuthentication()
                                    .getAuthorities()
            );

        } catch (Exception e) {

            SecurityContextHolder.clearContext();

            System.out.println("JWT ERROR: " + e.getMessage());

            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}

