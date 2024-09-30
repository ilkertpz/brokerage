package com.ing.brokerage.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListRequest {

  @NotNull
  private LocalDateTime startDateTime;

  @NotNull
  private LocalDateTime endDateTime;

  @NotNull
  private Long customerId;
}
