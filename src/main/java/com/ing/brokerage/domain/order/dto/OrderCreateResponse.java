package com.ing.brokerage.domain.order.dto;

import com.ing.brokerage.domain.customer.dto.CustomerDTO;
import com.ing.brokerage.domain.order.constant.Side;
import com.ing.brokerage.domain.order.constant.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResponse {

  private Long id;

  private String assetName;

  private Side side;

  private Status status;

  private Long size;

  private BigDecimal price;

  private CustomerDTO customerDTO;

  private LocalDateTime createDate;
}
