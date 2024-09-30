package com.ing.brokerage.generator;

import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.customer.dto.CustomerDepositRequest;
import com.ing.brokerage.domain.customer.dto.CustomerWithdrawRequest;
import com.ing.brokerage.domain.customer.entity.Customer;
import java.math.BigDecimal;

public class CustomerDataGenerator {

  public static CustomerDTO generateCustomerDTO(){
   CustomerDTO customerDTO = new CustomerDTO();
   customerDTO.setId(1L);
   customerDTO.setUsableBalance(new BigDecimal("100.00"));
   customerDTO.setName("TEST_CUSTOMER_NAME");
   customerDTO.setSurname("TEST_CUSTOMER_SURNAME");

   return customerDTO;
  }

  public static Customer generateCustomer(){
    Customer customer = new Customer();
    customer.setId(1L);
    customer.setUsableBalance(new BigDecimal("100.00"));
    customer.setName("TEST_CUSTOMER_NAME");
    customer.setSurname("TEST_CUSTOMER_SURNAME");

    return customer;
  }

  public static Customer generateCustomerWithBalanceParam(BigDecimal usableBalance){
    Customer customer = new Customer();
    customer.setId(1L);
    customer.setUsableBalance(usableBalance);
    customer.setName("TEST_CUSTOMER_NAME");
    customer.setSurname("TEST_CUSTOMER_SURNAME");

    return customer;
  }

  public static CustomerDTO generateCustomerDTOWithBalance(BigDecimal usableBalance){
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setId(1L);
    customerDTO.setUsableBalance(usableBalance);
    customerDTO.setName("TEST_CUSTOMER_NAME");
    customerDTO.setSurname("TEST_CUSTOMER_SURNAME");

    return customerDTO;
  }

  public static CustomerWithdrawRequest generateCustomerWithdrawRequest(){
    CustomerWithdrawRequest customerWithdrawRequest = new CustomerWithdrawRequest();
    customerWithdrawRequest.setCustomerId(1L);
    customerWithdrawRequest.setAmount(new BigDecimal("100.00"));
    customerWithdrawRequest.setIban("TEST_IBAN");

    return customerWithdrawRequest;
  }

  public static CustomerDepositRequest generateCustomerDepositRequest(){
    CustomerDepositRequest customerDepositRequest = new CustomerDepositRequest();
    customerDepositRequest.setCustomerId(1L);
    customerDepositRequest.setAmount(new BigDecimal("100.00"));

    return customerDepositRequest;
  }
}
