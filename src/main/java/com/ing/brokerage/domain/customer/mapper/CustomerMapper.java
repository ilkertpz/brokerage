package com.ing.brokerage.domain.customer.mapper;


import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.customer.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerDTO toDTO(Customer customer);

  Customer toEntity(CustomerDTO customerDTO);

}
