package com.csk.services.eazy.bank.service;

import com.csk.services.eazy.bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreAuthValidator {

    private final CustomerRepository customerRepository;

    public boolean validate(long customerId) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var hasAuthority = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> "ROLE_USER".equals(authority));

        if (hasAuthority) {

            var optionalCustomer = customerRepository.findById(customerId);

            if (optionalCustomer.get() != null) {

                var username = authentication.getName();
                var customer = optionalCustomer.get();

                Jwt principal = (Jwt) authentication.getPrincipal();
                String email = (String) principal.getClaims().get("email");
                if (email.equals(customer.getEmail())) {
                    return true;
                }
            }
        }

        return false;
    }
}
