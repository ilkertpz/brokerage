package com.ing.brokerage.domain.customer.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDepositRequest {

  @NotNull
  private Long customerId;

  @NotNull
  private BigDecimal amount;

}
