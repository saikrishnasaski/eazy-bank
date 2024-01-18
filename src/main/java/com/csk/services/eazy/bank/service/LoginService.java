/*
package com.csk.services.eazy.bank.section6.service;

import com.csk.services.eazy.bank.section6.exception.BusinessValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean registerUser(Customer customer) {

        var customers = customerRepository.findByEmail(customer.getEmail());

        if (!customers.isEmpty()) {

            log.error("User already exists with emailId ", customer.getEmail());
            throw new BusinessValidationException("User already exists", "email");
        }

        var encodedPassword = passwordEncoder.encode(customer.getPwd());
        customer.setPwd(encodedPassword);

        try {
            customerRepository.save(customer);
            return true;
        } catch (Exception e) {

            log.error("Exception occurred while user registration ", e.getMessage());
        }
        return false;
    }
}
*/
