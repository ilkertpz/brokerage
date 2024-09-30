package com.ing.brokerage.domain.customer.controller;


import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.customer.dto.CustomerDepositRequest;
import com.ing.brokerage.domain.customer.dto.CustomerWithdrawRequest;
import com.ing.brokerage.domain.customer.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brokerage/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final ICustomerService customerService;

  @PutMapping("/deposit")
  @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
  public ResponseEntity<CustomerDTO> deposit(@RequestBody @Valid CustomerDepositRequest customerDepositRequest) {
    return ResponseEntity.ok(customerService.deposit(customerDepositRequest));
  }

  @PutMapping("/withdraw")
  @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
  public ResponseEntity<CustomerDTO> withdraw(@RequestBody @Valid CustomerWithdrawRequest customerWithdrawRequest) {
    return ResponseEntity.ok(customerService.withdraw(customerWithdrawRequest));
  }

}
