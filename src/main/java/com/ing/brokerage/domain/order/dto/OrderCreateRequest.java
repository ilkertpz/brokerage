package com.ing.brokerage.domain.order.dto;

import com.ing.brokerage.domain.order.constant.Side;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

  @NotNull
  private String assetName;

  @NotNull
  private Side side;

  @NotNull
  private Long size;

  @NotNull
  private BigDecimal price;

  @NotNull
  private Long customerId;
}
