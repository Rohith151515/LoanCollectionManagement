
        package com.loan.collection.management.LoanCollectionManagement.Config;

import com.loan.collection.management.LoanCollectionManagement.model.User;
import com.loan.collection.management.LoanCollectionManagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mobile)
            throws UsernameNotFoundException {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with mobile: " + mobile
                        )
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getMobile())
                .password(user.getPasswordHash())
                .roles(user.getRole().name())
                .build();
    }
}
