package com.csk.services.eazy.bank.controller;

import com.csk.services.eazy.bank.entity.Loans;
import com.csk.services.eazy.bank.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoansController {

    private final LoanRepository loanRepository;

    @GetMapping("/myLoans")
    //@PreAuthorize("preAuthValidator.validate(#id) AND hasAuthority('VIEWLOANS')")
    @PreAuthorize("@preAuthValidator.validate(#id)")
    public List<Loans> getLoanDetails(@RequestParam int id) {
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);
        if (loans != null ) {
            return loans;
        }else {
            return null;
        }
    }

}
