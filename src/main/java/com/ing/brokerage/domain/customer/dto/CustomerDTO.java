package com.ing.brokerage.domain.customer.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  private Long id;

  private String name;

  private String surname;

  private BigDecimal usableBalance;

}
