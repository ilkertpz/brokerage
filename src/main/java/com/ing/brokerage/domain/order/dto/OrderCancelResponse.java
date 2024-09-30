package com.ing.brokerage.domain.order.dto;

import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCancelResponse {

  private OrderDTO order;

  private CustomerDTO customer;
}
