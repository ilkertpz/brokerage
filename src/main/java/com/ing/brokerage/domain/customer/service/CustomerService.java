package com.ing.brokerage.domain.customer.service;


import com.ing.brokerage.domain.customer.dto.CustomerDepositRequest;
import com.ing.brokerage.domain.customer.dto.CustomerWithdrawRequest;
import com.ing.brokerage.domain.customer.repository.CustomerRepository;
import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.customer.entity.Customer;
import com.ing.brokerage.domain.customer.exception.CustomerException;
import com.ing.brokerage.domain.customer.mapper.CustomerMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;


  @Override
  @Transactional(readOnly = true)
  public CustomerDTO findByCustomerId(Long customerId) {
    Customer customer = findById(customerId);
    return customerMapper.toDTO(customer);
  }



  @Override
  @Transactional
  public CustomerDTO update(CustomerDTO customerDTO) {
    Customer customer = customerMapper.toEntity(customerDTO);
    Customer updatedCustomer = customerRepository.save(customer);
    return customerMapper.toDTO(updatedCustomer);
  }

  @Override
  @Transactional
  public CustomerDTO deposit(CustomerDepositRequest customerDepositRequest) {
    Customer customer = findById(customerDepositRequest.getCustomerId());
    customer.setUsableBalance(customer.getUsableBalance().add(customerDepositRequest.getAmount()));
    Customer updatedCustomer = customerRepository.save(customer);
    return customerMapper.toDTO(updatedCustomer);
  }

  @Override
  @Transactional
  public CustomerDTO withdraw(CustomerWithdrawRequest customerWithdrawRequest) {
    Customer customer = findById(customerWithdrawRequest.getCustomerId());
    customer.setUsableBalance(customer.getUsableBalance().subtract(customerWithdrawRequest.getAmount()));
    Customer updatedCustomer = customerRepository.save(customer);
    //TODO: Send money to customer iban with a bank stakeholder API
    return customerMapper.toDTO(updatedCustomer);
  }

  //private methods
  private Customer findById(Long customerId) {
    Optional<Customer> customerOpt = customerRepository.findById(customerId);

    if(customerOpt.isEmpty()) {
      throw new CustomerException("Could not find customer");
    }
    return customerOpt.get();
  }

}
