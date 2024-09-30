package com.ing.brokerage.domain.order.dto;

import com.ing.brokerage.domain.asset.dto.AssetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMatchResponse {

  private AssetDTO assetDTO;

  private OrderDTO orderDTO;
}
