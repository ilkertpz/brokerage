package com.ing.brokerage.customer;

import static com.ing.brokerage.generator.CustomerDataGenerator.generateCustomer;
import static com.ing.brokerage.generator.CustomerDataGenerator.generateCustomerDTO;
import static com.ing.brokerage.generator.CustomerDataGenerator.generateCustomerDepositRequest;
import static com.ing.brokerage.generator.CustomerDataGenerator.generateCustomerWithBalanceParam;
import static com.ing.brokerage.generator.CustomerDataGenerator.generateCustomerWithdrawRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.customer.dto.CustomerDepositRequest;
import com.ing.brokerage.domain.customer.dto.CustomerWithdrawRequest;
import com.ing.brokerage.domain.customer.entity.Customer;
import com.ing.brokerage.domain.customer.mapper.CustomerMapperImpl;
import com.ing.brokerage.domain.customer.repository.CustomerRepository;
import com.ing.brokerage.domain.customer.service.CustomerService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {


  @Mock
  CustomerRepository customerRepository;

  @Mock
  CustomerMapperImpl customerMapper;

  @InjectMocks
  CustomerService customerService;

  @Test
  void givenCustomerDTO_whenUpdate_thenReturnUpdatedCustomerDTO() {

    // Arrange
    CustomerDTO customerDTO = generateCustomerDTO();
    Customer customer = generateCustomer();

    when(customerRepository.save(any())).thenReturn(customer);
    when(customerMapper.toDTO(any())).thenCallRealMethod();
    when(customerMapper.toEntity(any())).thenCallRealMethod();


    // Act
    CustomerDTO customerDTOResult = customerService.update(customerDTO);

    // Assert
    assertThat(customerDTOResult).isNotNull();
    assertThat(customerDTOResult.getId()).isNotNull();
    assertThat(customerDTOResult.getId()).isEqualTo(customerDTO.getId());
    assertThat(customerDTOResult.getName()).isNotNull();
    assertThat(customerDTOResult.getSurname()).isNotNull();
    assertThat(customerDTOResult.getUsableBalance()).isNotNull();


    verify(customerRepository, times(1)).save(any());

  }

  @Test
  void givenCustomerId_whenFindByCustomerId_thenReturnCustomerDTO() {

    // Arrange
    Long customerId = 1L;
    Customer customer = generateCustomer();

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    when(customerMapper.toDTO(any())).thenCallRealMethod();


    // Act
    CustomerDTO customerDTOResult = customerService.findByCustomerId(customerId);

    // Assert
    assertThat(customerDTOResult).isNotNull();
    assertThat(customerDTOResult.getId()).isNotNull();
    assertThat(customerDTOResult.getId()).isEqualTo(customerId);
    assertThat(customerDTOResult.getName()).isNotNull();
    assertThat(customerDTOResult.getSurname()).isNotNull();
    assertThat(customerDTOResult.getUsableBalance()).isNotNull();

    verify(customerRepository, times(1)).findById(customerId);

  }

  @Test
  void givenCustomerIdAndAmount_whenDeposit_thenReturnCustomerDTO() {

    // Arrange
    CustomerDTO customerDTO = generateCustomerDTO();
    CustomerDepositRequest customerDepositRequest = generateCustomerDepositRequest();
    Customer customer = generateCustomer();
    BigDecimal expectedAmount = customer.getUsableBalance().add(customerDepositRequest.getAmount());
    Customer customerWithNewUsableBalance = generateCustomerWithBalanceParam(expectedAmount);

    when(customerRepository.findById(customerDepositRequest.getCustomerId())).thenReturn(Optional.of(customer));
    when(customerRepository.save(any())).thenReturn(customerWithNewUsableBalance);
    when(customerMapper.toDTO(any())).thenCallRealMethod();


    // Act
    CustomerDTO customerDTOResult = customerService.deposit(customerDepositRequest);

    // Assert
    assertThat(customerDTOResult).isNotNull();
    assertThat(customerDTOResult.getId()).isNotNull();
    assertThat(customerDTOResult.getId()).isEqualTo(customerDTO.getId());
    assertThat(customerDTOResult.getName()).isNotNull();
    assertThat(customerDTOResult.getSurname()).isNotNull();
    assertThat(customerDTOResult.getUsableBalance()).isNotNull();
    assertThat(customerDTOResult.getUsableBalance()).isEqualTo(expectedAmount);


    verify(customerRepository, times(1)).findById(customerDepositRequest.getCustomerId());
    verify(customerRepository, times(1)).save(any());

  }

  @Test
  void givenCustomerIdAndAmount_whenWithdraw_thenReturnCustomerDTO() {

    // Arrange
    CustomerDTO customerDTO = generateCustomerDTO();
    Customer customer = generateCustomer();
    CustomerWithdrawRequest customerWithdrawRequest = generateCustomerWithdrawRequest();
    BigDecimal expectedAmount = customer.getUsableBalance().subtract(customerWithdrawRequest.getAmount());
    Customer customerWithNewUsableBalance = generateCustomerWithBalanceParam(expectedAmount);

    when(customerRepository.findById(customerWithdrawRequest.getCustomerId())).thenReturn(Optional.of(customer));
    when(customerRepository.save(any())).thenReturn(customerWithNewUsableBalance);
    when(customerMapper.toDTO(any())).thenCallRealMethod();


    // Act
    CustomerDTO customerDTOResult = customerService.withdraw(customerWithdrawRequest);

    // Assert
    assertThat(customerDTOResult).isNotNull();
    assertThat(customerDTOResult.getId()).isNotNull();
    assertThat(customerDTOResult.getId()).isEqualTo(customerDTO.getId());
    assertThat(customerDTOResult.getName()).isNotNull();
    assertThat(customerDTOResult.getSurname()).isNotNull();
    assertThat(customerDTOResult.getUsableBalance()).isNotNull();
    assertThat(customerDTOResult.getUsableBalance()).isEqualTo(expectedAmount);


    verify(customerRepository, times(1)).findById(customerWithdrawRequest.getCustomerId());
    verify(customerRepository, times(1)).save(any());

  }
}
