package com.ing.brokerage.domain.customer.service;

import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.customer.dto.CustomerDepositRequest;
import com.ing.brokerage.domain.customer.dto.CustomerWithdrawRequest;

public interface ICustomerService {

  CustomerDTO findByCustomerId(Long customerId);

  CustomerDTO update(CustomerDTO customerDTO);

  CustomerDTO deposit(CustomerDepositRequest customerDepositRequest);

  CustomerDTO withdraw(CustomerWithdrawRequest customerWithdrawRequest);

}
